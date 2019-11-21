package Database.Manager;

import Database.ServerDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class WordPoolManager
{
    private ServerDB DB = ServerDB.getInstance();

    public void addWord(String word)
    {
        try (Connection conn = DB.getConnection())
        {
            String QUERY = "INSERT INTO WORD_POOL (WORD) SELECT ? WHERE NOT EXISTS (SELECT * FROM WORD_POOL WHERE WORD = ?)";
            PreparedStatement ps = conn.prepareStatement(QUERY);
            ps.setString(1, word);
            ps.setString(2, word);
            ps.executeUpdate();

        } catch (Exception e)
        { e.printStackTrace(); }
    }

    public String[] getWords(int Amount)
    {
        List<String> words = new ArrayList<>();
        try (Connection conn = DB.getConnection())
        {
            String QUERY = "SELECT WORD FROM WORD_POOL ";
            PreparedStatement ps = conn.prepareStatement(QUERY);
            ResultSet result = ps.executeQuery();

            int count = 0;
            while(result.next() && count < Amount)
            {
                words.add(result.getString(1));
                count++;
            }
            return words.toArray(new String[1]);
        } catch (Exception e)
        { e.printStackTrace(); }
        return null;
    }

    public String[] getRandomWords(int Amount)
    {
        String[] words = getWords(9999);
        List<String> NEWS = new ArrayList<>();

        List<String> DUMP = new ArrayList<>(Arrays.asList(words));
        Collections.shuffle(DUMP,  new Random(System.currentTimeMillis()));

        for(int i = 0; i < Amount; i++) NEWS.add(DUMP.get(i));
        Collections.shuffle(NEWS, new Random(System.currentTimeMillis()));
        return NEWS.toArray(new String[1]);
    }

    public void init(File file)
    {
        if(!file.exists()) return;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)))
        {
            for(String line = br.readLine(); line != null; line = br.readLine())
            {
                addWord(line);
            }
        } catch (Exception e)
        { e.printStackTrace(); }
    }
}
