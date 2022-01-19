package hu.genesys.homework.bold360.api.helper;

import com.google.gson.Gson;
import hu.genesys.homework.bold360.api.model.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class ApiHelper {

    private final Logger logger = LoggerFactory.getLogger(ApiHelper.class);

    public User getFirstUser() throws IOException {
        return getUsers()[0];
    }

    private User[] getUsers() throws IOException {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/users")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();
            User[] users = gson.fromJson(response.body().string(), User[].class);

            Arrays.stream(users).forEach(user -> logger.info(user.getName() + " | " + user.getEmail()));

            return users;
        }
    }
}
