package cisc191;

import static org.junit.jupiter.api.Assertions.*;

class WordManagerTest {

    @org.junit.jupiter.api.Test
    void pick() {
            // Because it randomizes I will just test that length of output array is appropriate
            String[] words = {"a","b","c","d"};
            int rounds = 3;
            assertEquals(3, WordManager.pick(words,rounds).length);
    }

    @org.junit.jupiter.api.Test
    void occurs() {
        String str = "peppermint";
        String substr = "mint";
        assertEquals(true, WordManager.occurs(str,substr));
    }

    @org.junit.jupiter.api.Test
    void calculatePoints() {
    }

}