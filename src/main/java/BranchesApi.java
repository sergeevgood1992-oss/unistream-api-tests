import io.restassured.response.Response;

public class BranchesApi extends BaseApi {

    private static final String BASE_PATH = "/api/v1/poses";

    public Response searchBranches(String filterTerm, int rows) {
        return request()
                .queryParam("filter.term", filterTerm)
                .queryParam("rows", rows)
                .when()
                .get(BASE_PATH)
                .then()
                .extract()
                .response();
    }

    public String getFirstBranchAddress(Response response) {
        return response.jsonPath().getString("items[0].address");
    }
}