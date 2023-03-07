import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Spellchecker
{
    private HashSet<String> wordset = new HashSet<>();

    //we do this to make sure every instance of the spellchecker object has a new hashset with the words from words.txt initialized and available for use
    public Spellchecker()
    {
        this.wordset = new HashSet<>();
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
                wordset.add(data);
            }

            iterate.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Error: file not found: " + e.getMessage());
            System.exit(1);
        }         
    }


    //performs a Spell Check on the string s with respect to the set of words, W. If s is in W, then the call to spellCheck(s) returns an iterable
    //collection that contains only s, since it is assumed to be spelled correctly in this case. Otherwise, if s is not in W, then the call to 
    //spellCheck(s) returns a list of every word in W that could be a correct spelling of s
    public void Spellcheck(String word)
    {
        //call WordIsInSet to see if the word matches exactly with anything in the set. If there is no match...
        if(!WordIsInSet(word))
        {
            //...check if the given word is a match for any word in the wordlsit if adjacent characters
            //are swapped to account for potential mispellings

            

        }


    }

    public boolean WordIsInSet(String word)
    {
        if(wordset.contains(word))
        {
            System.out.println("word found");
            return true;
        }
        else
        {
            //here only for testing
            System.out.println("word not found");
            return false;
        }
    }
}
