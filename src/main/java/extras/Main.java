package extras;

import extras.PedidoInterface;
import pedido.Pedido;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main implements PedidoInterface {

    private  ArrayList<Pedido> pedidos = new ArrayList<>();

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }


    @Override
    public void adicionaPedido(Pedido pedido) {
        pedidos.add(pedido);
    }


    public void geraArquivoPedidos(){

        String filename = "pedidos.txt";

        try
        {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);


            pedidos.forEach((p) -> {
                try {
                    out.writeObject(p.getItens().toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            out.close();
            file.close();

            System.out.println("Pedido has been serialized");

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }


    }
}
