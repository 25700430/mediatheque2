package minishop;

import java.util.List;

public interface Journal {
    void ecrire(String message);

    List<String> getMessages();
}
