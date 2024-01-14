package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.dto.Booking;
import fr.pantheonsorbonne.ufr27.miage.dto.ETicket;
import fr.pantheonsorbonne.ufr27.miage.model.CashBack;
import fr.pantheonsorbonne.ufr27.miage.model.Client;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.LinkedHashMap;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @ConfigProperty(name = "camel.routes.enabled", defaultValue = "true")
    boolean isRouteEnabled;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    ClientGateway clientGateway;

    @Inject
    CashbackGateway cashBackGateway;

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);

        from("direct:M1.AMEX.AskTaux")
                .log("Received request: ${body}")
                .process(exchange -> {
                    LinkedHashMap<String, Object> jsonMap = exchange.getIn().getBody(LinkedHashMap.class);
                    CashBack cashBack = new CashBack();
                    cashBack.setIdClient((Integer) jsonMap.get("idClient"));
                    cashBack.setIdCashback((Integer) jsonMap.get("idCashback"));
                    cashBack.setIdTransaction((Integer) jsonMap.get("idTransaction"));
                    cashBackGateway.cashBack(cashBack);
                    exchange.setProperty("cashBack", cashBack);
                    exchange.setProperty("idClient", cashBack.getIdClient());
                })
                .log("Sending reply: ${body}")
                .to("direct:sendReplyAmex");

        from("direct:sendReplyAmex")
                .log("Sent reply: ${body}");

    }
}
