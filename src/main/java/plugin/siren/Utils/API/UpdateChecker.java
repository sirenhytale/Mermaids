package plugin.siren.Utils.API;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import plugin.siren.Mermaids;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

public class UpdateChecker {
    public static String checkForUpdate(){
        try{
            URL url = new URL("https://api.mermaids.dev/versions/mermaids/release/");

            if(!Mermaids.ifVersion1()){
                url = new URL("https://api.mermaids.dev/versions/mermaids/alpha/2.0.0/");
            }

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    if(line.contains("<h1>") && line.contains("</h1>")){
                        line = line.substring(line.indexOf("<h1>") + 4);
                        line = line.substring(0, line.indexOf("</"));
                        return line;
                    }
                }
            }
        } catch (Exception e) {
            return Mermaids.getVersion();
        }

        return Mermaids.getVersion();
    }

    public static void sendUpdateMessage(){
        sendUpdateMessage(null, false, false);
    }

    public static void sendUpdateMessage(boolean startUp){
        sendUpdateMessage(null, false, startUp);
    }

    public static void sendUpdateMessage(Player player){
        sendUpdateMessage(player, true, false);
    }

    public static void sendUpdateMessage(@Nullable Player player, boolean sendToPlayer, boolean startUp){
        String recentVersion = UpdateChecker.checkForUpdate();
        if(!Mermaids.getVersion().equalsIgnoreCase(recentVersion)){
            if(startUp) {
                Mermaids.LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -= =");
            }

            if(Mermaids.ifVersion1()) {
                String translationId = "server.updateChecker.mermaids.release.message";
                Message versionMessage = Message.translation(translationId).param("new-version", recentVersion);

                Mermaids.LOGGER.atInfo().log(versionMessage.getAnsiMessage());

                Runnable updateCheckRunnable = new Runnable() {
                    @Override
                    public void run() {
                        UpdateChecker.sendUpdateMessage();
                    }
                };

                if(startUp) {
                    HytaleServer.SCHEDULED_EXECUTOR.schedule(updateCheckRunnable, 15, TimeUnit.SECONDS);
                }

                if(sendToPlayer && player != null) {
                    if (player.hasPermission("*") && Mermaids.getConfig().get().ifNewVersion()) {
                        player.sendMessage(versionMessage.color(Color.CYAN));
                    }
                }
            }else{
                if(!recentVersion.equalsIgnoreCase("released")) {
                    String translationId = "server.updateChecker.mermaids.alpha.2.0.0.message";
                    Message versionMessage = Message.translation(translationId).param("new-version", recentVersion);

                    Mermaids.LOGGER.atInfo().log(versionMessage.getAnsiMessage());

                    Runnable updateCheckRunnable = new Runnable() {
                        @Override
                        public void run() {
                            UpdateChecker.sendUpdateMessage();
                        }
                    };

                    if(startUp) {
                        HytaleServer.SCHEDULED_EXECUTOR.schedule(updateCheckRunnable, 15, TimeUnit.SECONDS);
                    }

                    if(sendToPlayer && player != null) {
                        if (player.hasPermission("*") && Mermaids.getConfig().get().ifNewVersion()) {
                            player.sendMessage(versionMessage.color(Color.CYAN));
                        }
                    }
                }else{
                    String translationId = "server.updateChecker.mermaids.alpha.2.0.0.released.message";
                    Message versionMessage = Message.translation(translationId).param("new-version", recentVersion);

                    Mermaids.LOGGER.atInfo().log(versionMessage.getAnsiMessage());

                    Runnable updateCheckRunnable = new Runnable() {
                        @Override
                        public void run() {
                            UpdateChecker.sendUpdateMessage();
                        }
                    };

                    if(startUp) {
                        HytaleServer.SCHEDULED_EXECUTOR.schedule(updateCheckRunnable, 15, TimeUnit.SECONDS);
                    }

                    if(sendToPlayer && player != null) {
                        if (player.hasPermission("*") && Mermaids.getConfig().get().ifNewVersion()) {
                            player.sendMessage(versionMessage.color(Color.CYAN));
                        }
                    }
                }
            }
        }
    }
}
