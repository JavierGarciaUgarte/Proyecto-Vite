package juego;

public class DiscardPile {

	    private Card cartaActual;

	    public void ponerCarta(Card carta) {
	        this.cartaActual = carta;
	    }

	    public Card getCartaActual() {
	        return cartaActual;
	    }

	    public boolean esCartaInicialValida(Card carta) {
	        return carta.getNumero() != 13 && carta.getNumero() != 14;
	    }
	}