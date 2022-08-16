package extras;

import pedido.Pedido;

import java.io.Serializable;

public interface PedidoInterface extends Serializable {

    void adicionaPedido(Pedido pedido);

    void geraArquivoPedidos();


}
