package at.study.redmine.api.client;

import io.qameta.allure.Step;

public interface RestApiClient {
    @Step("Создание API-запроса {1}")
    RestResponse execute(RestRequest request);
}
