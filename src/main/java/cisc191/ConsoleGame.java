package cisc191;

import java.io.IOException;
import java.util.*;

public class ConsoleGame extends WordManager {

    public static String[] mainWordBank = {"AFRICA","AGENT","AIR","ALIEN","ALPS","AMAZON","AMBULANCE","AMERICA","ANGEL","ANTARCTICA",
            "APPLE","ARM","ATLANTIS","AUSTRALIA","AZTEC","BACK","BALL","BAND","BANK","BAR","BARK","BAT","BATTERY",
            "BEACH","BEAR","BEAT","BED","BEIJING","BELL","BELT","BERLIN","BERMUDA","BERRY","BILL","BLOCK","BOARD",
            "BOLT","BOMB","BOND","BOOM","BOOT","BOTTLE","BOW","BOX","BRIDGE","BRUSH","BUCK","BUFFALO","BUG","BUGLE"
            ,"BUTTON","CALF","CANADA","CAP","CAPITAL","CAR","CARD","CARROT","CASINO","CAST","CAT","CELL","CENTAUR"
            ,"CENTER","CHAIR","CHANGE","CHARGE","CHECK","CHEST","CHICK","CHINA","CHOCOLATE","CHURCH","CIRCLE",
            "CLIFF","CLOAK","CLUB","CODE","COLD","COMIC","COMPOUND","CONCERT","CONDUCTOR","CONTRACT","COOK","COPPER"
            ,"COTTON","COURT","COVER","CRANE","CRASH","CRICKET","CROSS","CROWN","CYCLE","CZECH","DANCE","DATE",
            "DAY","DEATH","DECK","DEGREE","DIAMOND","DICE","DINOSAUR","DISEASE","DOCTOR","DOG","DRAFT","DRAGON",
            "DRESS","DRILL","DROP","DUCK","DWARF","EAGLE","EGYPT","EMBASSY","ENGINE","ENGLAND","EUROPE","EYE","FACE"
            ,"FAIR","FALL","FAN","FENCE","FIELD","FIGHTER","FIGURE","FILE","FILM","FIRE","FISH","FLUTE","FLY",
            "FOOT","FORCE","FOREST","FORK","FRANCE","GAME","GAS","GENIUS","GERMANY","GHOST","GIANT","GLASS","GLOVE",
            "GOLD","GRACE","GRASS","GREECE","GREEN","GROUND","HAM","HAND","HAWK","HEAD","HEART","HELICOPTER",
            "HIMALAYAS","HOLE","HOLLYWOOD","HONEY","HOOD","HOOK","HORN","HORSE","HORSESHOE","HOSPITAL","HOTEL",
            "ICE","ICE CREAM","INDIA","IRON","IVORY","JACK","JAM","JET","JUPITER","KANGAROO","KETCHUP","KEY","KID",
            "KING","KIWI","KNIFE","KNIGHT","LAB","LAP","LASER","LAWYER","LEAD","LEMON","LEPRECHAUN","LIFE","LIGHT",
            "LIMOUSINE","LINE","LINK","LION","LITTER","LOCHNESS","LOCK","LOG","LONDON","LUCK","MAIL","MAMMOTH",
            "MAPLE","MARBLE","MARCH","MASS","MATCH","MERCURY","MEXICO","MICROSCOPE","MILLIONAIRE","MINE","MINT",
            "MISSILE","MODEL","MOLE","MOON","MOSCOW","MOUNT","MOUSE","MOUTH","MUG","NAIL","NEEDLE","NET","NEW YORK",
            "NIGHT","NINJA","NOTE","NOVEL","NURSE","NUT","OCTOPUS","OIL","OLIVE","OLYMPUS","OPERA","ORANGE","ORGAN",
            "PALM","PAN","PANTS","PAPER","PARACHUTE","PARK","PART","PASS","PASTE","PENGUIN","PHOENIX","PIANO","PIE",
            "PILOT","PIN","PIPE","PIRATE","PISTOL","PIT","PITCH","PLANE","PLASTIC","PLATE","PLATYPUS","PLAY","PLOT",
            "POINT","POISON","POLE","POLICE","POOL","PORT","POST","POUND","PRESS","PRINCESS","PUMPKIN","PUPIL",
            "PYRAMID","QUEEN","RABBIT","RACKET","RAY","REVOLUTION","RING","ROBIN","ROBOT","ROCK","ROME","ROOT",
            "ROSE","ROULETTE","ROUND","ROW","RULER","SATELLITE","SATURN","SCALE","SCHOOL","SCIENTIST","SCORPION",
            "SCREEN","SCUBA DIVER","SEAL","SERVER","SHADOW","SHAKESPEARE","SHARK","SHIP","SHOE","SHOP","SHOT","SINK"
            ,"SKYSCRAPER","SLIP","SLUG","SMUGGLER","SNOW","SNOWMAN","SOCK","SOLDIER","SOUL","SOUND","SPACE","SPELL"
            ,"SPIDER","SPIKE","SPINE","SPOT","SPRING","SPY","SQUARE","STADIUM","STAFF","STAR","STATE","STICK",
            "STOCK","STRAW","STREAM","STRIKE","STRING","SUB","SUIT","SUPERHERO","SWING","SWITCH","TABLE","TABLET",
            "TAG","TAIL","TAP","TEACHER","TELESCOPE","TEMPLE","THEATER","THIEF","THUMB","TICK","TIE","TIME","TOKYO",
            "TOOTH","TORCH","TOWER","TRACK","TRAIN","TRIANGLE","TRIP","TRUNK","TUBE","TURKEY","UNDERTAKER",
            "UNICORN","VACUUM","VAN","VET","WAKE","WALL","WAR","WASHER","WASHINGTON","WATCH","WATER","WAVE","WEB",
            "WELL","WHALE","WHIP","WIND","WITCH","WORM","YARD"};

    public static void main(String[] args) throws IOException, InterruptedException {

        // Game settings for difficulty level, number of guesses, and number of rounds
        int[] levels = {40, 20, 10};
        String[] cardinals = {"one", "two", "three", "four", "five"};
        int level = levels[1]; // medium difficulty level
        int guesses = 3; // three guesses
        int rounds = 3; // three rounds with one main word each

        // Explains game
        System.out.println("This is a word game, you will be given a main word, and then you will guess " + cardinals[rounds - 1]);
        System.out.println("words that are associated with that word. Your goal is to match as many words as ");
        System.out.println("possible from a list of words commonly associated with that main word.");
        System.out.println("Rules: ");
        System.out.println("1. Compound words are okay, ex: \"ice cream\", but must be no longer than two words.");
        System.out.println("2. Your guess cannot be a substring of the main word, ex: \"dino\" for \"DINOSAUR\".");
        System.out.println("3. Your guess cannot contain the entire main word, ex: \"strawberry\" is not ");
        System.out.println("allowed for \"BERRY\", but \"cherry\" is okay." );
        System.out.println();
        System.out.println("GAME BEGINS");

        /* Gets the main words list of length rounds from the word bank array wordList, will remain
        the same for this entire game. */
        String[] mainWords = WordManager.pick(mainWordBank,rounds);

        /* Below loop runs through all main functions for a round then stores results for each
        round in a 2-d array before moving onto next loop */
        String[][] allGuesses = new String[rounds][guesses];
        int[][] allPoints = new int[rounds][guesses];

        for (int round = 0; round < rounds; round++) {
            // Gets and stores user guesses for a single round
            System.out.println("ROUND " + cardinals[round].toUpperCase());
            String[] roundGuesses = WordManager.collectGuesses(mainWords[round], guesses, round);
            allGuesses[round] = roundGuesses;

            // Retrieves and saves the common word list for the round
            String[] commonWords = CommonWords.getCommonWords(mainWords[round], level);

            // Calculates the points for the round
            int[] roundPoints = WordManager.calculatePoints(commonWords, roundGuesses);
            allPoints[round] = roundPoints;

            // Reveals round summary information to user
            WordManager.reveal(mainWords[round], commonWords, roundGuesses, roundPoints);
        }

        // Displays a simple table of game results to user
        int sumTotal = 0;
        System.out.println("GAME SUMMARY");
        for (int round = 0; round < rounds; round++) { //rows
            System.out.print("ROUND " + cardinals[round].toUpperCase() + " : ");
            for (int guess = 0; guess < guesses; guess++) { //cols
                System.out.print(allGuesses[round][guess] + " (" + allPoints[round][guess] + ") ");
            }
            System.out.print(" = " + Arrays.stream(allPoints[round]).sum());
            sumTotal += Arrays.stream(allPoints[round]).sum();
            System.out.println();
        }
        System.out.println("Total Score = " + sumTotal);
        System.out.println("(Out of a possible " + rounds*guesses + " points)");



    }
}
