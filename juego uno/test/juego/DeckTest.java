package juego;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    void testDeckVacio() {
        Deck deck = new Deck();

        while (deck.robaCarta() != null) {}

        assertNull(deck.robaCarta());
    }
}
