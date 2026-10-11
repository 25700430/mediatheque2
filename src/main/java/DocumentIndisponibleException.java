/** Le document existe mais ne peut pas etre emprunte. */
public class DocumentIndisponibleException extends MediathequeException {
    /** Cas principal : le document est deja emprunte. */
    public DocumentIndisponibleException(String titre) {
        super("Document deja emprunte : " + titre);
    }

    /** Autre cas d'indisponibilite (ex. : consultation sur place uniquement). */
    public DocumentIndisponibleException(String titre, String motif) {
        super("Document indisponible a l'emprunt (" + motif + ") : " + titre);
    }
}
