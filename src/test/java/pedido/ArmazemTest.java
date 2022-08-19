package pedido;

import exceptions.IngredienteQuantidadeInvalidaException;
import ingredientes.*;
import armazem.Armazem;
import exceptions.IngredienteJaCadastradoException;
import exceptions.IngredienteNaoEncontradoException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import tipo.TipoBase;
import tipo.TipoFruta;
import tipo.TipoTopping;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArmazemTest {

    static Armazem armazem;
    static Fruta fruta;
    static Base base;
    static Topping topping;


    @BeforeAll
    void beforeAll() {
        fruta = new Fruta(TipoFruta.MORANGO);
        base = new Base(TipoBase.IOGURTE);
        topping = new Topping(TipoTopping.MEL);
    }

    @BeforeEach
    void setup() {
        armazem = new Armazem();
    }




    @Test
    @DisplayName("Teste cadastro de ingrediente")
    public void test_cadastrarIngredienteEmEstoque(){
        armazem.cadastrarIngredienteEmEstoque(base);
        armazem.cadastrarIngredienteEmEstoque(fruta);
        armazem.cadastrarIngredienteEmEstoque(topping);

        assertAll(
                () -> assertEquals(0,
                        armazem.consultarQuantidadeDoIngredienteEmEstoque(base)),
                () -> assertEquals(0,
                        armazem.consultarQuantidadeDoIngredienteEmEstoque(fruta)),
                () -> assertEquals(0,
                        armazem.consultarQuantidadeDoIngredienteEmEstoque(topping))

        );

        IngredienteJaCadastradoException thrown = assertThrows(
                IngredienteJaCadastradoException.class,
                () -> armazem.cadastrarIngredienteEmEstoque(base)
        );

        assertTrue(thrown.getMessage().contains("Ingrediente já cadastrado."));

    }

    @ParameterizedTest
    @DisplayName("Teste consulta quantidade de ingrediente no estoque")
    @ValueSource(ints = {2,3,5})
    public void test_consultarQuantidadeDoIngredienteEmEstoque(Integer quantidade){

        armazem.cadastrarIngredienteEmEstoque(topping);
        assertEquals(0, armazem.consultarQuantidadeDoIngredienteEmEstoque(topping));

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(topping, quantidade);
        assertEquals(quantidade, armazem.consultarQuantidadeDoIngredienteEmEstoque(topping));

        armazem.descadastrarIngredienteEmEstoque(topping);
        assertNull(armazem.getEstoque().get(topping));

        IngredienteNaoEncontradoException thrown = assertThrows(
                IngredienteNaoEncontradoException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(topping)
        );

        assertTrue(thrown.getMessage().contains("Ingrediente não encontrado."));
    }




    @ParameterizedTest
    @CsvSource(value = {"1, 4", "2, 3"})
    public void test_adicionarQuantidadeDoIngredienteEmEstoque(Integer quantidade, Integer outraQuantidade){

        armazem.cadastrarIngredienteEmEstoque(fruta);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(fruta, quantidade);

        assertEquals(quantidade, armazem.getEstoque().get(fruta));

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(fruta, outraQuantidade);

        assertEquals(quantidade + outraQuantidade, armazem.getEstoque().get(fruta));

        armazem.descadastrarIngredienteEmEstoque(fruta);
        assertNull(armazem.getEstoque().get(fruta));

        IngredienteNaoEncontradoException thrown = assertThrows(
                IngredienteNaoEncontradoException.class,
                () ->  armazem.descadastrarIngredienteEmEstoque(fruta)
        );

        assertTrue(thrown.getMessage().contains("Ingrediente não encontrado."));

    }

    @Test
    public void test_descadastrarIngredienteEmEstoque(){
        armazem.cadastrarIngredienteEmEstoque(base);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(base, 1);

        assertEquals(1, armazem.getEstoque().get(base));

        armazem.descadastrarIngredienteEmEstoque(base);
        assertNull(armazem.getEstoque().get(base));


        IngredienteNaoEncontradoException thrown = assertThrows(
                IngredienteNaoEncontradoException.class,
                () ->  armazem.descadastrarIngredienteEmEstoque(base)
        );

        assertTrue(thrown.getMessage().contains("Ingrediente não encontrado."));

    }

    @ParameterizedTest
    @CsvSource(value = {"4, 1", "3, 2"})
    public void test_reduzirQuantidadeDoIngredienteEmEstoque(Integer quantidade, Integer outraQuantidade){

        armazem.cadastrarIngredienteEmEstoque(base);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(base, quantidade);

        assertEquals(quantidade, armazem.getEstoque().get(base));

        armazem.reduzirQuantidadeDoIngredienteEmEstoque(base, outraQuantidade);
        assertEquals(quantidade - outraQuantidade, armazem.getEstoque().get(base));

        armazem.reduzirQuantidadeDoIngredienteEmEstoque(base, quantidade - outraQuantidade);
        assertNull(armazem.getEstoque().get(base));

        IngredienteQuantidadeInvalidaException thrown = assertThrows(
                IngredienteQuantidadeInvalidaException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(base, 0)
        );

        assertTrue(thrown.getMessage().contains("Quantidade invalida."));

        IngredienteNaoEncontradoException thrown2 = assertThrows(
                IngredienteNaoEncontradoException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(base, 1)
        );

        assertTrue(thrown2.getMessage().contains("Ingrediente não encontrado."));


    }


    @Test
    public void test_buscandoIgredientesEmArquivo(){
        armazem.geraArquivo();
    }


}
