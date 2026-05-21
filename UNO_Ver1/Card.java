package juego;

public class Card {
	
	private String color;
	private int numero;
	
	public Card(String color2, int i) {
	    this.color = color2;
	    this.numero = i;
	}

	public String getcolor() {
		return color;
	}
	
	public int getNumero() {
		return numero;
	}

	public boolean esJugableSobre(Card otra) {
		if (otra == null)return true;
		return this.color.equals(otra.color) || this.numero == otra.numero;

	}

	@Override
	public String toString() {
		return "Card [color=" + color + ", numero=" + numero + "]";
	}
	
}