import java.util.ArrayList;
import java.util.List;

public abstract class Importeur {
    public final List<Document> importer(List<String> lignes) {
        List<Document> docs = new ArrayList<>();
        for (String ligne : lignes) {
            if (!ligne.isBlank()) {
                docs.add(creerDocument(ligne));
            }
        }
        return docs;
    }

    protected abstract Document creerDocument(String ligne);
}
