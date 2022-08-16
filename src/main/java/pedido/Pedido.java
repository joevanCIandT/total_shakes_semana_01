package pedido;

import ingredientes.*;
import produto.Shake;

import java.util.*;
import java.util.stream.Collectors;

public class Pedido{

    private int id;
    private  ArrayList<ItemPedido> itens;
    private Cliente cliente;

    public Pedido(int id, ArrayList<ItemPedido> itens,Cliente cliente){
        this.id = id;
        this.itens=itens;
        this.cliente=cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return this.itens;
    }

    public int getId(){
        return this.id;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public double calcularTotal(Cardapio cardapio){

        double totalPedido = 0.0;
        double totalItem;
        double totalAdicional;
        double totalBase;

        for(ItemPedido item : this.getItens()) {
            totalBase = calculaTotalBase(item, cardapio);
            totalAdicional = calculaTotalAdicional(item, cardapio);
            totalItem = totalBase + totalAdicional;
            totalPedido = totalItem * item.getQuantidade() + totalPedido;
        }
        return totalPedido;
    }

    private double calculaTotalAdicional(ItemPedido item, Cardapio cardapio) {
        double totalAdicional = 0.0;
        for(Adicional adicional : item.getShake().getAdicionais()){
            totalAdicional = totalAdicional + cardapio.buscarPreco(adicional);
        }

        return totalAdicional;
    }

    private double calculaTotalBase(ItemPedido item, Cardapio cardapio) {
        var base = item.getShake().getBase();
        return (cardapio.buscarPreco(base) * item.getShake().getTipoTamanho().multiplicador);
    }

    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado){
        for(ItemPedido item: this.getItens()){
            boolean teste = verificaItensIguais(item, itemPedidoAdicionado);
            if(teste){
                int valor = item.getQuantidade() + itemPedidoAdicionado.getQuantidade();
                item.setQuantidade(valor);
                return;
            }
        }
        itens.add(itemPedidoAdicionado);
    }


    private boolean compareAdicionais(List<Adicional> adicionais1, List<Adicional> adicionais2) {
        List<Adicional> sortedList1 = adicionais1.stream().sorted().collect(Collectors.toList());
        List<Adicional> sortedList2 = adicionais2.stream().sorted().collect(Collectors.toList());

        if(sortedList1.equals(sortedList2)){
            return true;
        }else{
            return false;
        }
    }

    public Boolean compareIngredientes(Ingrediente i, Ingrediente t){
        if(t.equals(i)){
            return true;
        }
        return false;
    }

    public void removeItemPedido(ItemPedido itemPedidoRemovido) {
        if(verificaSeItemExistePedido(itemPedidoRemovido)){
        }else{
            throw new IllegalArgumentException("Item nao existe no pedido.");
        }
    }

    private boolean verificaSeItemExistePedido(ItemPedido itemPedidoRemovido) {
        boolean teste;
        for(ItemPedido item: this.getItens()){
            teste = verificaItensIguais(item, itemPedidoRemovido);
            if(teste){
                if(item.getQuantidade() == 1){
                    this.itens.remove(item);
                    return true;
                }else{
                    int count = item.getQuantidade();
                    item.setQuantidade(count - 1);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verificaItensIguais(ItemPedido item, ItemPedido novoItem) {
        boolean base = (compareIngredientes(item.getShake().getBase(), novoItem.getShake().getBase()));
        boolean fruta = compareIngredientes(item.getShake().getFruta(), novoItem.getShake().getFruta());
        boolean topping = compareIngredientes(item.getShake().getTopping(), novoItem.getShake().getTopping());
        boolean adicionais = compareAdicionais(item.getShake().getAdicionais(), novoItem.getShake().getAdicionais());

        if(base && fruta && topping && adicionais) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
