package juego;

public class Card {
    
    private String color;
    private int numero;

    public Card(String color, int numero) {
        this.color = color;
        this.numero = numero;
    }

    public String getColor() {
        return color;
    }

    public int getNumero() {
        return numero;
    }
    
    public void setColor(String color) {
        this.color = color;
    }

    
    
    @Override
    public String toString() {

        if (numero == 10) return color + " +2";
        if (numero == 11) return color + " salto";
        if (numero == 12) return color + " reversa";
        if (numero == 13) return "comodin";
        if (numero == 14) return "roba4";

        return color + " " + numero;
    }
}
