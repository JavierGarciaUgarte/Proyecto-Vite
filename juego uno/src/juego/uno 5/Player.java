package juego;

public class Player {

    private String nombre;
    private Hand mano;
    private boolean esHumano;

    public Player(String nombre, boolean esHumano) {
        this.nombre = nombre;
        this.esHumano = esHumano;
        this.mano = new Hand();
    }

    public String getNombre() {
        return nombre;
    }

    public Hand getMano() {
        return mano;
    }

    public boolean esHumano() {
        return esHumano;
    }

    public void robarCarta(Deck deck) {
        mano.agregarCarta(deck.robaCarta());
    }

    //  ahora recibe RuleEngine
    public boolean tieneJugadaValida(Card cartaMesa, RuleEngine reglas) {
        return mano.tieneJugadaValida(cartaMesa, reglas);
    }
}
