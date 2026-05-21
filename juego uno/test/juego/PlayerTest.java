package juego;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void testJugadorHumanoYBot() {
        Player humano = new Player("Humano", true);
        Player bot = new Player("Bot", false);

        assertTrue(humano.esHumano());
        assertFalse(bot.esHumano());
    }
}
