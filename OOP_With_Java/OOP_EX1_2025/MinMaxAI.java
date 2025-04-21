import java.util.List;

/**
 * MinMaxAI is an AI player that uses the Minimax algorithm with alpha-beta pruning
 * to make strategic moves in the game.
 */
public class MinMaxAI extends AIPlayer {

    /**
     * Constructs a MinMaxAI player.
     *
     * @param isPlayerOne a boolean indicating if this player is Player One.
     */
    public MinMaxAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {

        GameLogic gameLogic = (GameLogic) gameStatus;

        // Determine the current player based on the game state
        Player currentPlayer;
        if (gameLogic.isFirstPlayerTurn()) {
            currentPlayer = gameLogic.getFirstPlayer();
        } else {
            currentPlayer = gameLogic.getSecondPlayer();
        }

        // Retrieve all valid moves
        List<Position> validMoves = gameLogic.ValidMoves();
        if (validMoves.isEmpty()) {
            return null; // No valid moves, return null
        }

        // Use the Minimax algorithm to find the optimal move
        Move bestMove = findBestMove(gameLogic);

        // Fallback to the first valid move if no optimal move is found
        if (bestMove == null) {
            bestMove = new Move(validMoves.get(0), chooseDisc(currentPlayer));
        }

        return bestMove;
    }

    /**
     * Finds the best move for the current player using the Minimax algorithm.
     *
     * @param logic the current game logic state.
     * @return the best move based on the evaluation function.
     */
    private Move findBestMove(GameLogic logic) {
        Player currentPlayer;
        if (logic.isFirstPlayerTurn()) {
            currentPlayer = logic.getFirstPlayer();
        } else {
            currentPlayer = logic.getSecondPlayer();
        }

        // Perform Minimax search with a depth of 3
        MoveScore result = findOptimalMove(logic, currentPlayer, 3, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

        return result.move;
    }

    /**
     * Recursive function to find the optimal move using the Minimax algorithm with alpha-beta pruning.
     *
     * @param logic            the current game logic state.
     * @param player           the player for whom the evaluation is being done.
     * @param depth            the current depth of the search.
     * @param maximizingPlayer true if the current node is a maximizing player.
     * @param alpha            the best value that the maximizing player can guarantee.
     * @param beta             the best value that the minimizing player can guarantee.
     * @return the best move and its evaluation score.
     */
    private MoveScore findOptimalMove(GameLogic logic, Player player, int depth, boolean maximizingPlayer, double alpha, double beta) {
        if (depth == 0 || logic.isGameFinished()) {
            return new MoveScore(null, evaluateGameState(logic, player));
        }

        List<Position> validPositions = logic.ValidMoves();
        if (validPositions.isEmpty()) {
            return new MoveScore(null, evaluateGameState(logic, player));
        }

        Move bestMove = null;
        double bestValue = maximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

        for (Position position : validPositions) {
            Disc disc = chooseDisc(player);
            Move move = new Move(position, disc);

            // Skip invalid moves with no flips
            if (logic.countFlips(position) == 0) {
                continue;
            }

            // Make the move and evaluate recursively
            double evaluation = findOptimalMove(logic, player, depth - 1, !maximizingPlayer, alpha, beta).score;

            // Undo the move
            logic.undoLastMove();

            // Update the best move based on the evaluation
            if (maximizingPlayer) {
                if (evaluation > bestValue) {
                    bestValue = evaluation;
                    bestMove = move;
                }
                alpha = Math.max(alpha, bestValue);
            } else {
                if (evaluation < bestValue) {
                    bestValue = evaluation;
                    bestMove = move;
                }
                beta = Math.min(beta, bestValue);
            }

            // Alpha-beta pruning
            if (beta <= alpha) {
                break;
            }
        }

        return new MoveScore(bestMove, bestValue);
    }

    /**
     * Evaluates the current game state for the given player.
     *
     * @param logic  the current game logic state.
     * @param player the player for whom the evaluation is being done.
     * @return the evaluation score of the game state.
     */
    private double evaluateGameState(GameLogic logic, Player player) {
        int playerDiscs = logic.countPlayerDiscs(player);
        Player opponent;
        if (player.isPlayerOne()) {
            opponent = logic.getSecondPlayer();
        } else {
            opponent = logic.getFirstPlayer();
        }
        int opponentDiscs = logic.countPlayerDiscs(opponent);

        if (logic.isGameFinished()) {
            if (playerDiscs > opponentDiscs) return 1.0; // Win
            if (playerDiscs < opponentDiscs) return -1.0; // Loss
            return 0.0; // Tie
        }

        return playerDiscs - opponentDiscs; // Heuristic: Difference in discs
    }

    /**
     * Chooses the type of disc the player should use based on availability.
     *
     * @param player the player for whom the disc is being chosen.
     * @return the chosen disc.
     */
    private Disc chooseDisc(Player player) {
        if (player.getNumber_of_unflippedable() > 0) {
            return new UnflippableDisc(player);
        } else if (player.getNumber_of_bombs() > 0) {
            return new BombDisc(player);
        } else {
            return new SimpleDisc(player);
        }
    }

    /**
     * A helper class to represent a move and its associated score.
     */
    private static class MoveScore {
        private final Move move;
        private final double score;

        /**
         * Constructs a MoveScore with the given move and score.
         *
         * @param move  the move associated with this score.
         * @param score the evaluation score of the move.
         */
        public MoveScore(Move move, double score) {
            this.move = move;
            this.score = score;
        }
    }
}
