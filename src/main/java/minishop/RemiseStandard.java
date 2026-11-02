package minishop;

public class RemiseStandard implements RegleRemise {
    @Override
    public double appliquer(double total) {
        return total;
    }
}
