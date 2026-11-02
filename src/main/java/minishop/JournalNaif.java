package minishop;

public class JournalNaif {
    private static JournalNaif instance;

    private JournalNaif() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static JournalNaif getInstance() {
        if (instance == null) {
            instance = new JournalNaif();
        }
        return instance;
    }
}
