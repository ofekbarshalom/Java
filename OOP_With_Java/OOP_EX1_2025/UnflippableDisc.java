// initialize the Unflippable Disc with an owner.
public class UnflippableDisc implements Disc {
    private Player owner;

    public UnflippableDisc(Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }

    @Override
    public String getType() {
        return "⭕";
    }
}
