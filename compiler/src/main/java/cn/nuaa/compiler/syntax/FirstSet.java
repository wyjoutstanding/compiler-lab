package cn.nuaa.compiler.syntax;


import java.util.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/21 19:10
 * @Version 1.0
 */
public class FirstSet {
    Map<String, Set<String>> firstSet;
    Map<String, Boolean> status;
    static String epsilon = "ɛ"; // 空串定义

    public FirstSet(Grammar grammar) {
        firstSet = new HashMap<>();
        status  = new HashMap<>();
        for (String vn : grammar.getNotTerminalList()) {
            status.put(vn, false);
        }
//        createFirstSet(grammar);
    }

    /**
     * 获取每个符号对应的first集合,其中firstSet[V]表示符号V的first集合
     * @param grammar 文法
     * @param v 符号（终结符/非终结符）
     * @return first[v]集合
     */
    protected Set<String> getFirstByChar(Grammar grammar, String v) {
        // first[V]已找完，直接返回（剪枝）
        if (status.get(v) == true && firstSet.containsKey(v)) return new HashSet<>(firstSet.get(v)); // 返回副本
//        if (status.get(v) == true && firstSet.containsKey(v)) return firstSet.get(v);
//        Grammar.print();
        // 遍历v的每一个右侧候选式
        for (List<String> beta : grammar.getProduction().get(v)) {
            Set<String> tmpSet = new HashSet<>();
            if (!firstSet.isEmpty() && firstSet.containsKey(v)) tmpSet = firstSet.get(v); // First(v) 判断是否存在这个key;get返回first中set的引用
            tmpSet.addAll(getFirstByString(grammar, beta)); // 插入First(v)
            firstSet.put(v, tmpSet); // 填回first集
        }
        status.put(v,true); // 标记已找完v的first集
//        return firstSet.get(v);
//        firstSet.get(v).clone();
        return new HashSet<>(firstSet.get(v)); // 传递副本，而非自身引用
    }

    /**
     * 根据候选式（字符串）得到相应的First集合；便于构造FOLLOW集合预测分析表时直接调用
     * @param grammar 文法
     * @param beta 候选式
     * @return first[beta]集合
     */
    protected Set<String> getFirstByString(Grammar grammar, List<String> beta) {
        Set<String> retSet = new HashSet<>();
        if (epsilon.equals(beta.get(0)) ) { // 产生式第一个符号为空串:字符串值比较不用==，用equals
            retSet.add(epsilon);
        }
        else if (grammar.getTerminalList().contains(beta.get(0))) { // 产生式第一个符号为终结符VT
            retSet.add(beta.get(0));
        }
        else { // 产生式第一个符号为非终结符VN
            Set<String> tmpSet = getFirstByChar(grammar,beta.get(0));
            if (tmpSet != null && tmpSet.contains(epsilon)) { // fisrt集包含空
                tmpSet.remove(epsilon); // 删除空串
                retSet.addAll(tmpSet);
                if (beta.size() == 1) retSet.add(epsilon);
                else retSet.addAll(getFirstByString(grammar, beta.subList(1,beta.size()))); // 对剩余产生式递归求解
            }
            else retSet.addAll(tmpSet);
        }
//        return retSet;
        return new HashSet<>(retSet);
    }
    // 创建每个非终结符的first集
    public void createFirstSet(Grammar grammar) {
        // 遍历每个非终结符
        for (String vn : grammar.getNotTerminalList()) {
            // first[VN]尚未求解完成，继续求解，dfs思想
            if(!status.get(vn)) getFirstByChar(grammar, vn);
        }
    }
    @Override
    public String toString() {
        return firstSet.toString();
    }
}
