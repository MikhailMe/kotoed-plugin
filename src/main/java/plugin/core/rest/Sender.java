package plugin.core.rest;

import io.vertx.core.MultiMap;
import org.apache.http.HttpResponse;
import io.vertx.core.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.io.IOException;
import java.io.BufferedReader;

import static plugin.core.util.Address.*;

public class Sender extends BaseSender implements ISender {

    public Sender(@NotNull String configuration) {
        super(configuration);
    }

    public String getWhoAmI() {
        try {
            HttpResponse whoAmI = post(urlWhoAmI, jsonMyself, cookie);
            BufferedReader rd = getReader(whoAmI);
            return rd.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NotNull
    public String signIn(@NotNull final String denizen,
                         @NotNull final String password) {
        this.jsonMyself = new JsonObject()
                .put(DENIZEN_ID, denizen)
                .put(PASSWORD, password)
                .toString();
        return Objects.requireNonNull(setCookie());
    }

    public String signUp(@NotNull final String denizen,
                          @NotNull final String password) {
        this.jsonMyself = new JsonObject()
                .put(DENIZEN_ID, denizen)
                .put(PASSWORD, password)
                .toString();
        HttpResponse signUpResponse = post(urlSignUp, jsonMyself, cookie);
        try {
            BufferedReader rd = getReader(signUpResponse);
            return rd.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSuccessSignUp(@NotNull final String signUpResponse) {
        return signUpResponse.contains("true");
    }

    @NotNull
    public MultiMap getHeaders() {
        return MultiMap.caseInsensitiveMultiMap().add(FIELD_COOKIE, cookie);
    }

}
