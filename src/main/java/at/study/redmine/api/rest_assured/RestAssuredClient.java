package at.study.redmine.api.rest_assured;

import at.study.redmine.api.client.RestApiClient;
import at.study.redmine.api.client.RestMethod;
import at.study.redmine.api.client.RestRequest;
import at.study.redmine.api.client.RestResponse;
import at.study.redmine.model.user.Token;
import at.study.redmine.model.user.User;
import at.study.redmine.property.Property;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * какие api клиенты могут быть?
 * -неавторизованные
 * -api client пользователи
 * Создадим 2 спеки - используем 2 ctor-a
 * <p>
 * Чтобы можно было создать запрос нужна specification
 * cоответственно У нас две спеки(см.выше)
 */
public class RestAssuredClient implements RestApiClient {
    protected RequestSpecification specification;

    public RestAssuredClient() {
        this.specification = given()
                .baseUri(Property.getStringProperty("url"))
                .contentType(ContentType.JSON);
    }

    public RestAssuredClient(User user) {
        this();
        String token = user.getTokens().stream().filter(t -> t.getAction() == Token.TokenType.API)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("У пользователя нет API-токена"))
                .getValue();

        specification
                .header("X-Redmine-API-key", token);

    }

    /**
     * на базе RestRequest необходимо
     * собрать RestAssuerdRequest
     *
     * @param request
     * @return
     */
    @Override
    public RestResponse execute(RestRequest request) {
        RequestSpecification spec = given(specification)
                .queryParams(request.getQueryParameters())
                .headers(request.getHeaders());
        if (request.getBody() != null) {
            spec.body(request.getBody());
        }
        Response response = spec.log().all()
                .request(
                        toRestAssuredMethod(request.getMethod()),
                        request.getUri()
                );
        response.then().log().all();

        return new RestAssuredResponse(response);
    }

    private Method toRestAssuredMethod(RestMethod method) {
        return Method.valueOf(method.name());
    }

}
