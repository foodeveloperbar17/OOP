// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {

    public void testNoFollow1(){
        List<String> rules = stringToList("acab");
        Taboo<String> t = new Taboo<>(rules);
        List<String> res1 = Arrays.asList("c", "b");
        List<String> res2 = Arrays.asList("a");
        assertEquals(new HashSet<String>(res1) , t.noFollow("a"));
        assertEquals(new HashSet<>(res2), t.noFollow("c"));
    }

    public void testNoFollow2(){
        List<String> rules = stringToList("blaalb");
        Taboo<String> t = new Taboo<>(rules);
        List<String> res1 = Arrays.asList("a", "l");
        assertEquals(new HashSet<String>(res1) , t.noFollow("a"));
        List<String> res2 = Arrays.asList("l");
        assertEquals(new HashSet<>(res2), t.noFollow("b"));
        List<String> res3 = Arrays.asList("a", "b");
        assertEquals(new HashSet<>(res3), t.noFollow("l"));
    }

    public void testNoFollow3(){
//        test for empty results
        List<String> rules = stringToList("acab");
        Taboo<String> t = new Taboo<>(rules);

        assertEquals(Collections.emptySet(), t.noFollow("b"));
        assertEquals(Collections.emptySet(), t.noFollow("x"));


        rules = stringToList("claalb");
        t = new Taboo<>(rules);
        Collections.emptySet();
        assertEquals(Collections.emptySet(), t.noFollow("d"));
        assertEquals(Collections.emptySet(), t.noFollow("x"));
    }

    public void testReduce1(){
        List<String> rules = stringToList("acab");
        Taboo<String> t = new Taboo<>(rules);

        List<String> res1 = stringToList("axc");
        List<String> input1 = stringToList("acbxca");
        t.reduce(input1);
        assertEquals(res1, input1);

        List<String> res2 = stringToList("aaa");
        List<String> input2 = stringToList("aabbcac");
        t.reduce(input2);
        assertEquals(res2, input2);
    }

    public void testReduce2(){
        List<String> rules = stringToList("abcdea");
        Taboo<String> t = new Taboo<>(rules);

        List<String> res = stringToList("acebda");
        List<String> input = stringToList("acebda");
        t.reduce(input);
        assertEquals(res, input);
    }

    public void testReduce3(){
        List<String> rules = stringToList("abggdac");
        Taboo<String> t = new Taboo<>(rules);

        List<String> res = stringToList("acd");
        List<String> input = stringToList("abxcd"); //x will be changed with null
        input.set(2, null);
        System.out.println(input);
        t.reduce(input);
        assertEquals(res, input);
    }


    private List<String> stringToList(String s){
        List<String> list = new ArrayList<>();
        for(int i = 0; i < s.length(); i++){
            list.add(String.valueOf(s.charAt(i)));
        }
        return list;
    }
}
