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

    public Iterator<Produit> iterateurInverse() {
        return new Iterator<>() {
            private int index = taille - 1;

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public Produit next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return articles[index--];
            }
        };
    }

    @Override
    public Iterator<Produit> iterator() {
        return new Iterator<>() {
            private int index;
            private int dernierIndex = -1;
            private boolean suppressionAutorisee;

            @Override
            public boolean hasNext() {
                return index < taille;
            }

            @Override
            public Produit next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                dernierIndex = index++;
                suppressionAutorisee = true;
                return articles[dernierIndex];
            }

            @Override
            public void remove() {
                if (!suppressionAutorisee) {
                    throw new IllegalStateException();
                }
                supprimerA(dernierIndex);
                index--;
                dernierIndex = -1;
                suppressionAutorisee = false;
            }
        };
    }

    private void supprimerA(int index) {
        int nombreApres = taille - index - 1;
        if (nombreApres > 0) {
            System.arraycopy(articles, index + 1, articles, index, nombreApres);
        }
        articles[--taille] = null;
    }
}
