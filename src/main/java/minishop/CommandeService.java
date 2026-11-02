package minishop;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CommandeService {
    public void traiterCommande(List<Produit> produits, String typeClient, String email) {
        double total = 0;
        for (Produit produit : produits) {
            total += produit.getPrix();
        }

        if (typeClient.equals("STANDARD")) {
            total *= 1.00;
        } else if (typeClient.equals("PREMIUM")) {
            total *= 0.90;
        } else if (typeClient.equals("ETUDIANT")) {
            total *= 0.85;
        }

        try (FileWriter fw = new FileWriter("commandes.txt", true)) {
            fw.write(email + ";" + total + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Email a " + email + " : total = " + total);
    }
}
