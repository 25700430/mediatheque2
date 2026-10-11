import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RapportEmpruntTest {
    @Test void rapport_ferme_meme_si_un_emprunt_echoue() {
        Catalogue<Document> c = new Catalogue<>();
        c.ajouter(new Livre("Clean Code", 2008, "R. Martin", 464));
        EmpruntManager m = new EmpruntManager(c);
        RapportEmprunt rapport = new RapportEmprunt();

        assertThrows(DocumentIndisponibleException.class, () -> {
            try (rapport) {
                m.emprunter("Clean Code");
                rapport.enregistrer("Clean Code");
                m.emprunter("Clean Code"); // leve : close() sera quand meme appele
            }
        });

        assertTrue(rapport.estFerme());
        assertEquals(1, rapport.getTitres().size());
    }
}
