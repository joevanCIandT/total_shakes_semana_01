package pedido;

import ingredientes.Ingrediente;

import java.util.*;

public class Cardapio{



    public Cardapio(TreeMap<Ingrediente, Double> precos) {

        this.precos = precos;
    }

    private TreeMap<Ingrediente,Double> precos;

    public Cardapio(){
        this.precos= new TreeMap<Ingrediente, Double>(new sortKey());
    }


    public TreeMap<Ingrediente, Double> getPrecos(){

        return precos;
    }

    public void adicionarIngrediente(Ingrediente ingrediente,Double preco){
        if(preco > 0){
            precos.put(ingrediente, preco);
        }else{
            throw new IllegalArgumentException("Preco invalido.");
        }

    }

    public boolean atualizarIngrediente(Ingrediente ingrediente,Double preco){
        if(precos.containsKey(ingrediente)){
            if(preco > 0){
                precos.put(ingrediente, preco);
                return true;
            }else{
                throw new IllegalArgumentException("Preco invalido.");
            }
        }else{

            throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
        }
    }

    public boolean removerIngrediente(Ingrediente ingrediente){
        if(precos.containsKey(ingrediente)){
            precos.remove(ingrediente);
            return true;
        }else{

            throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
        }

    }

    public Double buscarPreco(Ingrediente ingrediente){
        if(precos.containsKey(ingrediente)){
            return precos.get(ingrediente);
        }else{

            throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
        }

    }

    @Override
    public String toString() {
        return this.precos.toString();
    }

}
