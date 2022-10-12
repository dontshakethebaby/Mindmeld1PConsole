package cisc191;

import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom; // No need to create a random object

public class WordManager {

    public WordManager () {

    }

    /* Given a word list, pick returns n words from that list, randomly selected
    but without repeats */
    public static String[] pick(String[] words, int rounds) {
        String[] mainWords = new String[rounds];
        int rand;

        for (int i = 0; i < rounds; i++) {
            rand = ThreadLocalRandom.current().nextInt(0, words.length);
            while (ArrayUtils.contains(mainWords,words[rand])) {                    // while a duplicate, re-roll
                rand = ThreadLocalRandom.current().nextInt(0, words.length);
            }
            mainWords[i] = words[rand];
        }
        return mainWords;
    }

    /* Returns true if a substring occurs within a string, because I'm counting splits,
    the original string needs to be padded. */
    public static boolean occurs(String str, String substr) {
        boolean doesContain = false;
        str = "0" + str + "0";
        String[] array = str.split(substr);
        if (array.length > 1) {
            doesContain = true;
        }
        return doesContain;
    }

    /* Takes in the main word for the round and a number of user guesses, then prompts
    and validates each user guess and returns an array of validated user guesses. */
    public static String[] collectGuesses(String mainWord, int guesses, int round) {

        String[] roundGuesses = new String[guesses];
        Scanner stdin = new Scanner(System.in);
        String[] cardinals = {"one", "two", "three", "four", "five"};
        String[] ordinals = {"first", "second", "third", "fourth", "fifth"};

        System.out.println("The main word for round " + cardinals[round] + " is " + mainWord + ".");

        for (int guess = 0; guess < guesses; guess++) {
            // Initial prompt for mth guess
            System.out.println("Give a " + ordinals[guess] + " word associated with " + mainWord + ":");
            String userWord = stdin.nextLine().toLowerCase();

            /* Validity checks for user input, re-prompt and replace userWord if it's > 2 words
            long or is a (weak) substring of the main word, or contains full main word */
            while ((WordManager.occurs(mainWord.toLowerCase(),userWord))|(userWord.split(" ").length > 2)
                    |(WordManager.occurs(userWord, mainWord.toLowerCase()))) {
                if (userWord.split(" ").length > 2) {
                    System.out.println("Too many words entered, please provide a guess that is two words or less: ");
                    userWord = stdin.nextLine();
                }
                else if (WordManager.occurs(userWord, mainWord.toLowerCase())) {
                    System.out.println("Please give a word that does not contain the main word: ");
                    userWord = stdin.nextLine();
                }
                else {
                    System.out.println("Please give a word that is NOT a substring of the main word: ");
                    userWord = stdin.nextLine();
                }
            }
            roundGuesses[guess] = userWord;
        }
        return roundGuesses;
    }

    /* Takes in array of common words and array of user guesses and returns an array of
    0/1 ints based on whether each guess was wrong/right. */
    public static int[] calculatePoints(String[] commonWords, String[] guesses) {
        int[] points = new int[guesses.length];
        for (int guess = 0; guess < guesses.length; guess++) {
            if (ArrayUtils.contains(commonWords, guesses[guess])) {
                points[guess] = 1;
            }
            else {}
        }
        return points;
    }

    // Takes in so much and returns nothing because its purpose is to display round info to the user
    public static void reveal(String mainWord, String[] commonWords, String[] guesses, int[] roundPoints) {
        Scanner stdin = new Scanner(System.in);
        String pointPlural = "";

        // Asks and validates user response to whether they want to see common words list and executes
        System.out.println("Would you like to see the common words list for this round? (Y/N): ");
        String seeList = stdin.nextLine().toLowerCase();
        while (!(WordManager.occurs(seeList, "y")|(WordManager.occurs(seeList, "n")))) {
            System.out.println("Please enter valid input (Y/N): ");
            seeList = stdin.nextLine().toLowerCase();
        }
        if (WordManager.occurs(seeList,"y")) {
            System.out.println("The common word list for " + mainWord + " is: ");
            for (int i = 0; i < commonWords.length; i++) {
                System.out.println(commonWords[i]);
            }
        }
        else {
        }

        // The guess by guess summary of points
        int sum = Arrays.stream(roundPoints).sum();
        for (int guess = 0; guess < guesses.length; guess++) {
            if (roundPoints[guess] == 1) {
                pointPlural = "point";
            }
            else {
                pointPlural = "points";
            }
            System.out.println("You received " + roundPoints[guess] + " " + pointPlural + " for "
                    + guesses[guess] + ",");
        }

        // Summary of round points
        if (sum == 1) {
            pointPlural = "point";
        }
        else {
            pointPlural = "points";
        }
        System.out.println("For a total of " + Arrays.stream(roundPoints).sum() + " " + pointPlural
        + " for the round.");
    }

}
