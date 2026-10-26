import java.nio.file.Path;
import java.util.List;

/** Point d'entree : petit scenario de demonstration/test a la main de la mediatheque. */
public class Main {
    public static void main(String[] args) throws MediathequeException {
        // 1. Catalogue + documents
        Catalogue<Document> catalogue = new Catalogue<>();
        // L'unicite de la configuration est une decision d'assemblage prise ici.
        var config = new ConfigurationMediatheque(21, 5);
        var manager = new EmpruntManager(catalogue, config);
        List<Document> docs = new ImporteurCsv().importer(List.of(
                "livre;Les Miserables;1862;Victor Hugo;1488",
                "dvd;Inception;2010;148",
                "revue;Science et Vie;2024;1285"));
        docs.forEach(catalogue::ajouter);

        System.out.println("=== Catalogue (trie par titre) ===");
        catalogue.afficherTout();

        // 2. Bibliothecaire (composition + delegation)
        System.out.println("\n=== Bibliothecaire ===");
        Bibliothecaire biblio = new Bibliothecaire("Sami", catalogue);
        biblio.accueillir();
        System.out.println("Recherche 'inception' : "
                + biblio.chercher("inception").map(Document::descriptionCourte).orElse("introuvable"));
        System.out.println("Recherche 'Dune' : "
                + biblio.chercher("Dune").map(Document::descriptionCourte).orElse("introuvable"));

        // 3. Emprunts + exceptions metier
        System.out.println("\n=== Emprunts ===");
        try (RapportEmprunt rapportEmprunt = new RapportEmprunt()) {
            String[] demandes = {"Inception", "Inception", "Science et Vie", "Dune"};
            for (String titre : demandes) {
                try {
                    manager.emprunter(titre);
                    rapportEmprunt.enregistrer(titre);
                    System.out.println("OK        : " + titre);
                } catch (MediathequeException e) {
                    System.out.println("REFUSE    : " + e.getMessage());
                }
            }
        } // close() appele automatiquement ici

        // 4. Chargement d'un fichier de titres (fichier inexistant -> exception)
        System.out.println("\n=== Chargement de fichier ===");
        try {
            System.out.println(ChargeurCatalogue.chargerTitres(Path.of("titres.txt")));
        } catch (CatalogueCorrompuException e) {
            System.out.println("Erreur : " + e.getMessage() + " (cause : " + e.getCause() + ")");
        }

        // 5. Rapports (SOLID : format, sortie et notification injectes)
        System.out.println("\n=== Rapports ===");
        SortieRapport console = contenu -> System.out.println(contenu);
        Notificateur notif = message -> System.out.println("[NOTIF] " + message);
        try {
            System.out.println("--- CSV ---");
            new RapportService(new FormatCsv(), console, notif).generer(docs);
            System.out.println("--- JSON ---");
            new RapportService(new FormatJson(), console, notif).generer(docs);
        } catch (java.io.IOException e) {
            System.out.println("Erreur d'ecriture : " + e.getMessage());
        }
    }
}