import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseApi {

    private static final int TIMEOUT_MS = 10_000;

    private static final RestAssuredConfig CONFIG = RestAssuredConfig
            .config()
            .httpClient(HttpClientConfig.httpClientConfig()
                    .setParam("http.connection.timeout", TIMEOUT_MS)
                    .setParam("http.socket.timeout", TIMEOUT_MS));

    protected RequestSpecification request() {
        return given().config(CONFIG);
    }
}