import java.util.Optional;

/*
 * REPONSES ECRITES (etape 3, a partir de BibliothecaireAbusif extends Catalogue<Document>)
 *
 * 1. Un bibliothecaire est-il un catalogue ? Non : c'est une personne qui UTILISE un catalogue.
 *    La relation "est un" est fausse, l'heritage ne sert qu'a recuperer du code "gratuit".
 *    Si Catalogue change (methode renommee, signature ou comportement modifie), Bibliothecaire
 *    change avec lui sans qu'on y touche : c'est la classe de base fragile.
 *    bibliothecaire.ajouter(livre) ne veut rien dire : le bibliothecaire "s'ajouterait" un livre.
 *
 * 2. Problemes de la conception :
 *    - relation "est un" fausse (heritage pour reutiliser du code) ;
 *    - API polluee : ajouter, rechercherParTitre, afficherTout visibles sur un bibliothecaire ;
 *    - couplage maximal : toute evolution de Catalogue risque de casser Bibliothecaire ;
 *    - substituabilite absurde : un bibliothecaire utilisable la ou un Catalogue est attendu ;
 *    - heritage unique consomme : impossible d'heriter de Personne ou Employe ;
 *    - relation figee a la compilation : impossible de lui donner un autre catalogue (test, section...).
 *
 * 3. Refactoring ci-dessous : composition (attribut injecte) + delegation.
 */
public class Bibliothecaire {
    private final String nom;
    private final Catalogue<Document> catalogue; // COMPOSITION : possede un catalogue

    public Bibliothecaire(String nom, Catalogue<Document> catalogue) {
        this.nom = nom;
        this.catalogue = catalogue;
    }

    public void accueillir() { System.out.println("Bonjour, " + nom); }

    // DELEGATION : on n'expose que ce qui a un sens pour un bibliothecaire
    public Optional<Document> chercher(String titre) {
        return catalogue.rechercherParTitre(titre);
    }
}
