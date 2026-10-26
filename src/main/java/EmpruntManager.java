import java.time.LocalDate;

public class EmpruntManager {
    private final Catalogue<Document> catalogue; // COMPOSITION
    private int empruntsEnCours = 0;

    public EmpruntManager(Catalogue<Document> catalogue) {
        this.catalogue = catalogue;
    }Étape 2 – Le flagrant délit
But : montrer par l'expérience que l'état global contamine les tests. Les deux tests sont corrects pris seuls, mais
le second échoue s'il s'exécute après le premier.
ConfigurationMediathequeTest.java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
// On FORCE l'ordre pour la démonstration (JUnit 5 n'en garantit aucun par défaut).
// Lancez la classe une fois avec @Order(1)/(2), puis une seconde fois en les inversant.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConfigurationMediathequeTest {

@Test @Order(1)
void dureePersonnalisee() {
// Modifie l'objet PARTAGÉ : la durée passe de 21 à 7 pour tout le programme
ConfigurationMediatheque.getInstance().setDureeEmprunt(7);
assertEquals(7, ConfigurationMediatheque.getInstance().getDureeEmpruntJours());
}
@Test @Order(2)
void dureeParDefaut() {
// Suppose la valeur par défaut (21). VERT s'il passe seul...
// ... ROUGE après dureePersonnalisee : expected <21> but was <7>
assertEquals(21, ConfigurationMediatheque.getInstance().getDureeEmpruntJours());
}
// FLAGRANT DÉLIT : l'instance du Singleton, et donc son état, survit entre les tests.
// dureeParDefaut dépend de l'ordre d'exécution. Un test qui dépend de l'ordre
// n'est plus un test. État global => tests contaminés.
}

Commit conseillé : « etape2: deux tests qui révèlent la contamination ».

    public void emprunter(String titre) throws MediathequeException {
        ConfigurationMediatheque configuration = ConfigurationMediatheque.getInstance();
        if (empruntsEnCours >= configuration.getNbMaxEmprunts()) {
            throw new DocumentIndisponibleException(titre,
                    "quota d'emprunts atteint (" + configuration.getNbMaxEmprunts() + ")");
        }

        // 1. recherche (delegation au catalogue) : absent -> exception metier
        Document d = catalogue.rechercherParTitre(titre)
                .orElseThrow(() -> new DocumentIntrouvableException(titre));

        // 2. la capacite "Empruntable" (pattern matching instanceof, Java 16+)
        if (!(d instanceof Empruntable e)) {
            throw new DocumentIndisponibleException(titre, "consultation sur place uniquement");
        }

        // 3. emprunt : peut lever DocumentIndisponibleException (deja emprunte)
        e.emprunter();
        empruntsEnCours++;
    }

    public LocalDate dateRetour(LocalDate debut) {
        return debut.plusDays(ConfigurationMediatheque.getInstance().getDureeEmpruntJours());
    }
}
