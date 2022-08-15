package exceptions;

public class PrecoInvalidoException extends IllegalArgumentException {

    public PrecoInvalidoException() {
        super("Preco invalido.");
    }
}
