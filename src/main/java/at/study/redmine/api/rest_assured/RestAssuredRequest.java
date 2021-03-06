package at.study.redmine.api.rest_assured;

import at.study.redmine.api.client.RestMethod;
import at.study.redmine.api.client.RestRequest;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RestAssuredRequest implements RestRequest {
    @NonNull
    private final RestMethod method;
    @NonNull
    private final String uri;

    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParameters = new HashMap<>();
    private final String body;

    public RestAssuredRequest(RestMethod method, String uri, Map<String, String> headers, Map<String, String> queryParameters, String body) {
        this.method = method;
        this.uri = uri;
        if (headers != null) {
            this.headers = headers;
        } // если при создании объекта запроса
        // RestAssuredRequest в этом конструкторе, придет null в параметрах конструктора
        // , то в header присвоется
        // значение по умолчанияю,
        // то есть пустой HashMap который объявлен в поле headers
        if (queryParameters != null) {
            this.queryParameters = queryParameters;
        }
        this.body = body;
    }

}
