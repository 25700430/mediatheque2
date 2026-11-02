package minishop;

public final class Carre implements Forme {
    private final double cote;

    public Carre(double cote) {
        this.cote = cote;
    }

    @Override
    public double aire() {
        return cote * cote;
    }
}
