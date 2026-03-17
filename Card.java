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

    public boolean esJugableSobre(Card otra) {
        if (otra == null) return true;

        
        if (this.numero == 13 || this.numero == 14) {
            return true;
        }

        return this.color.equals(otra.getColor()) || this.numero == otra.getNumero();
    }

    @Override
    public String toString() {
        switch(numero){
            case 10: return "[" + color + " +2]";
            case 11: return "[" + color + " SALTO]";
            case 12: return "[" + color + " REVERSA]";
            case 13: return "[COMODÍN]";
            case 14: return "[ROBA 4]";
            default: return "[" + color + " " + numero + "]";
        }
    }
}
