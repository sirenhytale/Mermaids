package plugin.siren.Commands.Mermaids.Bug;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import plugin.siren.Contributions.k3kdude.DiscordWebhook;
import plugin.siren.Mermaids;
import plugin.siren.Utils.Github.GithubIgnore;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;

public class ReportCmd extends AbstractPlayerCommand {
    public ReportCmd() {
        super("report", "server.commands.mermaids.bug.report.desc");

        this.setPermissionGroup(GameMode.Adventure);

        this.setAllowsExtraArguments(true);

    }

    RequiredArg<String> reportMessageArgList = this.withRequiredArg("Bug report message", "server.commands.mermaids.bug.report.arg0.desc", ArgTypes.STRING);

    protected final String getArgs(CommandContext ctx) {
        int firstSpace = ctx.getInputString().indexOf(' ');
        if(firstSpace == -1){
            return "";
        }
        String aFirstSpace = ctx.getInputString().substring(firstSpace + 1);

        int secondSpace = aFirstSpace.indexOf(' ');
        if(secondSpace == -1){
            return "";
        }
        String aSecondSpace = aFirstSpace.substring(secondSpace + 1);

        int thirdSpace = aSecondSpace.indexOf(' ');
        if(thirdSpace == -1){
            return "";
        }
        String aThirdSpace = aSecondSpace.substring(thirdSpace + 1);

        return aThirdSpace;
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        String reportMessageArg = getArgs(commandContext);

        boolean reported = false;
        boolean apiError = false;

        if(reportMessageArg != null && !reportMessageArg.isEmpty() && reportMessageArg != ""){
            reported = true;

            String reportMessage = "**Mermaids**: v" + Mermaids.getVersion();
            if(playerRef != null){
                reportMessage += "\n" + playerRef.getUsername() + ": " + reportMessageArg;
            }else {
                reportMessage += "\n" + reportMessageArg;
            }

            DiscordWebhook discordWebhook = new DiscordWebhook("Mermaids v" + Mermaids.getVersion() + " Bug Report", GithubIgnore.getDiscordWebhook());
            discordWebhook.setContent(reportMessage);
            try{
                discordWebhook.execute();
            }catch(IOException e){
                apiError = true;
            }
        }

        if(player != null){
            if(!reported){
                Message invalidArgMessage = Message.translation("server.commands.mermaids.bug.report.msg.invalidArg0").color(Color.RED);
                player.sendMessage(invalidArgMessage);
            }else {
                if(apiError){
                    Message apiErrorMessage = Message.translation("server.commands.mermaids.bug.report.msg.apiError").color(Color.RED);
                    player.sendMessage(apiErrorMessage);
                }else {
                    Message reportedMessage = Message.translation("server.commands.mermaids.bug.report.msg.success");
                    player.sendMessage(reportedMessage);
                }
            }
        }
    }
}