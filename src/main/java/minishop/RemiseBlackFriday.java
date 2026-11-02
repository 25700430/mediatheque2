package minishop;

public class RemiseBlackFriday implements RegleRemise {
    @Override
    public double appliquer(double total) {
        return total * 0.70;
    }
}
