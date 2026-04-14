package plugin.siren.Commands.Mermaids;

import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import plugin.siren.Commands.Mermaids.Bug.*;

public class BugCmd extends AbstractCommandCollection {
    public BugCmd(){
        super("bug","server.commands.mermaids.bug.desc");

        this.addAliases("b");

        this.addSubCommand(new LogCmd());
        this.addSubCommand(new ComponentFixCmd());
        this.addSubCommand(new ReportCmd());

        this.setPermissionGroup(GameMode.Adventure);
    }
}