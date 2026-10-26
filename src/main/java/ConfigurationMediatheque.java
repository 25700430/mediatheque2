public class ConfigurationMediatheque {
    private int dureeEmpruntJours = 21;
    private int nbMaxEmprunts = 5;

    private ConfigurationMediatheque() { }

    private static class Holder {
        private static final ConfigurationMediatheque INSTANCE = new ConfigurationMediatheque();
    }

    public static ConfigurationMediatheque getInstance() {
        return Holder.INSTANCE;
    }

    public int getDureeEmpruntJours() {
        return dureeEmpruntJours;
    }

    public int getNbMaxEmprunts() {
        return nbMaxEmprunts;
    }

    public void setDureeEmprunt(int jours) {
        this.dureeEmpruntJours = jours;
    }

    public void setNbMaxEmprunts(int n) {
        this.nbMaxEmprunts = n;
    }
}
