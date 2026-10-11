/** Aucun document ne porte ce titre. */
public class DocumentIntrouvableException extends MediathequeException {
    public DocumentIntrouvableException(String titre) {
        super("Aucun document au titre : " + titre);
    }
}
