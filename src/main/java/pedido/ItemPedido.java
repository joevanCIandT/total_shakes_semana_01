package pedido;

import ingredientes.Adicional;
import produto.Shake;

public class ItemPedido {
    private final Shake shake;
    private int quantidade;

    private double valor;

    public ItemPedido(Shake shake, int quantidade) {
        this.shake = shake;
        this.quantidade = quantidade;
    }

    public Shake getShake() {
        return shake;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }



    public double calculaValorBase(Cardapio cardapio){
       return  cardapio.buscarPreco(this.getShake().getBase()) * this.getShake().getTipoTamanho().multiplicador;
    }

    public double calculaValorAdicionais(Cardapio cardapio) {
        double totalAdicional = 0.0;
        for(Adicional adicional : this.getShake().getAdicionais()){
            totalAdicional = totalAdicional + cardapio.buscarPreco(adicional);
        }

        return totalAdicional;
    }

    @Override
    public String toString() {
        return this.shake + " / x" + this.quantidade;
    }
}
