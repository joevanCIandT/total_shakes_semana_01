package pedido;

import ingredientes.Adicional;
import ingredientes.Ingrediente;

import java.util.Comparator;

public class comparatorAdicional implements Comparator<Adicional> {


    @Override
    public int compare(Adicional adicional, Adicional t1) {
        return adicional.obterTipo().toString().compareTo(t1.obterTipo().toString());
    }
}
