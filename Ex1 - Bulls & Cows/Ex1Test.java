import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
/** Name: Ofek bar-shalom
 *  ID: 324161421
 */
public class Ex1Test {

    @Test
    @Order(1)        // specify the order in which test methods run.
    public void MakeGuessArr() {
        boolean[] boolArr = Ex1.MakeGuessArr(4);                   // makes array with true values.
        boolean[] expected = {true, true, true, true};                  // array for test
        assertArrayEquals(boolArr, expected, "Test 1 failed");  // Checks if the test array and boolArr array equals
    }

    @Test
    @Order(2)
    public void NextGuess() {
        boolean[] boolArr0 = {false, false, true, false};   // Test case: one 'true' (expecting the index 2)
        assertEquals(2, Ex1.NextGuess(boolArr0), "Test 1 failed");
        boolean[] boolArr1 = {true, false, false, false};   // Test case: one 'true' in the first cell (expecting the index 1)
        assertEquals(0, Ex1.NextGuess(boolArr1), "Test 2 failed");
        boolean[] boolArr2 = {false, true, false, true};    // Test case: two 'true' (expecting the index 2)
        assertEquals(1, Ex1.NextGuess(boolArr2), "Test 3 failed");
        boolean[] boolArr3 = {false, false, false, false};  // Test case: no 'true' (expecting the index -1)
        assertEquals(-1, Ex1.NextGuess(boolArr3), "Test 4 failed");
    }

    @Test
    @Order(3)
    public void CheckBullsAndCows() {
        // Test case 1: All Bulls
        int[] numArr1 = {1, 2, 3, 4};
        int[] guess1 = {1, 2, 3, 4};
        int[] expected1 = {4, 0};
        assertBullsAndCows(expected1, Ex1.CheckBullsAndCows(numArr1, guess1), "Test 1 failed");

        // Test case 2: 1 Bull, 2 Cows
        int[] numArr2 = {1, 3, 4, 5};
        int[] guess2 = {1, 2, 3, 4};
        int[] expected2 = {1, 2};
        assertBullsAndCows(expected2, Ex1.CheckBullsAndCows(numArr2, guess2), "Test 2 failed");

        // Test case 3: No Bulls and No Cows
        int[] numArr3 = {1, 2, 3, 4};
        int[] guess3 = {5, 6, 7, 8};
        int[] expected3 = {0, 0};
        assertBullsAndCows(expected3, Ex1.CheckBullsAndCows(numArr3, guess3), "Test 3 failed");
    }

    /** this function assert that the expected bulls and cows are the same as the ones we get from Ex1.CheckBullsAndCows function */
    public void assertBullsAndCows(int[] expected, int[] actual, String massage) {
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }

    @Test
    @Order(4)
    public void CheckGuessArr() {
        int[] guess = {2, 3};
        boolean[] boolArr = Ex1.MakeGuessArr(99);
        int[] res = {1, 0}; // Test case: 1 shared bull, no shared cows.
        Ex1.CheckGuessArr(res, guess, boolArr, 2);
        assertTrue(boolArr[20], "Test 1 failed");  // Test case: 1 shared bull (expecting 'True')
        assertTrue(boolArr[3], "Test 2 failed");   // Test case: 1 shared bull (expecting 'True')
        assertFalse(boolArr[23], "Test 3 failed"); // Test case: The same number as guess (expecting 'false')
        assertFalse(boolArr[56], "Test 4 failed"); // Test case: no shared bull (expecting 'false')

        boolean[] boolArr1 = Ex1.MakeGuessArr(99);
        int[] res1 = {0, 0}; // Test case: no shared bulls and cows
        Ex1.CheckGuessArr(res1, guess, boolArr1, 2); // guess[2,3]
        assertFalse(boolArr1[32], "Test 5 failed");   // Test case: 2 shared cows (expecting 'false')
        assertFalse(boolArr1[26], "Test 6 failed");   // Test case: 1 shared bull (expecting 'false')

        boolean[] boolArr2 = Ex1.MakeGuessArr(99);
        int[] res2 = {0, 2}; // Test case: 2 shared cows
        Ex1.CheckGuessArr(res2, guess, boolArr2, 2);
        assertTrue(boolArr2[32], "Test 7 failed");   // Test case: 2 shared cows (expecting 'True')
    }

    public static double avg2 = 0, avg3 = 0, avg4 = 0, avg5 = 0, avg6 = 0;

    @Test
    @Order(5)
    public void avg2() {
        avg2 = avgCheck(2);
        assertTrue(avg2 < 8);
    }

    @Test
    @Order(6)
    public void avg3() {
        avg3 = avgCheck(3);
        assertTrue(avg3 < 9);
    }

    @Test
    @Order(7)
    public void avg4() {
        avg4 = avgCheck(4);
        assertTrue(avg4 < 10);
    }

    @Test
    @Order(8)
    public void avg5() {
        avg5 = avgCheck(5);
        assertTrue(avg5 < 10);
    }

    @Test
    @Order(9)
    public void avg6() {
        avg6 = avgCheck(6);
        assertTrue(avg6 < 10);
    }

    /** avgCheck:
     * Checks the average of the rounds of 100 games.
     * @param numDigits The number of digits in the code
     * @return the average of the rounds.
     */
    public double avgCheck(int numDigits){
        Ex1.numOfDigits=numDigits;             // global int that represent the number of digits in the code
        for(int i=0;i<100;i++){                //loop over the main in Ex1 100 times to get an average rounds number.
            Ex1.main(null);
        }
        double avg=(double) Ex1.counter/100;   //Divide the counter to get the average of 100 runs
        Ex1.counter=0;                         //Reset the counter so we could use it again later
        return avg;
    }

    /**
     * PrintAverage:
     * Prints the guesses average for each digit and for all together
     */
    @AfterAll           // After all the other avarge already calculated.
    public static void PrintAverage() {
        System.out.println("the averages are:");
        System.out.println("2) " + avg2 + " 3) " + avg3 + " 4) " + avg4 + " 5) " + avg5 + " 6) " + avg6);
        double allAvgs = (avg2 + avg3 + avg4 + avg5 + avg6) / 5;
        allAvgs = Math.round(allAvgs * 100.0) / 100.0;           //round the avarge to 2 digits after the dot
        System.out.println("Average all: " + allAvgs);
    }
}
