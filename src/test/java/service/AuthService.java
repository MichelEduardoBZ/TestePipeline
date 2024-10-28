package service;

import br.com.michel.lixo.dto.user.UserInsertDTO;
import br.com.michel.lixo.service.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class AuthService {
    private String email;
    private String password;
    UserInsertDTO userInsertDTO = new UserInsertDTO();
    public Response response;

    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();


    public void setLoginFields(String field, String value) {
        switch (field) {
            case "email" -> email = value;
            case "password" -> password = value;
            default -> throw new IllegalArgumentException("Campo inesperado: " + field);
        }
    }

    public void login(String endpoint) {
        String url = "http://localhost:8080" + endpoint;
        response = given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public Response getResponse() {
        return response;
    }

    public void setRegisterFields(String field, String value) {
        switch (field) {
            case "name" -> userInsertDTO.setName(value);
            case "email" -> userInsertDTO.setEmail(value);
            case "birthdate" -> userInsertDTO.setBirthdate(LocalDate.parse(value));
            case "password" -> userInsertDTO.setPassword(value);
            default -> throw new IllegalArgumentException("Campo inesperado: " + field);
        }
    }

    public void register(String endpoint) {
        String url = "http://localhost:8080" + endpoint;
        response = given()
                .contentType(ContentType.JSON)
                .body(userInsertDTO)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public ResourceNotFoundException getErrorResponse() {
        return response.getBody().as(ResourceNotFoundException.class);
    }
}
