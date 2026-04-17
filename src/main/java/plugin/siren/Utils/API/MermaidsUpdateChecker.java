package plugin.siren.Utils.API;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.events.AllWorldsLoadedEvent;
import plugin.siren.Mermaids;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MermaidsUpdateChecker {
    public static List<String> getVersionStrings(){
        try{
            URL url = new URL("https://api.mermaids.dev/versions/mermaids/release/");

            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();

            List<String> list = new ArrayList<>();

            try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))){
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    if(line.contains("<h1>{ version: ") && line.contains(" }</h1>")){
                        line = line.substring(line.indexOf("<h1>{ version: ") + 15);
                        line = line.substring(0, line.indexOf(" }</"));
                        list.add(line);
                    }

                    if(line.contains("<h3>{ ignore-version: ") && line.contains(" }</h3>")){
                        line = line.substring(line.indexOf("<h3>{ ignore-version: ") + 22);
                        line = line.substring(0, line.indexOf(" }</"));
                        list.add(line);
                    }
                }

                return list;
            }
        } catch (Exception e) {
            Mermaids.LOGGER.atInfo().log("Exception with MermaidsUpdateChecker : " + e.toString());

            List<String> list = new ArrayList<>();
            list.add(Mermaids.getVersion());

            return list;
        }
    }

    public static void sendUpdateMessage(){
        sendUpdateMessage(null, false, Type.Default);
    }

    public static String sendUpdateMessage(Type type){
        if(Type.StartUp.getValue() == type.getValue()) {
            List<String> recentVersions = MermaidsUpdateChecker.getVersionStrings();

            boolean outDated = true;
            String latestVersion = recentVersions.getFirst();
            for(int i = 0; i < recentVersions.size(); i++){
                if(recentVersions.get(i).equalsIgnoreCase(Mermaids.getVersion())){
                    outDated = false;
                }
            }

            if(outDated) {
                Mermaids.LOGGER.atInfo().log("= =- -=- -=- -=- -=- -=- -=- -=- -= =");
                Mermaids.LOGGER.atInfo().log("The Mermaids mod version is outdated, Mermaids has released v" + latestVersion + ".");
            }

            Runnable updateCheckRunnable = new Runnable() {
                @Override
                public void run() {
                    sendUpdateMessage();
                }
            };

            Mermaids.get().getEventRegistry().registerGlobal(AllWorldsLoadedEvent.class, allWorldsLoadedEvent -> {
                HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(updateCheckRunnable, 3, 60*60*6, TimeUnit.SECONDS);
            });
        }else if(Type.InfoCmd.getValue() == type.getValue()){
            List<String> recentVersions = MermaidsUpdateChecker.getVersionStrings();

            if(recentVersions.isEmpty()){
                return Mermaids.getVersion();
            }else{
                return recentVersions.getFirst();
            }
        }else{
            sendUpdateMessage();
        }

        return null;
    }

    public static void sendUpdateMessage(Player player){
        sendUpdateMessage(player, true, Type.Default);
    }

    public static void sendUpdateMessage(Player player, Type type){
        sendUpdateMessage(player, true, type);
    }

    public static void sendUpdateMessage(@Nullable Player player, boolean sendToPlayer, Type type){
        List<String> recentVersions = MermaidsUpdateChecker.getVersionStrings();

        boolean outDated = true;
        String latestVersion = recentVersions.getFirst();
        for(int i = 0; i < recentVersions.size(); i++){
            if(recentVersions.get(i).equalsIgnoreCase(Mermaids.getVersion())){
                outDated = false;
            }
        }

        if(outDated){
            String translationId = "server.updateChecker.mermaids.release.message";
            Message versionMessage = Message.translation(translationId).param("version", latestVersion);

            if(Type.PlayerReadyEvent.getValue() != type.getValue()) {
                Mermaids.LOGGER.atInfo().log(versionMessage.getAnsiMessage());
            }

            if(sendToPlayer && player != null) {
                if ((player.hasPermission("*") || player.hasPermission("mermaids.admin")) && Mermaids.getConfig().get().ifNewVersion()) {
                    player.sendMessage(versionMessage.color(Color.CYAN).link("https://www.mermaids.dev/mermaids/curseforge/"));
                }
            }

        }
    }

    public enum Type {
        StartUp(0),
        InfoCmd(1),
        PlayerReadyEvent(2),
        Default(3);

        private final int value;
        private Type(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }
    }
}
