package minishop;

public class RemisePremium implements RegleRemise {
    @Override
    public double appliquer(double total) {
        return total * 0.90;
    }
}
