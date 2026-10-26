public class ImporteurLignesLivresAudio extends Importeur {
    @Override
    protected Document creerDocument(String ligne) {
        String[] c = ligne.split(";");
        return new LivreAudio(c[0], c[2], Integer.parseInt(c[3]), Integer.parseInt(c[4]));
    }
}
