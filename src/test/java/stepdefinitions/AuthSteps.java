package stepdefinitions;

import br.com.michel.lixo.service.ResourceNotFoundException;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import service.AuthService;

import java.util.List;
import java.util.Map;

public class AuthSteps {
    AuthService authService = new AuthService();

    @Dado("que eu tenha os seguintes dados de login:")
    public void queEuTenhaOsSeguintesDadosDeLogin(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            authService.setLoginFields(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string}")
    public void euEnviarARequisiçãoParaOEndpoint(String endpoint) {
        authService.login(endpoint);
    }

    @Então("o status code da resposta deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, authService.getResponse().statusCode());
    }

    @Dado("que eu tenha os seguintes dados do usuário:")
    public void queEuTenhaOsSeguintesDadosDoUsuario(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            authService.setRegisterFields(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de registro")
    public void euEnviarARequisiçãoParaOEndpointDeRegistro(String endpoint) {
        authService.register(endpoint);
    }

    @E("o corpo de resposta de erro da api deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeveRetornarAMensagem(String message) {
        ResourceNotFoundException errorMessageModel = authService.getErrorResponse();
        Assert.assertEquals(message, errorMessageModel.getMessage());
    }

    @E("o corpo de resposta de erro da API deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaAPIDeveRetornarAMensagem(String arg0) {}
}
