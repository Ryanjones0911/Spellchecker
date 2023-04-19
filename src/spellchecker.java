import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

//This spellchecker is currently set to check against the words_alpha.txt dictionary found here: https://github.com/dwyl/english-words

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
    public ArrayList<String> Spellcheck(String word)
    {
        ArrayList<String> results = new ArrayList<>();

        //call WordIsInSet to see if the word matches exactly with anything in the set. If there is no match...
        if(!WordIsInSet(word))
        {
            //...check if the given word is a match for any word in the wordlsit if adjacent characters
            //are swapped to account for potential mispellings
            results.addAll(AdjacentSwap(word));
            
            //..check if inserting a character in bewteen adjacent characters results in a match
            results.addAll(SingleCharInsertion(word));
                
            //..check if deleting a character in bewteen adjacent characters results in a match
            results.addAll(SingleCharDelete(word));
                    
            //check if replacing a char in the word with another char results in a match
            results.addAll(ReplaceChar(word));
        }
        else
        {
            results.add(word);
        }
        return results;
    }

    private ArrayList<String> ReplaceChar(String word) 
    {
        //iterate through char array and replace each letter with every other letter in the alphabet
        //and see if the resulting word results in a match

        char[] charArray = word.toCharArray();
        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        for(char c : charArray)
        {
            characters.add(c);
        }
       
        for(int i = 0; i < word.length(); i++)
        {
            for(char j = 'a'; j <= 'z'; j++)
            {
                characters.set(i, j);

                if(WordIsInSet(arrayListToString(characters)))
                {
                    result.add(arrayListToString(characters));
                    break;
                }
                else
                {
                    characters.clear();
                    for(char c : charArray)
                    {
                        characters.add(c);
                    }
                }
            }
        }
        return result;
    }

    private ArrayList<String> SingleCharDelete(String word) 
    {
        //iterate through char array and delete each letter to see if the resulting word minus the deleted letter
        //results in a match. same logic as charinsertion, just without the alphabet. O(n^2)

        char[] charArray = word.toCharArray();
        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        for(char c : charArray)
        {
            characters.add(c);
        }
       
        for(int i = 0; i < word.length(); i++)
        {
            characters.remove(i);

            if(WordIsInSet(arrayListToString(characters)))
            {
                result.add(arrayListToString(characters));
                break;
            }
            else
            {
                characters.clear();
                for(char c : charArray)
                {
                    characters.add(c);
                }
            }
        }
        return result;
    }

    private ArrayList<String> SingleCharInsertion(String word)
    {
        //iterate through char array and basically just test for each character in the alphabet inserted between every letter
        //to see if there's a match. Time complexity O(n^2 * 26). The 26 comes from the characters of the alphabet

        //this algo needs to insert characters at any given position in the char array.
        //to do this means to push all entries in the array back to make room for a new
        //array entry. This is not something that basic array easily supports
        //hence the need to cast it to an arraylist
        char[] charArray = word.toCharArray();
        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        for(char c : charArray)
        {
            characters.add(c);
        }
       
        for(int i = 0; i <= word.length(); i++)
        {
            for(char j = 'a'; j <= 'z'; j++)
            {
                characters.add(i, j);

                if(WordIsInSet(arrayListToString(characters)))
                {
                    result.add(arrayListToString(characters));
                }
                else
                {
                    characters.clear();
                    for(char c : charArray)
                    {
                        characters.add(c);
                    }
                }
            }
        }
        return result;
    }
    
    private ArrayList<String> AdjacentSwap(String word) 
    {
        //time complexity of this algo is O(n^2), and given the task it is performing I'm not sure it's possible to reduce it further.
        //we run through the swap, which is an O(n-1) operation, but we necessarily have to compare each resulting word against our hashset.
        //this check is also O(n) as it involves the creation of a new string. O((n-1) * n) = O(n^2)
        char[] charArray = word.toCharArray();
        ArrayList<String> result = new ArrayList<>();

        //swap each adjacent characters and call spellcheck. If never true, there are no adjacent misspellings. otherwise return true for found word
        for(int i = 0; i < word.length() - 1; i++)
        {
            char temp = charArray[i];
            charArray[i] = charArray[i+1];
            charArray[i + 1] = temp;

            if (WordIsInSet(new String(charArray)))
            {
                result.add(new String(charArray));
            }
            else
            {
                //this is important as you have to reset the word to its original state. Otherwise it will continue to shift characters
                //of the new word you've created, and not the one you're intending to use.
                charArray = word.toCharArray();
            }
        }
        return result;
    }

    public boolean WordIsInSet(String word)
    {
        if(wordset.contains(word))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private String arrayListToString(ArrayList<Character> list)
    //this is just a helper function that will convert an arraylist to a proper string like you would
    //expect to see from a word.
    {
        StringBuilder sb = new StringBuilder();
        for(char c : list)
        {
            sb.append(c);
        }
        String result = sb.toString();
        return result;
    }
}