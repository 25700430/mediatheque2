/** Une revue "est un" document mais ne "sait pas" etre empruntee. */
public class Revue extends Document {
    private final int numero;

    public Revue(String titre, int annee, int numero) {
        super(titre, annee);
        this.numero = numero;
    }

    @Override
    public String descriptionCourte() {
        return "[Revue] " + titre + " (" + annee + "), n°" + numero;
    }
}
