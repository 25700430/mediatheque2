import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LivreAudioTest {
    @Test
    void heriteDuLivreEtExposeSaDuree() {
        LivreAudio livreAudio = new LivreAudio("Dune", "Frank Herbert", 1965, 900);

        assertTrue(livreAudio instanceof Livre);
        assertEquals("Dune", livreAudio.getTitre());
        assertEquals(1965, livreAudio.getAnnee());
        assertEquals(900, livreAudio.getDureeMinutes());
        assertTrue(livreAudio.descriptionCourte().contains("900 min"));
    }
}
