package minishop;

import java.util.List;

public class CommandeService {
    private final DepotCommandes depot;
    private final Notificateur notificateur;
    private final Journal journal;

    public CommandeService(DepotCommandes depot, Notificateur notificateur, Journal journal) {
        this.depot = depot;
        this.notificateur = notificateur;
        this.journal = journal;
    }

    public double traiter(Iterable<Produit> produits, RegleRemise remise, String email) {
        double total = 0;
        for (Produit produit : produits) {
            total += produit.getPrix();
        }

        total = remise.appliquer(total);
        depot.enregistrer(email, total);
        notificateur.confirmer(email, total);
        journal.ecrire("Commande traitee : " + email + "= " + total);
        return total;
    }
}
