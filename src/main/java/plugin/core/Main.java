package plugin.core;

import io.vertx.core.MultiMap;
import plugin.core.rest.Sender;
import plugin.core.eventbus.Informator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String configuration = "GLOBAL";
        String localDenizen = "punsh";
        String localPassword = "punsh";

        Sender sender = new Sender(configuration);
        String cookie = sender.signIn(localDenizen, localPassword);

        MultiMap headers = sender.getHeaders(cookie);

        Informator informator = new Informator(headers);

        informator.getCourses();



    }

}
