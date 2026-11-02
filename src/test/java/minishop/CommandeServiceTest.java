package minishop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CommandeServiceTest {
    @Test
    void appliqueLesRemisesSelonLeTypeDeClient() {
        assertEquals(100.0, new RemiseStandard().appliquer(100.0));
        assertEquals(90.0, new RemisePremium().appliquer(100.0));
        assertEquals(85.0, new RemiseEtudiant().appliquer(100.0));
    }

    @Test
    void calculeEnregistreEtConfirmeLeTotalRemise() {
        List<String> operations = new ArrayList<>();
        DepotCommandes depot = (email, total) -> operations.add("depot:" + email + ":" + total);
        Notificateur notificateur = (email, total) -> operations.add("confirmation:" + email + ":" + total);
        CommandeService service = new CommandeService(depot, notificateur);
        List<Produit> produits = List.of(
                new Produit("Livre", 40.0, true),
                new Produit("DVD", 60.0, true));

        double total = service.traiter(produits, new RemisePremium(), "client@example.com");

        assertEquals(90.0, total);
        assertIterableEquals(List.of(
                "depot:client@example.com:90.0",
                "confirmation:client@example.com:90.0"), operations);
    }
}
