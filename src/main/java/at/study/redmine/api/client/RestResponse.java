package at.study.redmine.api.client;

import java.util.Map;

/**
 * что должен уметь делать респонс?
 * какую информацию он должен позволять получать из респонса?
 */

public interface RestResponse {

    int getStatusCode();

    Map<String, String> getHeaders();

    String getPayLoad();

    <T> T getPayload(Class<T> clazz);

}
