import io.javalin.http.Context;
import org.example.controller.MensalistaController;
import org.example.model.Mensalista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MensalistaControllerTest {

    private final Context context = mock(Context.class);

    @BeforeEach
    public void limpar() {
        MensalistaController.mensalistas = new HashMap<>();
    }

    // Endpoint /hello
    @Test
    public void deveRetornar200QuandoBuscarMensagemEndpointHello() {
        MensalistaController.pegarResposta(context);

        verify(context).status(200);
        verify(context).result("Hello, Javalin!");
    }

    // Endpoint /mensalista, Metodo POST, Status Code 201
    @Test
    public void deveRetornar201QuandoCriarNovoMensalista() {
        Mensalista mensalista = new Mensalista("Mensalista 1");
        when(context.bodyAsClass(Mensalista.class)).thenReturn(mensalista);

        MensalistaController.inserirMensalista(context);

        verify(context).status(201);
        verify(context).json(mensalista);
        assertTrue(MensalistaController.mensalistas.containsValue(mensalista));
        assertTrue(mensalista.getMatricula() > 0);
    }

    // Endpoint /mensalista, Metodo POST, Status Code 400
    @Test
    public void deveRetornar400QuandoCriarMensalistaComNomeInvalido() {
        Mensalista mensalista = new Mensalista(null);
        when(context.bodyAsClass(Mensalista.class)).thenReturn(mensalista);

        MensalistaController.inserirMensalista(context);

        verify(context).status(400);
        verify(context).result("O nome do mensalista é obrigatório.");
    }

    // Endpoint /mensalista, Metodo GET com pathParam, Status Code 200
    @Test
    public void deveRetornar200QuandoBuscarMensalistaPorMatricula1() {
        Mensalista mensalista = new Mensalista(1, "Mensalista 1");
        MensalistaController.mensalistas.put("1", mensalista);
        when(context.pathParam("matricula")).thenReturn("1");

        MensalistaController.pegarMensalistaPorMatricula(context);

        verify(context).status(200);
        verify(context).json(mensalista);
    }

    // Endpoint /mensalista, Metodo GET com pathParam, Status Code 404
    @Test
    public void deveRetornar404QuandoBuscarMensalistaComMatriculaInvalida() {
        Mensalista mensalista = new Mensalista(1, "Mensalista 1");
        MensalistaController.mensalistas.put("1", mensalista);
        when(context.pathParam("matricula")).thenReturn("2");

        MensalistaController.pegarMensalistaPorMatricula(context);

        verify(context).status(404);
        verify(context).result("Mensalista não encontrado");
    }

    // Endpoint /mensalista, Metodo GET para todos mensalistas
    @Test
    public void deveRetornar200QuandoBuscarTodosMensalistas() {
        Mensalista mensalista = new Mensalista(1, "Mensalista 1");
        MensalistaController.mensalistas.put("1", mensalista);

        MensalistaController.pegarTodosMensalistas(context);

        verify(context).status(200);
        verify(context).json(MensalistaController.mensalistas);
        assertFalse(MensalistaController.mensalistas.isEmpty());
        assertEquals(1, MensalistaController.mensalistas.size());
    }
}
