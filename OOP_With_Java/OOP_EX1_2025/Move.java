public class Move {
    private Position position;

    private Disc disc;

    /**
     * Constructs a new Move with the specified position and disc.
     *
     * @param position The position on the board where the move is made.
     * @param disc The disc used in the move.
     */
    public Move(Position position, Disc disc) {
        this.position = position;
        this.disc = disc;
    }

    /**
     * Sets the position of this move to the specified position.
     *
     * @param position The new position for this move.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Sets the disc for this move to the specified disc.
     *
     * @param disc The new disc for this move.
     */
    public void setDisc(Disc disc) {
        this.disc = disc;
    }

    /**
     * Returns the current position of this move.
     *
     * @return The position of this move.
     */
    public Position position() {
        return position;
    }

    /**
     * Returns the disc associated with this move.
     *
     * @return The disc used in this move.
     */
    public Disc disc() {
        return disc;
    }
}
