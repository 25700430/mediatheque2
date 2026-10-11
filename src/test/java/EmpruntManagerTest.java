import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmpruntManagerTest {
    @Test void emprunt_document_inconnu() {
        EmpruntManager m = new EmpruntManager(new Catalogue<>());
        MediathequeException ex = assertThrows(DocumentIntrouvableException.class,
                () -> m.emprunter("Fantome"));
        assertTrue(ex.getMessage().contains("Fantome"));
    }

    @Test void double_emprunt() throws MediathequeException {
        Catalogue<Document> c = new Catalogue<>();
        c.ajouter(new Livre("Clean Code", 2008, "R. Martin", 464));
        EmpruntManager m = new EmpruntManager(c);
        m.emprunter("Clean Code");
        DocumentIndisponibleException ex = assertThrows(DocumentIndisponibleException.class,
                () -> m.emprunter("Clean Code"));
        assertTrue(ex.getMessage().contains("Clean Code"));
    }

    @Test void revue_non_empruntable() {
        Catalogue<Document> c = new Catalogue<>();
        c.ajouter(new Revue("Science", 2026, 412));
        EmpruntManager m = new EmpruntManager(c);
        DocumentIndisponibleException ex = assertThrows(DocumentIndisponibleException.class,
                () -> m.emprunter("Science"));
        assertTrue(ex.getMessage().contains("Science"));
    }

    @Test void emprunt_puis_retour_puis_nouvel_emprunt() throws MediathequeException {
        Livre l = new Livre("Dune", 1965, "F. Herbert", 612);
        Catalogue<Document> c = new Catalogue<>();
        c.ajouter(l);
        EmpruntManager m = new EmpruntManager(c);
        m.emprunter("Dune");
        assertFalse(l.estDisponible());
        l.rendre();
        assertDoesNotThrow(() -> m.emprunter("Dune"));
    }
}
