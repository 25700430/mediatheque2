public abstract class Document implements Comparable<Document> {
    private static int compteur = 0;

    protected final int id;
    protected final String titre;
    protected final int annee;

    protected Document(String titre, int annee) {
        this.id = ++compteur;
        this.titre = titre;
        this.annee = annee;
    }

    public int getId() { return id; }
    public String getTitre() { return titre; }
    public int getAnnee() { return annee; }

    public abstract String descriptionCourte();

    @Override
    public int compareTo(Document autre) {
        return titre.compareToIgnoreCase(autre.titre);
    }
    
}
