package Database.Manager;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordPoolManagerTest
{

    @Test
    void addWord()
    {
        WordPoolManager wm = new WordPoolManager();
        File file = new File(System.getProperty("user.home") + "./System/words.txt");
        wm.init(file);
        System.out.println(Arrays.toString(wm.getWords(30)));
        System.out.println(Arrays.toString(wm.getRandomWords(5)));
        System.out.println(Arrays.toString(wm.getRandomWords(5)));
        System.out.println(Arrays.toString(wm.getRandomWords(5)));
        System.out.println(Arrays.toString(wm.getRandomWords(5)));
    }
}