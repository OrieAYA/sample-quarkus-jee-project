package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.CashbackDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.model.Cashback;
import jakarta.enterprise.context.ApplicationScoped;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ApplicationScoped
public class CashbackServiceTest {

    @InjectMocks
    private CashbackService cashbackService;

    @Mock
    private CashbackDAOImpl cdi;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCashback() {
        // Création d'un objet Cashback pour le test
        Cashback cashback = new Cashback(1, 5.0, 100.0);

        // Simuler le comportement du CashbackDAOImpl
        when(cdi.createNewCashback(cashback.getIdClient(), cashback.getTaux(), cashback.getMontant()))
                .thenReturn(cashback);

        // Appel à la méthode cashback du service
        Cashback result = cashbackService.cashback(cashback);

        // Vérifications
        verify(cdi, times(1)).createNewCashback(cashback.getIdClient(), cashback.getTaux(), cashback.getMontant());
        assertSame(cashback, result);
    }
}
