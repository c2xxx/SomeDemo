package com.basedamo.httpserver;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenHui on 2016/7/1.
 */
public class UiUpdater {
    protected static List<Handler> clients = new ArrayList<>();

    public static void registerClient(Handler client) {
        if (!clients.contains(client)) {
            clients.add(client);
        }
    }

    public static void unregisterClient(Handler client) {
        while (clients.contains(client)) {
            clients.remove(client);
        }
    }

    public static void updateClients() {

        for (Handler client : clients) {
            client.sendEmptyMessage(MESSAGES.UPDATE_UI);
        }
    }
}