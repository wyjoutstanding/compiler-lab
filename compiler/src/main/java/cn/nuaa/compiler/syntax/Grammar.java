package cn.nuaa.compiler.syntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/20 19:20
 * @Version 1.0
 * @Function: 从文件读取文法四元式：<S,VN,VT,P>
 */
public class Grammar {
    private String startVN; // 文法开始字符
    private List<String> terminalList = new ArrayList<String>(); // 终结符列表
    private List<String> notTerminalList = new ArrayList<String>(); // 非终结符列表
    private Map<String, List<List<String>>> production = new HashMap<String, List<List<String>>>(); // 产生式

    // 输入文件的路径
    public Grammar(String pathName) {
        initGrammar(pathName);
    }
    /**
     *  初始化文法：从文件读入文法开始符、终结符、非终结符、产生式
     * @param pathName 文法存储文件路径名
     */
    private void initGrammar(String pathName) {
        File file = new File(pathName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tmp = null;
            String[] tmps = null;
            int line = 1;
            startVN = reader.readLine();
            tmps = reader.readLine().split(" ");
            notTerminalList = new ArrayList(Arrays.asList(tmps));
            tmps = reader.readLine().split(" ");
            terminalList = new ArrayList(Arrays.asList(tmps));
            while((tmp = reader.readLine()) != null) {
//                System.out.println("line"+line+":"+tmp);
                tmps = tmp.split("->");
                String key = tmps[0].replace(" ", ""); // 产生式左侧
                tmp = tmps[1];
                tmps = tmp.split("\\|");
                List<List<String>> lists = new ArrayList<List<String>>();
                for (String t: tmps // 每个候选式
                ) {
                    List<String> list1 = new ArrayList<String>();
                    String[] sts = t.split(" "); // 根据空格分割
                    for (int i = 0; i < sts.length; i ++) {
                         if (!"".equals(sts[i])) list1.add(sts[i]); // 字符串常量和对象不可用==直接比较，他们比较的是应用对象，equals比较值
                    }
                    lists.add(list1); // 一个候选式
                }
                production.put(key, lists);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStartVN() {
        return startVN;
    }

    public List<String> getTerminalList() {
        return terminalList;
    }

    public List<String> getNotTerminalList() {
        return notTerminalList;
    }

    public Map<String, List<List<String>>> getProduction() {
        return production;
    }

    //  打印结果
    public void print() {
        System.out.print("起始符号: ");
        System.out.println(startVN);
        System.out.print("非终结符:  ");
        for (String s: notTerminalList
             ) {
            System.out.print(s+" ");
        }
        System.out.println();
        System.out.print("终结符:  ");
        for (String s: terminalList) {
            System.out.print(s+" ");
        }
        System.out.println();
        System.out.println("产生式: ");
        Set<String> keySet = production.keySet();
        for (String key: keySet) {
            List<List<String>> list1 = production.get(key);
            for (List<String> ls : list1) {
                System.out.print(key+"->");
                for (String l : ls) {
                    System.out.print(" "+ l);
                }
                System.out.println();
            }
        }
    }

}
