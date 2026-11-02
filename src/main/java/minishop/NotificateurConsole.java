package minishop;

public class NotificateurConsole implements Notificateur {
    @Override
    public void confirmer(String email, double total) {
        System.out.println("Email a " + email + " : total = " + total);
    }
}
