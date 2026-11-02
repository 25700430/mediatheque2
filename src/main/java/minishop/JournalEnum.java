package minishop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public enum JournalEnum implements Journal {
    INSTANCE;

    private final CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<>();

    @Override
    public void ecrire(String message) {
        messages.add(message);
    }

    @Override
    public List<String> getMessages() {
        return List.copyOf(messages);
    }
}
