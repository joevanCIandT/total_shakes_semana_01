package pedido;

import comparator.comparatorIngrediente;
import exceptions.IngredienteException;
import exceptions.PrecoInvalidoException;
import ingredientes.Ingrediente;

import java.util.*;

public class Cardapio{

    public Cardapio(TreeMap<Ingrediente, Double> precos) {

        this.precos = precos;
    }

    private TreeMap<Ingrediente,Double> precos;

    public Cardapio(){
        this.precos= new TreeMap<>(new comparatorIngrediente());
    }


    public TreeMap<Ingrediente, Double> getPrecos(){
        return precos;
    }

    public void adicionarIngrediente(Ingrediente ingrediente,Double preco){
        if(preco > 0){
            precos.put(ingrediente, preco);
        }else{
            throw new PrecoInvalidoException();
        }
    }

    public boolean atualizarIngrediente(Ingrediente ingrediente,Double preco){
        if(precos.containsKey(ingrediente)){
            if(preco > 0){
                precos.put(ingrediente, preco);
                return true;
            }else{
                throw new PrecoInvalidoException();
            }
        }else{

            throw new IngredienteException();
        }
    }

    public boolean removerIngrediente(Ingrediente ingrediente){
        if(precos.containsKey(ingrediente)){
            precos.remove(ingrediente);
            return true;
        }else{
            throw new IngredienteException();
        }
    }

    public Double buscarPreco(Ingrediente ingrediente){
        if(precos.containsKey(ingrediente)){
            return precos.get(ingrediente);
        }else{
            throw new IngredienteException();
        }
    }

    @Override
    public String toString() {
        return this.precos.toString();
    }

}
