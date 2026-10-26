public class LivreAudio extends Livre {
    private final int dureeMinutes;

    public LivreAudio(String titre, String auteur, int annee, int dureeMinutes) {
        super(titre, annee, auteur, 0);
        this.dureeMinutes = dureeMinutes;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    @Override
    public String descriptionCourte() {
        return "[Livre audio] " + getTitre() + " (" + getAnnee() + "), "
                + getDureeMinutes() + " min";
    }
}
