package minishop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

class PanierTest {
    @Test
    void laBoucleForEachParcourtTousLesArticlesAjoutes() {
        Panier panier = new Panier();
        panier.ajouter(new Produit("Clavier", 45.0, true));
        panier.ajouter(new Produit("Souris", 20.0, true));
        panier.ajouter(new Produit("Ecran", 150.0, false));
        panier.ajouter(new Produit("Casque", 60.0, true));

        List<String> noms = new ArrayList<>();
        for (Produit produit : panier) {
            noms.add(produit.getNom());
        }

        assertEquals(List.of("Clavier", "Souris", "Ecran", "Casque"), noms);
    }

    @Test
    void disponiblesNeParcourtQueLesProduitsDisponibles() {
        Panier panier = new Panier();
        panier.ajouter(new Produit("Clavier", 45.0, true));
        panier.ajouter(new Produit("Souris", 20.0, true));
        panier.ajouter(new Produit("Ecran", 150.0, false));
        panier.ajouter(new Produit("Casque", 60.0, true));

        List<String> noms = new ArrayList<>();
        for (Produit produit : panier.disponibles()) {
            noms.add(produit.getNom());
        }

        assertEquals(List.of("Clavier", "Souris", "Casque"), noms);
    }

    @Test
    void iterateurInverseParcourtDuDernierAuPremier() {
        Panier panier = panierTest();
        List<String> noms = new ArrayList<>();

        Iterator<Produit> iterateur = panier.iterateurInverse();
        while (iterateur.hasNext()) {
            noms.add(iterateur.next().getNom());
        }

        assertEquals(List.of("Casque", "Ecran", "Souris", "Clavier"), noms);
    }

    @Test
    void removeSupprimeLeDernierProduitRenvoyeEtConserveLeParcours() {
        Panier panier = panierTest();
        Iterator<Produit> iterateur = panier.iterator();

        assertEquals("Clavier", iterateur.next().getNom());
        iterateur.remove();
        assertEquals("Souris", iterateur.next().getNom());
        assertEquals("Ecran", iterateur.next().getNom());
        iterateur.remove();
        assertEquals("Casque", iterateur.next().getNom());
        assertEquals(List.of("Souris", "Casque"), noms(panier));
    }

    @Test
    void removeRespecteLeContratDeIterator() {
        Iterator<Produit> iterateur = panierTest().iterator();

        assertThrows(IllegalStateException.class, iterateur::remove);
        iterateur.next();
        iterateur.remove();
        assertThrows(IllegalStateException.class, iterateur::remove);
    }

    private Panier panierTest() {
        Panier panier = new Panier();
        panier.ajouter(new Produit("Clavier", 45.0, true));
        panier.ajouter(new Produit("Souris", 20.0, true));
        panier.ajouter(new Produit("Ecran", 150.0, false));
        panier.ajouter(new Produit("Casque", 60.0, true));
        return panier;
    }

    private List<String> noms(Panier panier) {
        List<String> noms = new ArrayList<>();
        for (Produit produit : panier) {
            noms.add(produit.getNom());
        }
        return noms;
    }
}
