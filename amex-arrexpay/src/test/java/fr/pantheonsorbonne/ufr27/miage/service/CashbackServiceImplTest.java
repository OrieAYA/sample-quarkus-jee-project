package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.exception.CustomerNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.CashBack;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class CashbackServiceImplTest {
    @Inject
    CashbackService cashbackService;

    @Test
    void should_return_CashbackAmount() throws CustomerNotFoundException {
        int idClient = 456;

        CashBack expectedCashback = new CashBack(idClient);
        expectedCashback.setIdTransaction(555);
        expectedCashback.setMontantCashback(28);

        Double result = cashbackService.getCashbackAmount(idClient);

        assertEquals(expectedCashback.getMontantCashback(), result);
    }

    @Test
    void should_throw_exception() {
        assertThrows(CustomerNotFoundException.class, () -> cashbackService.getCashbackAmount(123));
    }

}
