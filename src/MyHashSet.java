import java.util.LinkedList;

//I don't know if a custom hashset implementation was required, but doing it this way felt more in line with the spirit of the class
/*
* please note that as I know this will only be used for strings, I did not bother making it usable for other types. To do so would be
* outside the scope of this project. If this were intended to be used for programs other than this one, I would have made it type generic
* 
* I am also aware that this class does not implement all methods required of a set. Once again, implementing a fully functioning hashset
* seems outside the scope of this assignment. I have implemented those methods necessary for the function of this specific program.
* 
* 
* 370105 entries in words_long.txt list.
* 
* hashkey = key (data being hashed) % number of available slots (or buckets)
* 
* storing strings, can't do math on strings directly. Need to get added value of all the ascii chars that make up string
* and use that for modulus math
*/

public class MyHashSet
{
    //array of linked lists
    private LinkedList<String>[] buckets;

    //under the assumption that we don't know how much data will be hashed, we initialize the array to be used by our hashset
    //with an arbitrary size at first. This can and will later be resized.
    public MyHashSet()
    {
        this.buckets = new LinkedList[32];
        for(int i = 0; i < buckets.length; i++);
    }

    public void AddData(String data)
    {
        //if over half of table is full, increase size. Effectively a load factor of .5
        if(CountFilledSlots(buckets) > (buckets.length / 2))
        {
            buckets = IncreaseSize(buckets);
        }
        //get the added ascii num value of the string
        int stringAsNum = StringNumValue(data);

        //use that num to get the hashkey
        int hash = HashData(stringAsNum);

        //insert data into the table at the index of hashkey
        if(buckets[hash] == null)
        {
            buckets[hash] = new LinkedList<>();
        }
        buckets[hash].add(data);
    }

    //checks if a given value already exists in the hashset
    public boolean Contains(String data)
    {
        int hash = HashData(StringNumValue(data));
        if(buckets[hash] != null)
        {
            for(int i = 0; i < buckets[hash].size(); i++)
            {
                if(buckets[hash].get(i).equalsIgnoreCase(data))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void Display()
    {
        for(int i = 0; i < buckets.length; i++)
        {
            if(buckets[i] != null)
            {
                System.out.println(buckets[i]);
            }

        }
    }

    //make a new array twice the size of the original and rehash all existing values into the new array.
    //NOTE: I don't like how this works as it kind of rewrites the AddData method, but I can't think of a way to
    //      make it play nice with it right now. I'll revist if I have time.
    private LinkedList<String>[] IncreaseSize(LinkedList<String>[] buckets)
    {
        LinkedList<String>[] resized = new LinkedList[buckets.length * 2];
        for(int i = 0; i < buckets.length; i++)
        {
            if(buckets[i] != null)
            {
                for(int j = 0; j < buckets[i].size(); j++)
                {
                    int stringAsNum = StringNumValue(buckets[i].get(j));
                    int hash = HashData(stringAsNum);
                    if(resized[hash] == null)
                    {
                        resized[hash] = new LinkedList<>();
                    }
                    resized[hash].add(buckets[i].get(j));
                }
            }
        }
        return resized;
    }

    //storing strings, can't do math on strings directly. Need to get added value of all the ascii chars that make up string
    //and use that for modulus math
    private int StringNumValue(String string)
    {
        int result = 0;
        for(int i = 0; i < string.length(); i++)
        {
            char c = string.charAt(i);
            result += (int) c;
        }
        return result;
    }

    //hashkey = key (data being hashed) % number of available slots (or buckets)
    private int HashData(int stringAsNum)
    {
        return stringAsNum % buckets.length;
    }

    //used to determine if the hash table needs to be resized. Counts & returns number of slots in table that have data.
    //if greater than half, indicates table needs to be resized
    private int CountFilledSlots(LinkedList<String>[] buckets)
    {
        int count = 0;
        for(int i = 0; i < buckets.length; i++)
        {
            if(buckets[i] != null)
            {
                count++;
            }
        }
        return count;
    }
}
