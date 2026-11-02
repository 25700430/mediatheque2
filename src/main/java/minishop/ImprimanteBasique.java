package minishop;

public class ImprimanteBasique implements Imprimante {
    @Override
    public void imprimer(String document) {
        System.out.println("Impression : " + document);
    }
}
