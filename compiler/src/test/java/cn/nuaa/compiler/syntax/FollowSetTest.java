package cn.nuaa.compiler.syntax;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/24 22:07
 * @Version 1.0
 */
public class FollowSetTest {
    String path = "D:\\Desktop\\compiler-lab\\test-pool\\syntax";
    @Test
    public void test0() {
        Grammar grammar = new Grammar(path+"\\grammar-1.txt");
        FollowSet followSet = new FollowSet(grammar);
        System.out.println(followSet.getFollowSet().toString());
    }
    @Test
    public void test1() {
        Grammar grammar = new Grammar(path+"\\grammar-2.txt");
        FollowSet followSet = new FollowSet(grammar);
        System.out.println(followSet.getFollowSet().toString());
    }
    @Test
    public void test2() {
        Grammar grammar = new Grammar(path+"\\grammar-3.txt");
        FollowSet followSet = new FollowSet(grammar);
        System.out.println(followSet.getFollowSet().toString());
    }
    @Test
    public void test3() {
        Grammar grammar = new Grammar(path+"\\grammar-4.txt");
        FollowSet followSet = new FollowSet(grammar);
        System.out.println(followSet.getFollowSet().toString());
    }
    @Test
    public void test4() {
        Grammar grammar = new Grammar(path+"\\grammar-5.txt");
        FollowSet followSet = new FollowSet(grammar);
        System.out.println("FOLLOW集："+followSet.getFollowSet().toString());
    }
}