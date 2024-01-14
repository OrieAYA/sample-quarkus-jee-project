package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.dto.ConfirmationPayment;
import fr.pantheonsorbonne.ufr27.miage.dto.InformationForAmex;
import fr.pantheonsorbonne.ufr27.miage.dto.InformationPayment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @ConfigProperty(name = "camel.routes.enabled", defaultValue = "true")
    boolean isRouteEnabled;

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() {
        camelContext.setTracing(true);

        from("direct:validatePayment")
                .autoStartup(isRouteEnabled)
                .setExchangePattern(ExchangePattern.InOut)
                .process(exchange -> {
                    InformationPayment i = exchange.getIn().getBody(InformationPayment.class);
                    Map<String, Object> jsonOutput = new HashMap<>();
                    jsonOutput.put("idClient", i.getClient().getIdClient());
                    jsonOutput.put("prix",(double) i.getPrice());
                    exchange.getIn().setBody(jsonOutput);
                })
                .marshal().json()
                .to("sjms2:M1.payment.validate?exchangePattern=InOut")
                .unmarshal().json(ConfirmationPayment.class);

        from("direct:sendToAmex")
                .autoStartup(isRouteEnabled)
                .process(exchange -> {
                    InformationForAmex i = exchange.getIn().getBody(InformationForAmex.class);
                    Map<String, Object> jsonOutput = new HashMap<>();
                    jsonOutput.put("idClient", i.getClient().getIdClient());
                    jsonOutput.put("age", i.getClient().getAge());
                    jsonOutput.put("profession",i.getClient().getProfession());
                    jsonOutput.put("genre",i.getClient().getGenre());
                    jsonOutput.put("prix",(double) i.getPrice());
                    exchange.getIn().setBody(jsonOutput);
                })
                .marshal().json()
                .multicast()
                .to("file:data/folder","sjms2:M1.AMEX.amex");
    }
}
