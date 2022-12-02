import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
/**
 * Hangman game. 
 * @author Tony Gonzalez
 * @version 8/23/2022
 */
public class Hangman
{
    // ArrayList objects
    static ArrayList<Character> randomWordArray = new ArrayList<>();
    static ArrayList<Character> hangmanWordArray = new ArrayList<>();
    static ArrayList<Character> missedLetterArray = new ArrayList<>();
    
    /**
     * Runs game.
     * @param args Args
     * @throws FileNotFoundException If file is not found. 
     */
    public static void main(String[] args) throws FileNotFoundException 
    {
        // file object
        File dictionaryWords = new File("dictionary.txt");

        // scanner objects
        Scanner fileReader = new Scanner(dictionaryWords);
        Scanner userInput = new Scanner(System.in);

        // random object
        Random randomLineGenerator = new Random();

        // instance variables 
        int randomLine = 1 + randomLineGenerator.nextInt(20);
        int hangmanCounter = 0;
        boolean wordGuessed = false;
        boolean hangmanFilled = false;
        String randomWord = getWord(fileReader, randomLine);
        String hangmanWord = "HANGMAN";

        // read random word and fill in blank array
        readWord(randomWord);

        // fill hangman ArrayList with '-'
        populateHangmanArray();

        do
        {
            boolean correctGuess = false;

            // prompt user to guess letter
            System.out.print("Guess your next letter: ");
            char userGuess = userInput.next(".").charAt(0);

            // check if word contains the letter that the user guessed
            correctGuess = checkGuess(randomWord, userGuess);

            // replace letter if guessed correctly
            if(correctGuess)
            {
                replaceGussedLetter(randomWord, userGuess);
            }
            else // fill in "HANGMAN" if guessed incorrectly
            {
                System.out.println(">>>>>>>Buzzer sound<<<<<<<<<");
                populateMissedLetterArray(userGuess);
                replaceHangmanLetter(hangmanCounter, hangmanWord);
                hangmanCounter++;
            }

            // check if game is over
            wordGuessed = checkGameStatus(randomWordArray);
            hangmanFilled = checkGameStatus(hangmanWordArray);

            // print random word with filled in letters
            for(Character i : randomWordArray)
            {
                System.out.print(i);
            }
            System.out.println();

            // print blank hangman string
            for(Character i : hangmanWordArray)
            {
                System.out.print(i);
            }
            System.out.println();

            // print missed letter array
            System.out.print("Missed letters: ");
            for(Character i : missedLetterArray)
            {
                System.out.print(i);
            }
            System.out.println();
            System.out.println();

        }while(!wordGuessed && !hangmanFilled); // continue if word has not been gussed or hangman not filled

        // print end game message based on result 
        if(wordGuessed)
        {
            System.out.println("Congratulations! You correctly gussed the word!");
        }
        else if(hangmanFilled)
        {
            System.out.println("You have lost :(");
        }
        
        fileReader.close();
        userInput.close();
    }

    /**
     * Gets a random word from the dictionary.txt file.
     * @param fileReader File reader scanner
     * @param randomLine Random line in file 
     * @return Returns a random word from txt file
     */
    public static String getWord(Scanner fileReader, int randomLine)
    {
        String randomWord = "";
        for(int i = 0; i < randomLine; i++)
        {
            randomWord = fileReader.nextLine();
        }
        return randomWord;
    }

    /**
     * Reads word and adds a '-' for every charater in the word
     * @param word Randomly chosen word
     */
    public static void readWord(String word)
    {
        for(int i = 0; i < word.length(); i++)
        {
            randomWordArray.add('-');
        }
    }

    /**
     * Populates hangman ArrayList with '-' to start game
     */
    public static void populateHangmanArray()
    {
        for(int i = 0; i < 7; i++)
        {
            hangmanWordArray.add('-');
        }
    }

    /**
     * Fills missed letter array as user continues to play
     * @param userGuess Letter gussed
     */
    public static void populateMissedLetterArray(char userGuess)
    {
        missedLetterArray.add(userGuess);
        missedLetterArray.add(' ');
    }

    /**
     * Checks if user guess matches a letter in the word and replaces '-' with the correctly guessed letter
     * @param randomWord Randomly chosen word
     * @param userGuess Guessed letter
     */
    public static void replaceGussedLetter(String randomWord, char userGuess)
    {
        for(int i = 0; i < randomWord.length(); i++)
        {
            if(userGuess == randomWord.charAt(i))
            {
                randomWordArray.set(i, userGuess);
            }
        }
    }

    /**
     * Fills in "HANGMAN" array if incorrect letter was guessed
     * @param hangmanCounter Amount of letters in "HANGMAN" filled in so far
     * @param hangmanWord "HANGMAN" string
     */
    public static void replaceHangmanLetter(int hangmanCounter, String hangmanWord)
    {
        hangmanWordArray.set(hangmanCounter, hangmanWord.charAt(hangmanCounter));
    }

    /**
     * Checks if user correctly guessed a letter
     * @param randomWord Randomly chosen word
     * @param userGuess Guessed letter
     * @return true if user guessed correctly, false if they did not
     */
    public static boolean checkGuess(String randomWord, char userGuess)
    {
        for(int i = 0; i < randomWord.length(); i++)
        {
            if(userGuess == randomWord.charAt(i))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks randomWord and hangmanWord ArrayLists to see if either have been filled
     * @param wordArray ArrayList to be checked
     * @return false if ArrayList has not been filled with letters, true if it has been filled
     */
    public static boolean checkGameStatus(ArrayList<Character> wordArray)
    {
        for(Character i : wordArray)
        {
            if(i.equals('-'))
            {
                return false;
            }
        }
        return true;
    }
}