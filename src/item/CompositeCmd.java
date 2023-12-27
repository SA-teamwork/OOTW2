package item;

import java.util.ArrayList;

public class CompositeCmd extends ItemCommand {
    private ArrayList<ItemCommand> cmds = new ArrayList<>();

    @Override
    public void Execute() {
        for (ItemCommand cmd : cmds) {
            cmd.Execute();
        }
    }

    @Override
    public void removeCmd(ItemCommand cmd) {
        cmds.remove(cmd);
    }

    @Override
    public void addCmd(ItemCommand cmd) {
        cmds.add(cmd);
    }

    @Override
    public ItemCommand getChild(int i) {
        return cmds.get(i);
    }


}
