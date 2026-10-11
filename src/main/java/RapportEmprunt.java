import java.util.ArrayList;
import java.util.List;

/** Memorise les titres empruntes ; fermee automatiquement par try-with-resources. */
public class RapportEmprunt implements AutoCloseable {
    private final List<String> titres = new ArrayList<>();
    private boolean ferme = false;

    public void enregistrer(String titre) { titres.add(titre); }
    public List<String> getTitres() { return List.copyOf(titres); }
    public boolean estFerme() { return ferme; }

    @Override
    public void close() { // pas de "throws Exception" : try (rapport) n'impose rien a l'appelant
        ferme = true;
        System.out.println("Rapport clos : " + titres);
    }
}
