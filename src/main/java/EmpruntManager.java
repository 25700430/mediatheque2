import java.time.LocalDate;

public class EmpruntManager {
    private final Catalogue<Document> catalogue; // COMPOSITION
    private final ConfigurationMediatheque config; // INJECTÉE
    private int empruntsEnCours = 0;

    public EmpruntManager(Catalogue<Document> catalogue, ConfigurationMediatheque config) {
        this.catalogue = catalogue;
        this.config = config;
    }

    public void emprunter(String titre) throws MediathequeException {
        if (empruntsEnCours >= config.getNbMaxEmprunts()) {
            throw new DocumentIndisponibleException(titre,
                    "quota d'emprunts atteint (" + config.getNbMaxEmprunts() + ")");
        }

        Document d = catalogue.rechercherParTitre(titre)
                .orElseThrow(() -> new DocumentIntrouvableException(titre));

        if (!(d instanceof Empruntable e)) {
            throw new DocumentIndisponibleException(titre, "consultation sur place uniquement");
        }

        e.emprunter();
        empruntsEnCours++;
    }

    public LocalDate dateRetour(LocalDate debut) {
        return debut.plusDays(config.getDureeEmpruntJours());
    }
}
