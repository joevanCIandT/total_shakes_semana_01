package pedido;

import ingredientes.Ingrediente;

import java.util.Comparator;

public class sortKey implements Comparator<Ingrediente> {
    @Override
    public int compare(Ingrediente ingrediente, Ingrediente t1) {
        return ingrediente.obterTipo().toString().compareTo(t1.obterTipo().toString());
    }
}
