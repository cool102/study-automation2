package at.study.redmine.api.client;

import java.util.Map;

/**
 * что должен уметь делать реквест?
 * какую информацию он должен позволять получать из реквеста
 */

public interface RestRequest {
    RestMethod getMethod();

    String getUri();

    Map<String, String> getHeaders();

    Map<String, String> getQueryParameters();

    String getBody();
}
