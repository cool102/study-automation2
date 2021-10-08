package at.study.redmine.api.rest_assured;

import at.study.redmine.api.client.RestResponse;
import io.restassured.http.Header;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

import static at.study.redmine.api.rest_assured.GsonProvider.GSON;

@Getter
public class RestAssuredResponse implements RestResponse {

    private int statusCode;
    private Map<String, String> headers;
    private String payload;

    public RestAssuredResponse(Response response) {
        this.statusCode = response.getStatusCode();
        this.headers = response.getHeaders().asList() //List<Headers> -> Map<String,String>
                .stream().collect(Collectors.toMap(Header::getName, Header::getValue));
        this.payload = response.getBody().asString();
    }

    @Override
    public String getPayLoad() {
        return payload;
    }

    @Override
    public <T> T getPayload(Class<T> clazz) {

        return GSON.fromJson(payload, clazz);
    }

}
