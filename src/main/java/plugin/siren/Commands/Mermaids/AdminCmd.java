package plugin.siren.Commands.Mermaids;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import plugin.siren.Commands.Mermaids.Admin.*;
import plugin.siren.Mermaids;

public class AdminCmd extends AbstractCommandCollection {
    public AdminCmd(){
        super("admin","server.commands.mermaids.admin.desc");

        this.addAliases("a");

        this.addSubCommand(new ReloadCmd());
        this.addSubCommand(new TransformModeCmd());
        this.addSubCommand(new MermaidOnLandCmd());
        this.addSubCommand(new LandSpeedDebuffCmd());
        this.addSubCommand(new ItemSpeedCmd());
        this.addSubCommand(new BlockTransformCmd());
        this.addSubCommand(new RainTransformCmd());
        this.addSubCommand(new MermaidGlowCmd());
        this.addSubCommand(new MermaidGlowRadiusCmd());

        if(Mermaids.ifOrbisOrigins()){
            this.addSubCommand(new OrbisOriginsCmd());
        }

        if(Mermaids.ifEndlessLeveling()){
            this.addSubCommand(new EndlessLevelingCmd());
        }

        this.requirePermission("mermaids.admin");
    }
}
