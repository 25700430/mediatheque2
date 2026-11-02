package minishop;

public class MultifonctionBureau implements Imprimante, Numeriseur, Telecopieur {
    @Override
    public void imprimer(String document) {
        System.out.println("Impression : " + document);
    }

    @Override
    public void numeriser(String document) {
        System.out.println("Numérisation : " + document);
    }

    @Override
    public void faxer(String document) {
        System.out.println("Fax : " + document);
    }
}
