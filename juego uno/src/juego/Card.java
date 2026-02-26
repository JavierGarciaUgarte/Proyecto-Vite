package juego;

public class Card {
	
	private String color;
	private int numero;
	
	public Card(String color2, int i) {
		// TODO Auto-generated constructor stub
	}

	public String getcolor() {
		return color;
	}
	
	public int getNumero() {
		return numero;
	}

	public boolean esJugableSobre(Card otra) {
		if (otra == null)return true;
		return this.color.equals(otra.color) || this.numero ==0;
	}

	@Override
	public String toString() {
		return "Card [color=" + color + ", numero=" + numero + "]";
	}
	
}
    