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

public class MermaidsUpdateChecker {
    public static String checkForUpdate(){
        try{
            //URL url = new URL("https://api.mermaids.dev/versions/mermaids/release/");
            URL url = new URL("https://api.mermaids.dev/versions/mermaids/pre-release/alpha/2.0.0/");

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    if(line.contains("<h1>{ version: ") && line.contains(" }</h1>")){
                        line = line.substring(line.indexOf("<h1>{ version: ") + 15);
                        line = line.substring(0, line.indexOf(" }</"));
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
        sendUpdateMessage(null, false);
    }

    public static void sendUpdateMessage(Type type){
        if(Type.StartUp.getValue() == type.getValue()) {
            String recentVersion = MermaidsUpdateChecker.checkForUpdate();
            if(!Mermaids.getVersion().equalsIgnoreCase(recentVersion)) {
                Mermaids.LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -= =");
                Mermaids.LOGGER.atInfo().log("The Mermaids mod version is outdated, Mermaids has released v" + recentVersion + ".");
            }

            Runnable updateCheckRunnable = new Runnable() {
                @Override
                public void run() {
                    sendUpdateMessage();
                }
            };

            HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(updateCheckRunnable, 15, 60*60*6, TimeUnit.SECONDS);
        }else{
            sendUpdateMessage();
        }
    }

    public static void sendUpdateMessage(Player player){
        sendUpdateMessage(player, true);
    }

    public static void sendUpdateMessage(@Nullable Player player, boolean sendToPlayer){
        String recentVersion = MermaidsUpdateChecker.checkForUpdate();
        if(!Mermaids.getVersion().equalsIgnoreCase(recentVersion)){

            /*    RELEASE UPDATE MESSAGE
            String translationId = "server.updateChecker.mermaids.release.message";
            Message versionMessage = Message.translation(translationId).param("version", recentVersion);

            Mermaids.LOGGER.atInfo().log(versionMessage.getAnsiMessage());

            if(sendToPlayer && player != null) {
                if (player.hasPermission("*") && Mermaids.getConfig().get().ifNewVersion()) {
                    player.sendMessage(versionMessage.color(Color.CYAN));
                }
            }
            DELETE EVERYTHING BELOW ON RELEASE OF 2.0.0, UPDATE URL LINK */

            String translationId = "server.updateChecker.mermaids.alpha.2.0.0.message";
            if(recentVersion.equalsIgnoreCase("released")) {
                translationId = "server.updateChecker.mermaids.alpha.2.0.0.released.message";
            }

            Message versionMessage = Message.translation(translationId).param("version", recentVersion);

            Mermaids.LOGGER.atInfo().log(versionMessage.getAnsiMessage());

            if(sendToPlayer && player != null) {
                if (player.hasPermission("*") && Mermaids.getConfig().get().ifNewVersion()) {
                    player.sendMessage(versionMessage.color(Color.CYAN));
                }
            }
        }
    }

    public enum Type {
        StartUp(0);

        private final int value;
        private Type(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    }
}
