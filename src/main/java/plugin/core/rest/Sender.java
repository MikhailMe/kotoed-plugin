package plugin.core.rest;

import io.vertx.core.MultiMap;
import org.apache.http.HttpResponse;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.io.IOException;
import java.io.BufferedReader;

import static plugin.core.util.Address.*;

public class Sender extends BaseSender implements ISender{

    public Sender(@NotNull String configuration) {
        super(configuration);
    }

    public String getWhoAmI() {
        return jsonMyself == null ? null : Objects.requireNonNull(getWhoAmI(jsonMyself));
    }

    private String getWhoAmI(@NotNull final String jsonMyself) {
        try {
            HttpResponse whoAmI = post(urlWhoAmI, jsonMyself);
            BufferedReader rd = getReader(whoAmI);
            return IOUtils.toString(rd);
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
        return Objects.requireNonNull(getCookie(jsonMyself));
    }

    public void signUp(@NotNull final String denizen,
                       @NotNull final String password) {
        this.jsonMyself = new JsonObject()
                .put(DENIZEN_ID, denizen)
                .put(PASSWORD, password)
                .toString();
        post(urlSignUp, jsonMyself);
    }

    @NotNull
    public MultiMap getHeaders(String cookie) {
        return MultiMap.caseInsensitiveMultiMap().add(FIELD_COOKIE, cookie);
    }


}
