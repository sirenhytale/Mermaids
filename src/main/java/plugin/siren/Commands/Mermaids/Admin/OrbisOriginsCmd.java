package plugin.siren.Commands.Mermaids.Admin;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import plugin.siren.Commands.Mermaids.Admin.OrbisOrigins.*;

public class OrbisOriginsCmd extends AbstractCommandCollection {
    public OrbisOriginsCmd(){
        super("orbisorigins","server.commands.mermaids.admin.orbisorigins.desc");

        this.addSubCommand(new MermaidsContentCmd());
        this.addSubCommand(new OnlyInWaterCmd());

        this.requirePermission("mermaids.admin.orbisorigins");
    }
}
