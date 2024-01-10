package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dao.NoSuchTicketException;
import fr.pantheonsorbonne.ufr27.miage.dto.Booking;
import fr.pantheonsorbonne.ufr27.miage.dto.ETicket;
import fr.pantheonsorbonne.ufr27.miage.exception.CustomerNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.exception.ExpiredTransitionalTicketException;
import fr.pantheonsorbonne.ufr27.miage.exception.UnsuficientQuotaForVenueException;
import fr.pantheonsorbonne.ufr27.miage.service.TicketingService;
import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @ConfigProperty(name = "camel.routes.enabled", defaultValue = "true")
    boolean isRouteEnabled;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    BookingGateway bookingHandler;

    @Inject
    TicketingService ticketingService;

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        //---------------------------------------------------------------MsgReception-------------
        // Configuration du composant SQL pour l'accès à la base de données
        getContext().addComponent("sql", new SqlComponent());

        // Route pour récupérer des informations du canal P2P et les injecter dans la base de données
        from("direct:recevoirInfos")
                .log("Réception d'informations : ${body}")
                .to("sql:INSERT INTO ma_table (colonne1, colonne2) VALUES (#, #)") // Remplacez avec vos colonnes et valeurs
                .log("Informations injectées dans la base de données avec succès");

        //---------------------------------------------------------------MsgToAmex----------------
        // Utilisation d'un message store pour la livraison garantie
        from("direct:envoyerDemande")
                .log("Envoi d'une demande")
                .setBody().constant("Ceci est une demande")
                .to("seda:requete");  // Utilisation de SEDA comme message store

        // Route pour traiter la demande
        from("seda:requete?size=10")  // Utilisation de SEDA comme message store
                .log("Traitement de la demande : ${body}")
                .transform().simple("Réponse à la demande : ${body}")
                .to("direct:envoyerReponse");

        // Route pour recevoir la réponse
        from("direct:envoyerReponse")
                .log("Réception d'une réponse : ${body}");

        //---------------------------------------------------------------MsgToClient--------------
        // Route pour créer un objet et l'exposer via un service REST
        from("direct:restReturnObject")
                .process(exchange -> {
                    // Créez ici votre objet à envoyer
                    MonObjet returnClient = new MonObjet();
                    monObjet.setNom("Exemple");
                    monObjet.setContent("Ceci est un exemple d'objet REST");

                    // Définissez l'objet dans le corps du message
                    exchange.getIn().setBody(monObjet);
                })
                .to("direct:restReturnObject");

        // Route pour traiter la réponse du service REST (optionnelle)
        from("direct:restReturnObject")
                .log("Réponse du service REST : ${body}");

        //---------------------------------------------------------------------------------------

        camelContext.setTracing(true);

        onException(ExpiredTransitionalTicketException.class)
                .handled(true)
                .process(new ExpiredTransitionalTicketProcessor())
                .setHeader("success", simple("false"))
                .log("Clearning expired transitional ticket ${body}")
                .bean(ticketingService, "cleanUpTransitionalTicket");

        onException(UnsuficientQuotaForVenueException.class)
                .handled(true)
                .setHeader("success", simple("false"))
                .setBody(simple("Vendor has not enough quota for this venue"));


        onException(NoSuchTicketException.class)
                .handled(true)
                .setHeader("success", simple("false"))
                .setBody(simple("Ticket has expired"));

        onException(CustomerNotFoundException.NoSeatAvailableException.class)
                .handled(true)
                .setHeader("success", simple("false"))
                .setBody(simple("No seat is available"));


        from("sjms2:" + jmsPrefix + "booking?exchangePattern=InOut")//
                .autoStartup(isRouteEnabled)
                .log("ticker received: ${in.headers}")//
                .unmarshal().json(Booking.class)//
                .bean(bookingHandler, "book").marshal().json()
        ;


        from("sjms2:" + jmsPrefix + "ticket?exchangePattern=InOut")
                .autoStartup(isRouteEnabled)
                .unmarshal().json(ETicket.class)
                .bean(ticketingService, "emitTicket").marshal().json();


        from("direct:ticketCancel")
                .autoStartup(isRouteEnabled)
                .marshal().json()
                .to("sjms2:topic:" + jmsPrefix + "cancellation");

    }

    private static class ExpiredTransitionalTicketProcessor implements Processor {
        @Override
        public void process(Exchange exchange) throws Exception {
            //https://camel.apache.org/manual/exception-clause.html
            CamelExecutionException caused = (CamelExecutionException) exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);


            exchange.getMessage().setBody(((ExpiredTransitionalTicketException) caused.getCause()).getExpiredTicketId());
        }
    }
}
