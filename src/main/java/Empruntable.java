public interface Empruntable {
    // checked : la signature engage TOUTES les implementations
    void emprunter() throws DocumentIndisponibleException;
    void rendre();
    boolean estDisponible();
}
