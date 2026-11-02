package minishop;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class JournalEnumTest {
    @Test
    void enumFournitUneInstanceUnique() {
        assertSame(JournalEnum.INSTANCE, JournalEnum.valueOf("INSTANCE"));
    }
}
