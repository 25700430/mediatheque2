package minishop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CommandeServiceTest {
    @Test
    void lesRemisesExistantesConserventLeursTaux() {
        assertEquals(100.0, new RemiseStandard().appliquer(100.0));
        assertEquals(90.0, new RemisePremium().appliquer(100.0));
        assertEquals(85.0, new RemiseEtudiant().appliquer(100.0));
    }

    @Test
    void traiterEnregistreNotifieEtJournaliseLaCommande() {
        DepotFactice depot = new DepotFactice();
        NotificateurFactice notificateur = new NotificateurFactice();
        JournalFactice journal = new JournalFactice();
        CommandeService service = new CommandeService(depot, notificateur, journal);

        double total = service.traiter(panierTest().disponibles(), new RemisePremium(), "client@example.com");

        assertEquals(112.5, total);
        assertEquals(112.5, depot.dernierTotal);
        assertEquals(1, depot.nombreAppels);
        assertEquals(1, notificateur.nombreAppels);
        assertIterableEquals(List.of("Commande traitee : client@example.com= 112.5"), journal.messages);
    }

    @Test
    void remiseBlackFridayAppliqueTrentePourCentDeRemise() {
        DepotFactice depot = new DepotFactice();
        NotificateurFactice notificateur = new NotificateurFactice();
        JournalFactice journal = new JournalFactice();
        CommandeService service = new CommandeService(depot, notificateur, journal);

        double total = service.traiter(panierTest().disponibles(), new RemiseBlackFriday(), "client@example.com");

        assertEquals(87.5, total);
        assertEquals(87.5, depot.dernierTotal);
        assertEquals(1, depot.nombreAppels);
        assertEquals(1, notificateur.nombreAppels);
        assertIterableEquals(List.of("Commande traitee : client@example.com= 87.5"), journal.messages);
    }

    private Panier panierTest() {
        Panier panier = new Panier();
        panier.ajouter(new Produit("Clavier", 45.0, true));
        panier.ajouter(new Produit("Souris", 20.0, true));
        panier.ajouter(new Produit("Ecran", 150.0, false));
        panier.ajouter(new Produit("Casque", 60.0, true));
        return panier;
    }

    private static class DepotFactice implements DepotCommandes {
        private double dernierTotal;
        private int nombreAppels;

        @Override
        public void enregistrer(String email, double total) {
            dernierTotal = total;
            nombreAppels++;
        }
    }

    private static class NotificateurFactice implements Notificateur {
        private int nombreAppels;

        @Override
        public void confirmer(String email, double total) {
            nombreAppels++;
        }
    }

    private static class JournalFactice implements Journal {
        private final List<String> messages = new ArrayList<>();

        @Override
        public void ecrire(String message) {
            messages.add(message);
        }

        @Override
        public List<String> getMessages() {
            return List.copyOf(messages);
        }
    }
}
