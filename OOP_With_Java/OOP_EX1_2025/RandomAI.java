import java.util.List;
import java.util.Random;
// Initialize an AI player that chooses a random disc and move.
public class RandomAI extends AIPlayer {

    public RandomAI(boolean isPlayerOne) {super(isPlayerOne);}

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        Disc disc = chooseDisc();
        // If there are no valid moves, return null (pass turn)
        if (validMoves.isEmpty()) {
            return null;
        }

        // Randomly select a move from the list of valid moves
        Random random = new Random();
        int randomIndex = random.nextInt(validMoves.size());
        return new Move(validMoves.get(randomIndex),disc);
    }

    public Disc chooseDisc(){
        Random random = new Random();
        int randomIndex = random.nextInt(3) + 1;


        if(randomIndex == 1 && number_of_unflippedable > 0){
            return new UnflippableDisc(this);
        }
        else if(randomIndex == 2 && number_of_bombs > 0){
            return new BombDisc(this);
        }
        else{
            return new SimpleDisc(this);
        }
    }
}
