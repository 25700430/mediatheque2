package minishop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
}
