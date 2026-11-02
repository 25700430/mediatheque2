package minishop;

import java.util.List;

public class CommandeService {
    private final DepotCommandes depot;
    private final Notificateur notificateur;

    public CommandeService(DepotCommandes depot, Notificateur notificateur) {
        this.depot = depot;
        this.notificateur = notificateur;
    }

    public double traiter(List<Produit> produits, RegleRemise remise, String email) {
        double total = 0;
        for (Produit produit : produits) {
            total += produit.getPrix();
        }

        total = remise.appliquer(total);
        depot.enregistrer(email, total);
        notificateur.confirmer(email, total);
        return total;
    }
}
