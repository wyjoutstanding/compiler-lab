package cn.nuaa.compiler.syntax;

import org.junit.Test;

import java.io.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/20 20:05
 * @Version 1.0
 */
public class GrammarTest {
    String path = "D:\\Desktop\\compiler-lab\\test-pool\\syntax";
    @Test
    public void test0() {
        Grammar grammar = new Grammar(path+"//grammar-1.txt");
        grammar.print();
    }
    @Test
    public void test1() {
        Grammar grammar = new Grammar(path+"//grammar-2.txt");
        grammar.print();
    }
    @Test
    public void test2() {
        Grammar grammar = new Grammar(path+"//grammar-3.txt");
        grammar.print();
    }
    @Test
    public void test3() {
        Grammar grammar = new Grammar(path+"//grammar-4.txt");
        grammar.print();
    }
    @Test
    public void test4() {
        Grammar grammar = new Grammar(path+"//grammar-5.txt");
        grammar.print();
    }

//=====================下面别看，这是我爬题库用的==============================
    public void saveResult(String savePath, int cnt, String res) { // 保存路径、编号、保存的内容
        String pathName = savePath+"\\第"+(cnt-1)+"节.txt";
        File outFile = new File(pathName);
        try {
            if (!outFile.exists()) outFile.createNewFile();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(outFile.getPath()),"UTF-8");
            FileWriter fileWriter = new FileWriter(outFile.getPath());
            System.out.println(outFile.getPath());
//            fileWriter.write(res);
            System.out.println(res);
//            fileWriter.flush();
//            fileWriter.close();
            outputStreamWriter.write(res);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
        catch (IOException e) {

        }
    }

    @Test
    public void test2_1() {
        String path = "D:\\Desktop\\PA-助教\\题库建立\\tmp.txt";
        String savePath = "D:\\Desktop\\PA-助教\\题库建立";
        File file = new File(path);
        BufferedReader reader = null;
        try {
            String string, res="";
            reader = new BufferedReader(new FileReader(file));
            int cnt = 0;
            while((string = reader.readLine()) != null) {
                if ("".equals(string)) continue;
                if (string.contains("/1.25")) continue;
                if ("得分/总分".equals(string)) continue;
                if ("A.".equals(string) || "B.".equals(string) ||"C.".equals(string) ||"D.".equals(string)) {
                    res += string;
                    res += reader.readLine() + "\n";
//                    if (!"A.".equals(string)) res += "\n";
                }
                else {
                    if ("正确答案".equals(string.substring(0,4))) res += string.substring(2,6)+"\n";
                    else if (string.contains("单选(1.25分)")) {
                            if ("1".equals(string.substring(0,1))) {
                                cnt ++;
                                if (!"".equals(res)) {
                                    saveResult(savePath, cnt, res);
                                    res = "";
                                }
//                                res += "=============第"+cnt+"节============="+"\n";
//                                String pathName = savePath+"\\第"+(cnt-1)+"节.doc";
//                                File outFile = new File(pathName);
//                                if (!outFile.exists()) outFile.createNewFile();
//                                FileWriter fileWriter = new FileWriter(outFile.getName());
//                                System.out.println(outFile.getPath()+outFile.getName());
////                                fileWriter.write(res);
//                                fileWriter.close();
                            }
                        res += "\n"+string.substring(0,1) + "、";
                    }
                    else {
                        if (string.contains("解析：")) {
                            res += "答案";
                            res += string + "\n";
                            res += "题型：单选题"+"\n";
                        }
                        else res += string + "\n";
                    }
                }
            }
//                System.out.println(res);
            saveResult(savePath, cnt+1, res);
            reader.close();
        }
        catch (IOException e) {

        }
    }

    @Test
    public void readData() {
        String pathName = "D:\\Desktop\\questionBank-pa-chapter7-8\\第1节.doc";
        File in = new File(pathName);
        if (in.exists()) {
            System.out.println("文件存在");
        }
        try {
            String res = "";
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(in.getPath()),"UTF-8");
            int tmp;
            while((tmp = inputStreamReader.read()) != -1) {
                res += (char)tmp;
                System.out.println((char)tmp);
            }
            System.out.println(res);
        }catch (IOException e) {

        }
    }
}