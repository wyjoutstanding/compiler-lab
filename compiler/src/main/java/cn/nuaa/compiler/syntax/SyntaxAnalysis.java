package cn.nuaa.compiler.syntax;


import java.util.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/20 19:11
 * @Version 1.0
 */
public class SyntaxAnalysis {
    private PredictionTable predictionTable;
    private String epsilon = "ɛ"; // 空串定义

    public SyntaxAnalysis(Grammar grammar) {
        predictionTable = new PredictionTable(grammar); // 预测分析表构造
    }
    /**
     * 主控程序，接受字符串，推导判断其是否合乎语法规则
     * @param inputString
     * @return
     */
    public boolean mainController(final Grammar grammar, List<String> inputString) throws Exception {
        Stack<String> symbolStack = new Stack<String>(){{// 符号栈初始化
            push("#");
            push(grammar.getStartVN());
        }};
        inputString.add("#");
        // 输出处理
        int cnt = 1;
        String outputString = ""; // 打印字符串
//        outputString += String.format("%-10s%-20s%20s%40s","步骤","符号栈","输入栈","所用产生式")+"\n";
        outputString += String.format("%-10s%-40s%40s%40s","Step","Symbol Stack","Input Stack","Used Production")+"\n";
        outputString += String.format("%-10d%-40s%40s", cnt,symbolStack.toString(),inputString)+"\n";
        // 输入栈初始化
        Stack<String> inputStack = new Stack<String>();
        Collections.reverse(inputString); // 反转推入栈中
        inputStack.addAll(inputString);
        // 主体逻辑
        while (symbolStack.size() != 1 || inputStack.size() != 1) {
            String symbolTop = symbolStack.peek();
            String inputTop = inputStack.peek();
            if (grammar.getTerminalList().contains(symbolTop)) { // 符号栈顶为终结符
                if (symbolTop.equals(inputTop)) {
                    symbolStack.pop();
                    inputStack.pop();
                    // 输出
                    List<String> list = new ArrayList<>(inputStack);
                    Collections.reverse(list);
                    cnt ++;
                    outputString += String.format("%-10d%-40s%40s", cnt, symbolStack.toString(),list.toString())+"\n";
                }
                else throw new Exception("非法输入串");
            }
            else { // 符号栈顶不是终结符（包含空串情况+ 非终结符）
                if (predictionTable.getPredictionTable().containsKey(Arrays.asList(symbolTop, inputTop))) { // 预测分析表存在对应产生式
                    symbolStack.pop(); // 弹出符号栈顶
                    List<String> production = predictionTable.getPredictionTable().get(Arrays.asList(symbolTop, inputTop));
                    if (!production.get(0).equals(epsilon)) { // 产生式右部不是空串
                        // 将产生式反向压入符号栈
                        ListIterator<String> listIt =  production.listIterator(production.size()); // 定位最后一个
                        while (listIt.hasPrevious()) { // 反向压栈
                            symbolStack.push(listIt.previous());
                        }
                    }
                    // 输出格式转换
                    List<String> list = new ArrayList<>(inputStack);
                    Collections.reverse(list);
                    cnt ++;
                    outputString += String.format("%-10d%-40s%40s%40s", cnt, symbolStack.toString(),list.toString(),symbolTop+"->"+production.toString())+"\n";
                }
                else throw new Exception("非法输入串");
            }
        }
        System.out.println("==========================================================主控分析程序启动=========================================================\n"+outputString);
        inputString.remove("#");
        Collections.reverse(inputString);
        System.out.println("输入串为："+inputString.toString());
//        System.out.println("\n输入串为："+inputString.toString());
        System.out.println("============分析结果==========\n该字符串满足文法规则！！！\n");
        return true;
    }
}
