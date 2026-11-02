package minishop;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FiltreDisponibles implements Iterator<Produit> {
    private final Iterator<Produit> source;
    private Produit prochain;

    public FiltreDisponibles(Iterator<Produit> source) {
        this.source = source;
        avancer();
    }

    @Override
    public boolean hasNext() {
        return prochain != null;
    }

    @Override
    public Produit next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Produit courant = prochain;
        avancer();
        return courant;
    }

    private void avancer() {
        prochain = null;
        while (source.hasNext()) {
            Produit candidat = source.next();
            if (candidat != null && candidat.isDisponible()) {
                prochain = candidat;
                return;
            }
        }
    }
}
