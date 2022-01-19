package hu.genesys.homework.bold360.api;

import hu.genesys.homework.bold360.api.helper.ApiHelper;
import hu.genesys.homework.bold360.api.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestApiTest {

    private final ApiHelper apiHelper = new ApiHelper();

    @Test
    public void testFirstUserEmail() throws Exception {
        User firstUser = apiHelper.getFirstUser();
        Assertions.assertTrue(firstUser.getEmail().contains("@"));
    }
}
