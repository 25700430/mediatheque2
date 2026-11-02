package minishop;

public final class Rectangle implements Forme {
    private final double largeur;
    private final double hauteur;

    public Rectangle(double largeur, double hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    @Override
    public double aire() {
        return largeur * hauteur;
    }
}
