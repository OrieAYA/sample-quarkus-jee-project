package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.model.CashBack;
import fr.pantheonsorbonne.ufr27.miage.service.CashBackService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CashbackGateway {

    @Inject
    CashBackService cashbackService;

    public CashBack cashBack(CashBack cashBack){
        return cashbackService.cashback(cashBack);
    }

}