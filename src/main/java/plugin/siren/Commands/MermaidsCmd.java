package plugin.siren.Commands;

import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import plugin.siren.Commands.Mermaids.*;
import plugin.siren.Mermaids;

public class MermaidsCmd extends AbstractCommandCollection {
    public MermaidsCmd(){
        super("mermaids","Mermaids command line");

        this.addSubCommand(new AdminCmd());
        this.addSubCommand(new InfoCmd());
        this.addSubCommand(new ToggleCmd());
        this.addSubCommand(new MermaidsUICmd());
        this.addSubCommand(new PermPotionRemoveCmd());

        if(Mermaids.ifDebug()){
            this.addSubCommand(new DebugCmd());
        }

        this.setPermissionGroup(GameMode.Adventure);
    }
}
