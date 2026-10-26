import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Chaque test utilise un catalogue neuf et sa propre configuration.
class EmpruntManagerTest {
    private Catalogue<Document> catalogue;

    @BeforeEach
    void preparer() {
        catalogue = new Catalogue<>();
        catalogue.ajouter(new Livre("Dune", 1965, "F. Herbert", 612));
    }

    @Test void emprunt_document_inconnu() {
        EmpruntManager m = new EmpruntManager(catalogue,
                new ConfigurationMediatheque(21, 5));
        MediathequeException ex = assertThrows(DocumentIntrouvableException.class,
                () -> m.emprunter("Fantome"));
        assertTrue(ex.getMessage().contains("Fantome"));
    }

    @Test void double_emprunt() throws MediathequeException {
        EmpruntManager m = new EmpruntManager(catalogue, new ConfigurationMediatheque(21, 5));
        m.emprunter("Dune");
        DocumentIndisponibleException ex = assertThrows(DocumentIndisponibleException.class,
                () -> m.emprunter("Dune"));
        assertTrue(ex.getMessage().contains("Dune"));
    }

    @Test void revue_non_empruntable() {
        catalogue.ajouter(new Revue("Science", 2026, 412));
        EmpruntManager m = new EmpruntManager(catalogue, new ConfigurationMediatheque(21, 5));
        DocumentIndisponibleException ex = assertThrows(DocumentIndisponibleException.class,
                () -> m.emprunter("Science"));
        assertTrue(ex.getMessage().contains("Science"));
    }

    @Test void emprunt_puis_retour_puis_nouvel_emprunt() throws MediathequeException {
        Livre l = (Livre) catalogue.rechercherParTitre("Dune").orElseThrow();
        EmpruntManager m = new EmpruntManager(catalogue, new ConfigurationMediatheque(21, 5));
        m.emprunter("Dune");
        assertFalse(l.estDisponible());
        l.rendre();
        assertDoesNotThrow(() -> m.emprunter("Dune"));
    }

    @Test void quota_refuse_un_emprunt_sans_emprunter_le_document() throws MediathequeException {
        Livre second = new Livre("Fondation", 1951, "I. Asimov", 255);
        catalogue.ajouter(second);
        EmpruntManager manager = new EmpruntManager(catalogue,
                new ConfigurationMediatheque(21, 1));

        manager.emprunter("Dune");
        DocumentIndisponibleException exception = assertThrows(
                DocumentIndisponibleException.class,
                () -> manager.emprunter("Fondation"));

        assertTrue(exception.getMessage().contains("quota d'emprunts atteint"));
        assertTrue(second.estDisponible());
    }

    @Test void dureePersonnalisee() {
        EmpruntManager manager = new EmpruntManager(catalogue,
                new ConfigurationMediatheque(7, 5));

        assertEquals(LocalDate.of(2026, 10, 8),
                manager.dateRetour(LocalDate.of(2026, 10, 1)));
    }

    @Test void dureeParDefaut() {
        EmpruntManager manager = new EmpruntManager(catalogue,
                new ConfigurationMediatheque(21, 5));

        assertEquals(LocalDate.of(2026, 10, 22),
                manager.dateRetour(LocalDate.of(2026, 10, 1)));
    }

    @Test void quotaAtteint() throws MediathequeException {
        EmpruntManager manager = new EmpruntManager(catalogue,
                new ConfigurationMediatheque(21, 1));
        manager.emprunter("Dune");

        assertThrows(DocumentIndisponibleException.class,
                () -> manager.emprunter("Dune"));
    }
}
