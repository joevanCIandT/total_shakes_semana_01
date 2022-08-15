package exceptions;

public class IngredienteException extends IllegalArgumentException {
    public IngredienteException() {
        super("Ingrediente nao existe no cardapio.");

    }
}
