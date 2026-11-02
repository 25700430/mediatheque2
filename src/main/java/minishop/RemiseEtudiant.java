package minishop;

public class RemiseEtudiant implements RegleRemise {
    @Override
    public double appliquer(double total) {
        return total * 0.85;
    }
}
