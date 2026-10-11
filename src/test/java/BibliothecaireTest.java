import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BibliothecaireTest {
    @Test void chercher_delegue_au_catalogue_injecte() {
        Catalogue<Document> c = new Catalogue<>();
        c.ajouter(new Livre("Dune", 1965, "F. Herbert", 612));
        Bibliothecaire b = new Bibliothecaire("Sami", c);
        assertTrue(b.chercher("dune").isPresent());
        assertTrue(b.chercher("Inconnu").isEmpty());
    }
}
