package plugin.siren.Commands.Mermaids;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import plugin.siren.Commands.Mermaids.Debug.*;

public class DebugCmd extends AbstractCommandCollection {
    public DebugCmd(){
        super("debug","server.commands.mermaids.debug.desc");

        this.addSubCommand(new MermaidRingCmd());

        this.requirePermission("mermaids.debug");
    }
}
