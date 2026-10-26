import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ImporteurTest {
    @Test
    void importeurCsvCreeLesTypesDeDocumentsCorrespondants() {
        Importeur importeur = new ImporteurCsv();

        List<Document> documents = importeur.importer(List.of(
                "livre;Dune;1;Herbert;1965",
                "dvd;Alien;2;117",
                "revue;Science;3;412"));

        assertEquals(3, documents.size());
        assertInstanceOf(Livre.class, documents.get(0));
        assertEquals("Dune", documents.get(0).getTitre());
        assertEquals(1, documents.get(0).getAnnee());
        assertInstanceOf(Dvd.class, documents.get(1));
        assertEquals("Alien", documents.get(1).getTitre());
        assertInstanceOf(Revue.class, documents.get(2));
        assertEquals("Science", documents.get(2).getTitre());
    }

    @Test
    void importeurLignesLivresAudioIgnoreIdSourceEtConserveLesDonneesAudio() {
        Importeur importeur = new ImporteurLignesLivresAudio();

        List<Document> documents = importeur.importer(List.of("Dune;42;Herbert;1965;900"));

        assertEquals(1, documents.size());
        LivreAudio livreAudio = assertInstanceOf(LivreAudio.class, documents.get(0));
        assertEquals("Dune", livreAudio.getTitre());
        assertEquals(1965, livreAudio.getAnnee());
        assertEquals(900, livreAudio.getDureeMinutes());
    }

    @Test
    void importerIgnoreLesLignesVidesEtDelegueLaCreation() {
        Importeur importeur = new Importeur() {
            @Override
            protected Document creerDocument(String ligne) {
                return new Revue(ligne, 2026, 1);
            }
        };

        List<Document> documents = importeur.importer(List.of("Science", " ", "\t"));

        assertEquals(1, documents.size());
        assertInstanceOf(Revue.class, documents.get(0));
        assertEquals("Science", documents.get(0).getTitre());
    }
}
