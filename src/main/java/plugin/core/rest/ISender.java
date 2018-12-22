package plugin.core.rest;

import io.vertx.core.MultiMap;
import org.jetbrains.annotations.NotNull;

public interface ISender {

    String getWhoAmI();

    String signIn(@NotNull final String denizen, @NotNull final String password);

    String signUp(@NotNull final String denizen, @NotNull final String password);

    MultiMap getHeaders();
}
