package plugin.gui.Utils;

import io.vertx.core.MultiMap;
import com.intellij.openapi.util.Key;

public class PsiKeys {

    public static final Key<String> PSI_KEY_COOKIE = Key.create("Cookie");
    public static final Key<MultiMap> PSI_KEY_HEADERS = Key.create("Headers");

}
