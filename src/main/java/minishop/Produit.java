package minishop;

public class Produit {
    private final String nom;
    private final double prix;
    private final boolean disponible;

    public Produit(String nom, double prix, boolean disponible) {
        this.nom = nom;
        this.prix = prix;
        this.disponible = disponible;
    }

    public String getNom() { return nom; }
    public double getPrix() { return prix; }
    public boolean isDisponible() { return disponible; }
}
