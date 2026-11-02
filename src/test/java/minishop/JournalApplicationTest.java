package minishop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class JournalApplicationTest {
    @Test
    void getInstanceRetourneToujoursLaMemeInstance() {
        assertSame(JournalApplication.getInstance(), JournalApplication.getInstance());
    }

    @Test
    void centThreadsPeuventEcrireChacunUnMessage() throws InterruptedException {
        Journal journal = JournalApplication.getInstance();
        int nombreInitial = journal.getMessages().size();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            int numero = i;
            Thread thread = new Thread(() -> journal.ecrire("Message " + numero));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(nombreInitial + 100, journal.getMessages().size());
    }
}
