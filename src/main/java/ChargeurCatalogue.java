import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ChargeurCatalogue {
    /** Lit un fichier contenant un titre par ligne. */
    public static List<String> chargerTitres(Path chemin) throws CatalogueCorrompuException {
        try (var lecteur = Files.newBufferedReader(chemin)) { // fermeture garantie
            return lecteur.lines().toList();
        } catch (IOException | UncheckedIOException e) {
            // lines() signale une erreur de lecture en cours de flux par une UncheckedIOException
            Throwable cause = (e instanceof UncheckedIOException u) ? u.getCause() : e;
            throw new CatalogueCorrompuException("Echec du chargement : " + chemin, cause);
        }
    }
}
