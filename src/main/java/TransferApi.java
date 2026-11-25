import io.restassured.response.Response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TransferApi extends BaseApi {

    private static final String BASE_PATH = "/api/v2/transfer/calculate";

    public Response calculateCommission(int senderBankId,
                                        String countryCode,
                                        String acceptedCurrency,
                                        String withdrawCurrency,
                                        BigDecimal amount) {

        // recipientBankId не передаём: перевод безадресный
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("senderBankId", senderBankId);
        requestBody.put("countryCode", countryCode);
        requestBody.put("acceptedCurrency", acceptedCurrency);
        requestBody.put("withdrawCurrency", withdrawCurrency);
        requestBody.put("amount", amount);

        return request()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASE_PATH)
                .then()
                .extract()
                .response();
    }

    public BigDecimal getCommissionFromResponse(Response response) {
        return response.jsonPath()
                .getObject("fees[0].acceptedTotalFee", BigDecimal.class);
    }
}