import java.util.Scanner;
import java.util.Arrays;
/**
 * Introduction to Computer Science, Ariel University, Ex1 (manual Example + a Template for my solution)
 * See: <a href="https://docs.google.com/document/d/1C1BZmi_Qv6oRrL4T5oN9N2bBMFOHPzSI/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true">...</a>
 *
 * Ex1 Bulls & Cows - Automatic solution.
 * **** README ****
 * Bulls & Cows is a code-guessing game that challenges you to find a secret code with 2-6 digits.
 * After each guess, you receive feedback in the form of bulls and cows:
 * Bulls represent the correct numbers in the correct positions.
 * Cows represent correct numbers that are not in the right positions.
 * by getting this information you can strategically change your guesses until you guess the right code!
 *
 * **** General Solution (algorithm) ****
 * The automatic solving algorithm is a way to guess the code in the minimum possible attempts.
 * The program as a boolean array representing the possible guesses (Each index in the array corresponds to a number).
 * the first guess is "0000", after we get the bulls and cows feedback from the server, we disqualified
 * all the indexes (possible guesses) in the boolean array that do not match the same number of bulls and cows as the guess.
 * then we send the next possible guess from our boolean array until we successfully find the code.
 *
 * **** Results ****
 * for 2,3,4,5,6 digit code:
 *
 * Average guesses 2: 6.65
 * Average guesses 3: 7.64
 * Average guesses 4: 8.22
 * Average guesses 5: 8.75
 * Average guesses 6: 9.22
 * Average guesses for all: 8.51
 */

public class Ex1 {
    public static final String Title = "Ex1 demo: manual Bulls & Cows game";
    public static int numOfDigits = 2;                // Number of digits [2,6]
    public static double counter=0;
    public static void main(String[] args) {
        BP_Server game = new BP_Server();   // Starting the "game-server"
        long myID = 324161421L;
        game.startGame(myID, numOfDigits);  // Starting a game
        System.out.println(Title+" with code of "+numOfDigits+" digits");
        //manualEx1Game(game);
        autoEx1Game(game);
    }
    public static void manualEx1Game(BP_Server game) {
        Scanner sc = new Scanner(System.in);
        int ind=1;      // Index of the guess
        int numOfDigits = game.getNumOfDigits();
        double max = Math.pow(10,numOfDigits);
        while(game.isRunning()) {             // While the game is running (the code has not been found yet
            System.out.println(ind+") enter a guess: ");
            int g = sc.nextInt();
            if(g>=0 && g<max) {
                int[] guess = toArray(g, numOfDigits); // int to digit array
                int[] res = game.play(guess); // Playing a round and getting the B,C
                if (game.isRunning()) {       // While the game is running
                    System.out.println(ind + ") B: " + res[0] + ",  C: " + res[1]); // Prints the Bulls [0], and the Cows [1]
                    ind += 1;                 // Increasing the index
                }
            }
            else {
                System.out.println("ERR: wrong input, try again");
            }
        }
        System.out.println(game.getStatus());
    }
    /**
     * Simple parsing function that gets an int and returns an array of digits
     * @param a - a natural number (as a guess)
     * @param size  - number of digits (to handle the 00 case).
     * @return an array of digits
     */
    private static int[] toArray(int a, int size){   //Fixed version of toArray
        int[] c = new int[size];
        for(int j=size-1;j>=0;j--) {
            c[j] = a%10;
            a=a/10;
        }
        return c;
    }
////////////////////////////////////////////////////
    /** autoEx1Game:
     * This function automatically solves a Bulls & Cows game by generating guesses
     * and adjusting the set of possible solutions based on Bulls and Cows feedback
     * from the provided game server.
     * @param game BP_Server object for Bulls & Cows game interaction.
     */
    public static void autoEx1Game(BP_Server game) {
        int numDigits = game.getNumOfDigits();                  //the number of digits in code
        double max = Math.pow(10, numDigits);                   //the number of possible answers
        boolean[] guessArr=MakeGuessArr(max);                   //array of the possible guesses
        while (game.isRunning()) {
            counter++;                                          //a global parameter for checking the average in ExTest
            int[] guess=toArray(NextGuess(guessArr),numDigits); //Convert the guess from a number to an array representation
            int[] res = game.play(guess);                       //Get Bulls and Cows result and store in an array
            CheckGuessArr(res, guess, guessArr, numDigits);     //Update the possible Guesses array
        }
    }
    /** MakeGuessArr:
     * Makes a boolean array with the possible guesses.
     * Each index in the array corresponds to a number, and all values are set to true,
     * indicating that all numbers are considered as possible guesses at the beginning.
     * @param max The maximum possible value for guesses.
     * @return Boolean array indicating possible guesses.
     */
    public static boolean[] MakeGuessArr(double max){
        boolean[] guessArr=new boolean[(int)max];
        for(int i=0;i<max;i++) {                    //fill the array with True values
            guessArr[i] =true;
        }
        return guessArr;
    }
    /** NextGuess:
     * If the function finds an index with a 'true' value, it returns that index.
     * [When the value of the index is "True" it's a possible guess]
     * @param boolArr Boolean array indicating possible guesses.
     * @return The next possible guess index in the boolean array.
     */
    public static int NextGuess(boolean[] boolArr){
        for(int i=0;i<boolArr.length;i++){
            if(boolArr[i]){                  //Check if the value of the index is "True"
                return i;
            }
        }
        return 0;                            //return 0 when there are no possible numbers (not supposed to happen)
    }
    /** CheckGuessArr:
     * Checks and updates the boolean guess array based on the Bulls and Cows result.
     * Compares each possible guess with the actual result to evaluate if it's a possible number.
     * If the Bulls and Cows counts do not match, or the guess is fully correct(we already guessed that number),
     * mark the index in the boolean array as 'false'(not a possible number).
     * @param res the result of Bulls and Cows that given from the server.
     * @param guess the guess code we sent to the server.
     * @param boolArr Boolean array indicating possible guesses.
     * @param numDigits an array of the number of Digits in the code.
     */
    public static void CheckGuessArr(int[] res, int[] guess, boolean[] boolArr, int numDigits) {
        for (int i = 0; i < boolArr.length; i++) {                           //loop over all the numbers in the guess array
            if (boolArr[i]) {                                                //check only if the index value is True
                int[] result=checkBullsAndCows(toArray(i,numDigits),guess);  // array with the Bulls and Cows between the guess and the index number in the boolean array
                if (!Arrays.equals(result, res))                             //checks if the 'result' array is equal the guess Bulls and Cows result
                    boolArr[i] = false;
                if (result[0] == numDigits)                                  //checks if the index is already sent to the server as a guess
                    boolArr[i] = false;
            }
        }
    }
    /** checkBullsAndCows:
     * compare the number of Bulls and Cows between the last guess and the current index in the boolean array.
     * @param numArr an array of the index guess in the boolean array
     * @param guess an array of the guess code we sent to the server.
     * @return an array with the number of Bulls and Cows between the guess and the index number in the boolean array [Bulls,Cows]
     */
    public static int[] checkBullsAndCows(int[]numArr, int[]guess){
        int[] result={0,0};                                  //array with the Bulls and Cows between the guess and the index in the boolean array
        for(int i=0;i<numArr.length;i++){                    //loop over all individual numbers of the index in the boolean array.
            if(guess[i]==numArr[i])                          //checks for bulls
                result[0]++;
            else
                for(int j=0;j<guess.length;j++){                                         //loop over all individual numbers of the guess.
                    if(numArr[i] == guess[j] && i != j && numArr[i] != guess[i]) {       //checks for Cows
                        result[1]++;
                        break;          //breaks the loop if a match is found since there's no need to continue searching.
                    }
                }
        }
        return result;
    }
}
