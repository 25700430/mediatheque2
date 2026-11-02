package minishop;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Panier implements Iterable<Produit> {
    private final Produit[] articles = new Produit[10];
    private int taille = 0;

    public void ajouter(Produit produit) {
        if (taille == articles.length) {
            throw new IllegalStateException("Panier plein");
        }
        articles[taille++] = produit;
    }

    public Iterable<Produit> disponibles() {
        return () -> new FiltreDisponibles(iterator());
    }

    @Override
    public Iterator<Produit> iterator() {
        return new Iterator<>() {
            private int index;

            @Override
            public boolean hasNext() {
                return index < taille;
            }

            @Override
            public Produit next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return articles[index++];
            }
        };
    }
}
