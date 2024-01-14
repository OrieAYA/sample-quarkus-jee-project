package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.service.ClientService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("Client")
public class ClientRessources {

    @Inject
    ClientService clientService;

    @Path("Client/{clientId}/{montantArgent}")
    @DELETE
    public void cancelVenue(@PathParam("montantArgent") float montantArgent, @PathParam("clientId") int clientId) {
        clientService.ajoutArgent(montantArgent, clientId);
    }
}
