package cn.nuaa.compiler.syntax;

import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/25 15:38
 * @Version 1.0
 */
public class SyntaxAnalysisTest {
    String path = "D:\\Desktop\\compiler-lab\\test-pool\\syntax"; // 文法和输入串所在目录的绝对路径

    /**
     * 使用try-catch包装测试
     * @param grammar 文法
     * @param inputString 输入串
     */
    public void tryAnalysis(Grammar grammar, List<String> inputString) {
        SyntaxAnalysis syntaxAnalysis = new SyntaxAnalysis(grammar);
        try {
            syntaxAnalysis.mainController(grammar, inputString);
        }
        catch (Exception e) {
            inputString.remove("#");
            Collections.reverse(inputString);
            System.out.println("输入串为："+inputString.toString());
            System.out.println("============分析结果==========\nSorry, 该字符串不满足给定的语法规则！！！\n");
        }
    }
    @Test
    public void test0() {
        Grammar grammar = new Grammar(path+"\\grammar-1.txt");
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("i", "+")));
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("i", "+=")));
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("i", "*", "i", "+")));
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("i", "*", "i", "+", "(", ")")));
    }
    @Test
    public void test1() {
        Grammar grammar = new Grammar(path+"\\grammar-2.txt");
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("(", "a",",","^",")"))); // 满足
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("(", "a",",","^"))); // 不满足
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("(", "a",",","^",",","a",")"))); // 满足
    }
    @Test
    public void test2() {
        Grammar grammar = new Grammar(path+"\\grammar-3.txt");
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("(", "^",")","*"))); // 满足
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("(", "^",")"))); // 满足
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("(", "^","-"))); // 不满足
    }
    @Test
    public void test3() {
        Grammar grammar = new Grammar(path+"\\grammar-4.txt");
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("id", "-","-","id","(","(","id",")",")"))); // 满足
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("id", "-","-","id","(","id",")",")"))); // 不满足
    }
    @Test
    public void test4() { // 输入文法不是LL（1）:错误检测
        Grammar grammar = new Grammar(path+"\\grammar-5.txt");
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("a","b","c"))); // 满足
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("a","b","c","a","b","c"))); // 满足
        tryAnalysis(grammar,  new ArrayList<String>(Arrays.asList("a","b","c","a","b","c","c"))); // 不满足
    }

    /**
     * 根据路径名读取测试输入串
     * @param pathName 绝对路径名
     * @return 预处理后的输入串列表
     */
    public List<List<String>> readData(String pathName) {
        File file = new File(pathName);
        BufferedReader bufferedReader = null;
        List<List<String>> dataList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String tmp = null;
            String[] tmps = null;
            int line = 1;
            while((tmp = bufferedReader.readLine()) != null) {
//                System.out.println("line"+line+":"+tmp);
                tmps = tmp.split(" ");
                List<String> ret = new ArrayList<>(Arrays.asList(tmps));
                ret.removeIf(string->string.equals("")); // 去除空串
                dataList.add(ret);

            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 根据指定的文法文件，分析输入串是否合法
     * @param grammarFileName
     * @param inputFileName
     */
    public void testAnalysis(String grammarFileName, String inputFileName) {
        String grammarPathName = path + "\\" + grammarFileName;
        String inputFilePathName = path + "\\"+ inputFileName;
        List<List<String>> dataList = readData(inputFilePathName);
        Grammar grammar = new Grammar(grammarPathName);
        for (List<String> inputString: dataList) {
            tryAnalysis(grammar, inputString);
        }
    }
    @Test
    public void testByFile() { // 根据改变文法、输入串的文件名即可测试
        testAnalysis("grammar-1.txt", "g1-test-input.txt");
        testAnalysis("grammar-2.txt", "g2-test-input.txt");
        testAnalysis("grammar-3.txt", "g3-test-input.txt");
        testAnalysis("grammar-4.txt", "g4-test-input.txt");
    }
}
