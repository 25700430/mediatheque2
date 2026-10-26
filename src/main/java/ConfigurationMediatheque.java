public class ConfigurationMediatheque {
    private final int dureeEmpruntJours;
    private final int nbMaxEmprunts;

    public ConfigurationMediatheque(int dureeEmpruntJours, int nbMaxEmprunts) {
        this.dureeEmpruntJours = dureeEmpruntJours;
        this.nbMaxEmprunts = nbMaxEmprunts;
    }

    public int getDureeEmpruntJours() {
        return dureeEmpruntJours;
    }

    public int getNbMaxEmprunts() {
        return nbMaxEmprunts;
    }
}
