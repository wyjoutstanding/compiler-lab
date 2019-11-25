package cn.nuaa.compiler.syntax;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/25 13:54
 * @Version 1.0
 */
public class PredictionTableTest {
    String path = "D:\\Desktop\\compiler-lab\\test-pool\\syntax";
    @Test
    public void test0() {
        Grammar grammar = new Grammar(path+"\\grammar-1.txt");
        PredictionTable predictionTable = new PredictionTable(grammar);
        System.out.println(predictionTable.toString(grammar));
    }
    @Test
    public void test1() {
        Grammar grammar = new Grammar(path+"\\grammar-2.txt");
        PredictionTable predictionTable = new PredictionTable(grammar);
        System.out.println(predictionTable.toString(grammar));
    }
    @Test
    public void test2() {
        Grammar grammar = new Grammar(path+"\\grammar-3.txt");
        PredictionTable predictionTable = new PredictionTable(grammar);
        System.out.println(predictionTable.toString(grammar));
    }
    @Test
    public void test3() {
        Grammar grammar = new Grammar(path+"\\grammar-4.txt");
        PredictionTable predictionTable = new PredictionTable(grammar);
        System.out.println(predictionTable.toString(grammar));
    }
    @Test
    public void test4() {
        Grammar grammar = new Grammar(path+"\\grammar-5.txt");
        PredictionTable predictionTable = new PredictionTable(grammar);
        System.out.println(predictionTable.toString(grammar));
    }
}