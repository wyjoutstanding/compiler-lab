package cn.nuaa.compiler.syntax;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/21 19:30
 * @Version 1.0
 */
public class FirstSetTest {
    String path = "D:\\Desktop\\compiler-lab\\test-pool\\syntax";
    @Before
    public void setUp() {

    }
    @Test
    public void test0() {
        Grammar grammar = new Grammar(path+"\\grammar-1.txt");
        FirstSet firstSet = new FirstSet(grammar);
        System.out.println(firstSet.getFirstByString(grammar, Arrays.asList("T","E'")));
        System.out.println(firstSet.getFirstByString(grammar, Arrays.asList("T","E'")));
        System.out.println(firstSet.getFirstByString(grammar, Arrays.asList("T'","T")));
        System.out.println(firstSet.getFirstByString(grammar, Arrays.asList("T'","T")));
        System.out.println(firstSet.getFirstByString(grammar, Arrays.asList("T'","E")));
        System.out.println(firstSet.toString());
    }
    @Test
    public void test1() {
        Grammar grammar = new Grammar(path+"\\grammar-2.txt");
        FirstSet firstSet = new FirstSet(grammar);
        firstSet.createFirstSet(grammar);
        System.out.println(firstSet.getFirstByString(grammar, Arrays.asList("S","T'")));
        System.out.println(firstSet.getFirstByString(grammar, Arrays.asList("T'")));
        System.out.println(firstSet.toString());
    }
    @Test
    public void test2() {
        Grammar grammar = new Grammar(path+"\\grammar-3.txt");
        FirstSet firstSet = new FirstSet(grammar);
        firstSet.createFirstSet(grammar);
        System.out.println(firstSet.toString());
    }
    @Test
    public void test3() {
        Grammar grammar = new Grammar(path+"\\grammar-4.txt");
        FirstSet firstSet = new FirstSet(grammar);
        firstSet.createFirstSet(grammar);
        System.out.println(firstSet.toString());
    }
    @Test
    public void test4() {
        Grammar grammar = new Grammar(path+"\\grammar-5.txt");
        FirstSet firstSet = new FirstSet(grammar);
        firstSet.createFirstSet(grammar);
//        System.out.println(firstSet.toString());
//        Set<String> set = firstSet.getFirstByChar(grammar,"S");
//        set.add("J");
        System.out.println(firstSet.toString());
    }
}