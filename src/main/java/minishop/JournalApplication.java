package minishop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class JournalApplication implements Journal {
    private final CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<>();

    private JournalApplication() {
    }

    private static class Holder {
        private static final JournalApplication INSTANCE = new JournalApplication();
    }

    public static JournalApplication getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void ecrire(String message) {
        messages.add(message);
    }

    @Override
    public List<String> getMessages() {
        return List.copyOf(messages);
    }
}
