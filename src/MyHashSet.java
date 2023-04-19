public class MyHashSet 
{
    //experimenting with designing & implementing my own hashing algorithm. Intend to use modulus hashing with seperate chaining
    /*
     * 370105 entries in words.txt list. It is advised that hash tables be constructed with at least 25% more space than needed,
     * therefore I have created an array of size 493474
     * 
     * hashkey = key (data being hashed) % number of available slots (or buckets)
    */
    String[] buckets = new String[493474];

    int key;
    int hashkey = key % 493474;
}
