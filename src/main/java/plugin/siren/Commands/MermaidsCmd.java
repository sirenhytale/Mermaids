package plugin.siren.Commands;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import plugin.siren.Commands.Mermaids.MermaidCompBugCmd;
import plugin.siren.Commands.Mermaids.MermaidsPermPotionRemove;
import plugin.siren.Commands.Mermaids.MermaidsUI;
import plugin.siren.Commands.Mermaids.ToggleMermaid;

public class MermaidsCmd extends AbstractCommandCollection {
    public MermaidsCmd(){
        super("mermaids","Mermaids command line");

        addSubCommand(new ToggleMermaid());
        addSubCommand(new MermaidsUI());
        addSubCommand(new MermaidsPermPotionRemove());
        addSubCommand(new MermaidCompBugCmd());
    }
}
