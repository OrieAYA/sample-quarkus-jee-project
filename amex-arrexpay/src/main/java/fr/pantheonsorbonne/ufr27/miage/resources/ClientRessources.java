package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.service.ClientService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("artist")
public class ClientRessources {

    @Inject
    ClientService clientService;

    @Path("{artistId}/Client/{clientId}")
    @DELETE
    public void cancelVenue(@PathParam("montantArgent") float montantArgent, @PathParam("clientId") int clientId) {
        clientService.ajoutArgent(montantArgent, clientId);
    }
}
