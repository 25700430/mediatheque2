package minishop;

public class Main {
    public static void main(String[] args) {
        Panier panier = new Panier();
        panier.ajouter(new Produit("Clavier", 45.0, true));
        panier.ajouter(new Produit("Souris", 20.0, true));
        panier.ajouter(new Produit("Ecran", 150.0, false));
        panier.ajouter(new Produit("Casque", 60.0, true));

        Journal journal = JournalApplication.getInstance();
        CommandeService service = new CommandeService(
                new DepotFichier(),
                new NotificateurConsole(),
                journal);

        double total = service.traiter(panier.disponibles(), new RemisePremium(), "client@example.com");
        System.out.println("Total final : " + total);
    }
}
