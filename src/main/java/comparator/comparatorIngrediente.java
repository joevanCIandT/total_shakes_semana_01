package comparator;

import ingredientes.Ingrediente;

import java.util.Comparator;

public class comparatorIngrediente implements Comparator<Ingrediente> {

    @Override
    public int compare(Ingrediente ingrediente, Ingrediente t1) {
        return ingrediente.obterTipo().toString().compareTo(t1.obterTipo().toString());
    }
}
