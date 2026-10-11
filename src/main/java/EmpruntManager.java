public class EmpruntManager {
    private final Catalogue<Document> catalogue; // COMPOSITION

    public EmpruntManager(Catalogue<Document> catalogue) {
        this.catalogue = catalogue;
    }

    public void emprunter(String titre) throws MediathequeException {
        // 1. recherche (delegation au catalogue) : absent -> exception metier
        Document d = catalogue.rechercherParTitre(titre)
                .orElseThrow(() -> new DocumentIntrouvableException(titre));

        // 2. la capacite "Empruntable" (pattern matching instanceof, Java 16+)
        if (!(d instanceof Empruntable e)) {
            throw new DocumentIndisponibleException(titre, "consultation sur place uniquement");
        }

        // 3. emprunt : peut lever DocumentIndisponibleException (deja emprunte)
        e.emprunter();
    }
}
