package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiTests {

    private Long userId;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @AfterEach
    public void tearDown() {
        // Remove o usuário de teste pelo ID
        if (userId != null) {
            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .delete("/user/" + userId)
                    .then()
                    .statusCode(204);
        }
    }

    @Test
    public void testCadastrarUsuarioComSucesso() {
        String requestBody = "{\"name\": \"Test User\", \"email\": \"testeUnitario@gmail.com\", \"birthdate\": \"2000-01-01\", \"password\": \"password1\"}";

        // Captura a resposta do POST e extrai o ID
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/auth/register")
                .then()
                .statusCode(201)
                .extract().response();

        // Extrai o ID do corpo da resposta
        userId = response.jsonPath().getLong("id");
    }

    @Test
    public void testLoginUsuarioComSucesso() {
        // Primeiro, registre o usuário
        testCadastrarUsuarioComSucesso();
        String loginBody = "{\"email\": \"testeUnitario@gmail.com\", \"password\": \"password1\"}";
        given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200);
    }

    @Test
    public void testErroCadastroEmailExistente() {
        testCadastrarUsuarioComSucesso();

        String requestBody = "{\"name\": \"Another User\", \"email\": \"testeUnitario@gmail.com\", \"birthdate\": \"2000-01-01\", \"password\": \"password2\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/auth/register")
                .then()
                .statusCode(400) // Espera-se o status 400
                .extract().response();

        // Verificar a mensagem de erro
        response.then().body("message", equalTo("User with e-mail exists!"));
    }

    @Test
    public void testErroLoginComCredenciaisInvalidas() {
        String loginBody = "{\"email\": \"testeUnitario@gmail.com\", \"password\": \"wrongpassword\"}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(403)
                .extract().response();

        response.then().body("message", equalTo("Access Denied"));
    }
}
