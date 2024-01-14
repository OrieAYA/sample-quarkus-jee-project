package fr.pantheonsorbonne.ufr27.miage.camel;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.model.CashBack;
import fr.pantheonsorbonne.ufr27.miage.service.CashbackService;
import io.vertx.core.json.JsonObject;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @Inject
    CamelContext camelContext;

    @Inject
    CashbackService cashbackService;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);

        from("sjms2:M1.payment.validate?exchangePattern=InOut")
                .unmarshal().json(JsonLibrary.Jackson, LinkedHashMap.class)
                .process(exchange -> {
                    LinkedHashMap<String, Object> jsonMap = exchange.getIn().getBody(LinkedHashMap.class);
                    Integer idClient = (Integer) jsonMap.get("idClient");
                    double price = (double) jsonMap.get("prix");
                    CashBack c = cashbackService.cashback(idClient);
                    boolean b = cashbackService.verifPrice(price);
                    Map<String, Object> jsonOutput = new HashMap<>();
                    jsonOutput.put("idTransaction", c.getIdTransaction());
                    jsonOutput.put("transactionStatus", b);
                    exchange.getIn().setBody(jsonOutput);;
                })
                .marshal().json();

        from("sjms2:M1.AMEX.AskTaux?exchangePattern=InOut")
                .unmarshal().json(JsonLibrary.Jackson, LinkedHashMap.class)
                .process(exchange -> {
                    LinkedHashMap<String, Object> jsonMap = exchange.getIn().getBody(LinkedHashMap.class);
                    Integer idClient = (Integer) jsonMap.get("idClient");
                    Integer idTransaction = (Integer) jsonMap.get("idTransaction");
                    Integer taux = cashbackService.returnTaux(idClient);
                    Map<String, Object> jsonOutput = new HashMap<>();
                    jsonOutput.put("idTransaction", idTransaction);
                    jsonOutput.put("idClient", idClient);
                    jsonOutput.put("tauxCashback", taux);
                    exchange.getIn().setBody(jsonOutput);;
                })
                .marshal().json();

        from("sjms2:M1.AMEX.toAMEXPay")
                .unmarshal().json(JsonLibrary.Jackson, LinkedHashMap.class)
                .process(exchange -> {
                    LinkedHashMap<String, Object> jsonMap = exchange.getIn().getBody(LinkedHashMap.class);
                    Integer idClient = (Integer) jsonMap.get("idClient");
                    double montant = (double) jsonMap.get("montantCashback");
                    cashbackService.addMontant(idClient,montant);
                });
    }
}
