package armazem;

import exceptions.IngredienteJaCadastradoException;
import exceptions.IngredienteNaoEncontradoException;
import exceptions.IngredienteQuantidadeInvalidaException;
import ingredientes.Ingrediente;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Armazem implements Serializable {

        private final TreeMap<Ingrediente,Integer> estoque;

    public Armazem(){
        this.estoque= new TreeMap<>();
    }


    public TreeMap<Ingrediente, Integer> getEstoque(){
        return estoque;
    }

    public void cadastrarIngredienteEmEstoque(Ingrediente ingrediente){
        if(estoque.containsKey(ingrediente)){
            throw new IngredienteJaCadastradoException();
        }else{
            estoque.put(ingrediente, 0);
        }
    }

    public void descadastrarIngredienteEmEstoque(Ingrediente ingrediente){
        if(estoque.containsKey(ingrediente)){
            estoque.remove(ingrediente);
        }else{
            throw new IngredienteNaoEncontradoException();
        }
    }

    public void adicionarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, Integer quantidade){
        if(quantidade <= 0){
            throw new IngredienteQuantidadeInvalidaException();
        }else{
            if(estoque.containsKey(ingrediente)){
                estoque.replace(ingrediente, estoque.get(ingrediente) + quantidade);
            }else {
                throw new IngredienteNaoEncontradoException();
            }
        }

    }

    public void reduzirQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, Integer quantidade){
        if(quantidade <= 0){
            throw new IngredienteQuantidadeInvalidaException();
        }else{
            if(estoque.containsKey(ingrediente)){
                if(estoque.get(ingrediente) == quantidade ){
                    estoque.remove(ingrediente);
                }else{
                    estoque.replace(ingrediente, estoque.get(ingrediente) - quantidade);
                }

            }else {
                throw new IngredienteNaoEncontradoException();
            }
        }
    }

    public Integer consultarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente){
        if(!estoque.containsKey(ingrediente)){
            throw new IngredienteNaoEncontradoException();
        }else {
            return estoque.get(ingrediente);
        }


    }

    public void geraArquivo(){

        String filename = "armazem.txt";

        try
        {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            for(Map.Entry<Ingrediente,Integer> entry: estoque.entrySet()){
                try {
                    out.writeObject(entry.getKey().obterTipo().toString());
                    out.writeObject(entry.getValue().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            out.close();
            file.close();

            System.out.println("Igrediente has been serialized");

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }


    }


}
