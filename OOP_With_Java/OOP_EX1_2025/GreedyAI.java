import java.util.List;

// Initialize an AI player that always chooses the move flipping the most discs.
public class GreedyAI extends AIPlayer {
    public GreedyAI(boolean isPlayerOne) {super(isPlayerOne);}

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        Disc disc = new SimpleDisc(this); // Always use a normal disc

        // If there are no valid moves, return null (pass turn)
        if (validMoves.isEmpty()) {
            return null;
        }

        // Find the move that flips the most discs, considering the rightmost and lowest if needed
        int maxFlips = 0;
        Position bestMove = null;
        for (Position move : validMoves) {
            int flips = gameStatus.countFlips(move); // Assuming countFlips returns the number of discs flipped

            if (flips > maxFlips) {
                maxFlips = flips;
                bestMove = move;
            } else if (flips == maxFlips) {
                // If the number of flips is equal, choose the rightmost move
                if (bestMove == null || Integer.compare(move.col(), bestMove.col()) > 0 ||
                        (Integer.compare(move.col(), bestMove.col()) == 0 && Integer.compare(move.row(), bestMove.row()) > 0)) {
                    bestMove = move;
                }
            }
        }

        // Return the move with the maximum number of flips, rightmost, and lowest
        return new Move(bestMove, disc);
    }
}
