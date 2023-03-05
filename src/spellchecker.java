import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Spellchecker 
{
    private HashSet<String> words = new HashSet<>();

    //we do this to make sure every instance of the spellchecker object has a new hashset with the words from words.txt initialized and available for use
    public Spellchecker()
    {
        this.words = new HashSet<>();
        this.FillSet(); 
    }

    //this is a helper function used only to populate new instances of the spellchecker hashset with the words.txt wordlist
    private void FillSet()
    {
        //try catch block to input content of words.txt to our hashset. Throws an error if the file cannot be found.
        try 
        {
            File wordlist = new File("resources/words.txt"); 
            Scanner iterate = new Scanner(wordlist);

            //opens words.txt and adds every line as a new entry in the hashset
            while(iterate.hasNextLine())
            {
                String data = iterate.nextLine();
                words.add(data);
            }

            iterate.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Error: file not found");
            System.exit(1);
        }         
    }

    public boolean WordIsInSet(String word)
    {
        if(words.contains(word))
        {
            System.out.println("word found");
            return true;
        }
        else
        {
            System.out.println("word not found");
            return false;
        }
    }

    public void SwapAdjacent()
    {
        //check if the given word is a match for any word in the wordlsit if adjacent characters
        //are swapped to account for potential mispellings
    }
}
