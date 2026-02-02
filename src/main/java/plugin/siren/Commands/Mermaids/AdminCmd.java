package plugin.siren.Commands.Mermaids;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import plugin.siren.Commands.Mermaids.Admin.*;

public class AdminCmd extends AbstractCommandCollection {
    public AdminCmd(){
        super("admin","Mermaids admin command line");

        this.addSubCommand(new MermaidOnLandCmd());
        this.addSubCommand(new BlocksTransformationCmd());
        this.addSubCommand(new RainTransformationCmd());

        this.requirePermission("mermaids.admin");
    }
}
