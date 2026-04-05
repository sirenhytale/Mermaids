package plugin.siren.Commands.Mermaids.Admin;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import plugin.siren.Commands.Mermaids.Admin.EndlessLeveling.*;

public class EndlessLevelingCmd extends AbstractCommandCollection {
    public EndlessLevelingCmd(){
        super("endlessleveling","server.commands.mermaids.admin.endlessleveling.desc");

        this.addAliases("endless");

        this.addSubCommand(new MermaidsContentELCmd());
        this.addSubCommand(new OnlyInWaterELCmd());

        this.requirePermission("mermaids.admin.endlessleveling");
    }
}
