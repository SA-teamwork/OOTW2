package item;

public abstract class ItemCommand {
    public abstract void Execute();

    public void removeCmd(ItemCommand cmd) {
        throw new UnsupportedOperationException();
    }

    public void addCmd(ItemCommand cmd) {
        throw new UnsupportedOperationException();
    }

    public ItemCommand getChild(int i) {
        throw new UnsupportedOperationException();
    }
    
}
