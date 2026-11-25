import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class UnistreamApiTest {

    private static final String BASE_URL = "https://slt-test.info.api.unistream.com";

    private static final int SENDER_BANK_ID = 10400;
    private static final String COUNTRY_ARMENIA = "ARM";
    private static final BigDecimal AMOUNT = new BigDecimal("1000");

    private static final String SEARCH_TERM = "Балашиха";
    private static final String EXPECTED_ADDRESS = "Московская область, Балашиха, Советская, 13";

    @BeforeAll
    static void setUpClass() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    void task1_calculateCommissionForAddresslessTransfer() {
        TransferApi transferApi = new TransferApi();

        Response response = transferApi.calculateCommission(
                SENDER_BANK_ID, COUNTRY_ARMENIA, "RUB", "RUB", AMOUNT);
        assertEquals(200, response.statusCode(),
                "Запрос должен выполниться успешно. Ответ: " + response.asString());

        BigDecimal commission = transferApi.getCommissionFromResponse(response);

        assertNotNull(commission, "Комиссия должна присутствовать в ответе");
        assertTrue(commission.signum() >= 0, "Комиссия не должна быть отрицательной");

        System.out.println("Задание 1: сумма комиссии = " + commission + " RUB");
    }

    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void task2_findNearestBranchInBalashikha() {
        BranchesApi branchesApi = new BranchesApi();

        Response response = branchesApi.searchBranches(SEARCH_TERM, 1);

        assertEquals(200, response.statusCode(),
                "Запрос должен выполниться успешно. Ответ: " + response.asString());

        String actualAddress = branchesApi.getFirstBranchAddress(response);

        System.out.println("Задание 2: найденный адрес = " + actualAddress);

        assertNotNull(actualAddress, "Адрес отделения не должен быть null");
        assertEquals(EXPECTED_ADDRESS, actualAddress,
                "Адрес ближайшего отделения не совпал с ожидаемым");
    }
}