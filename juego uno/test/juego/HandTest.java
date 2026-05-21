package juego;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void testJugarCartaIndiceInvalido() {
        Hand mano = new Hand();

        Card resultado = mano.jugarCarta(0);

        assertNull(resultado);
    }
}
