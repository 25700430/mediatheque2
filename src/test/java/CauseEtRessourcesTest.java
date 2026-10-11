import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

class CauseEtRessourcesTest {
    @Test void cause_chainee() {
        Path absent = Path.of("fichier-qui-n-existe-pas.txt");
        CatalogueCorrompuException ex = assertThrows(CatalogueCorrompuException.class,
                () -> ChargeurCatalogue.chargerTitres(absent));
        assertTrue(ex.getMessage().contains("fichier-qui-n-existe-pas.txt")); // message
        assertInstanceOf(IOException.class, ex.getCause());                   // cause conservee
    }
}
