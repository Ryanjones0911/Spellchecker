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

            AdjacentSwap(word);

            //..check if inserting a character in bewteen adjacent characters results in a match
            SingleCharInsertion(word);

            //..check if deleting a character in bewteen adjacent characters results in a match
            SingleCharDelete(word);

            //check if replacing a char in the word with another char results in a match
            ReplaceChar(word);
        }
    }

    private void ReplaceChar(String word) 
    {

    }

    private void SingleCharDelete(String word) 
    {

    }

    private void SingleCharInsertion(String word)
    {

    }

    private boolean AdjacentSwap(String word) 
    {
        //time complexity of this algo is O(n^2), and given the task it is performing I'm not sure it's possible to reduce it further.
        //we run through the swap, which is an O(n-1) operation, but we necessarily have to compare each resulting word against our hashset.
        //this check is also O(n) as it involves the creation of a new string. O((n-1) * n) = O(n^2)
        char[] charArray = word.toCharArray();

        //swap each adjacent characters and call spellcheck. If never true, there are no adjacent misspellings. otherwise return true for found word
        for(int i = 0; i < word.length() - 1; i++)
        {
            char temp = charArray[i];
            charArray[i] = charArray[i+1];
            charArray[i + 1] = temp;

            if (WordIsInSet(new String(charArray)))
            {
                return true;
            }
            else
            {
                //this is important as you have to reset the word to its original state. Otherwise it will continue to shift characters
                //of the new word you've created, and not the one you're intending to use.
                charArray = word.toCharArray();
            }
        }
        return false;
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
