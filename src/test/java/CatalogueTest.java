import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CatalogueTest {
    // throws : emprunter() est une exception checked
    @Test void emprunt_nominal() throws DocumentIndisponibleException {
        Livre l = new Livre("Clean Code", 2008, "R. Martin", 464);
        l.emprunter();
        assertFalse(l.estDisponible());
        l.rendre();
        assertTrue(l.estDisponible());
    }

    @Test void double_emprunt_leve_une_exception_metier() throws DocumentIndisponibleException {
        Livre l = new Livre("Refactoring", 1999, "M. Fowler", 448);
        l.emprunter();
        DocumentIndisponibleException ex =
                assertThrows(DocumentIndisponibleException.class, l::emprunter);
        assertTrue(ex.getMessage().contains("Refactoring"));
    }

    @Test void double_emprunt_dvd() throws DocumentIndisponibleException {
        Dvd d = new Dvd("Inception", 2010, 148);
        d.emprunter();
        DocumentIndisponibleException ex =
                assertThrows(DocumentIndisponibleException.class, d::emprunter);
        assertTrue(ex.getMessage().contains("Inception"));
    }

    @Test void recherche_infructueuse() {
        Catalogue<Document> c = new Catalogue<>();
        c.ajouter(new Revue("Science", 2026, 412));
        assertTrue(c.rechercherParTitre("Nature").isEmpty());
    }

    @Test void recherche_ignore_la_casse() {
        Catalogue<Document> c = new Catalogue<>();
        c.ajouter(new Dvd("Inception", 2010, 148));
        assertTrue(c.rechercherParTitre("INCEPTION").isPresent());
    }
}
