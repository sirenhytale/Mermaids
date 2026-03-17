package plugin.siren.Commands.Mermaids;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import plugin.siren.Commands.Mermaids.Admin.*;

public class AdminCmd extends AbstractCommandCollection {
    public AdminCmd(){
        super("admin","server.commands.mermaids.admin.desc");

        this.addSubCommand(new TransformModeCmd());
        this.addSubCommand(new MermaidOnLandCmd());
        this.addSubCommand(new ItemSpeedCmd());
        this.addSubCommand(new BlockTransformCmd());
        this.addSubCommand(new RainTransformCmd());
        this.addSubCommand(new MermaidGlowCmd());
        this.addSubCommand(new MermaidGlowRadiusCmd());

        this.requirePermission("mermaids.admin");
    }
}
