package cn.nuaa.compiler.syntax;

import java.util.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/25 13:33
 * @Version 1.0
 */
public class PredictionTable {
    private Map<List<String>, List<String>> predictionTable;
    private String epsilon = "ɛ"; // 空串

    /**
     * 构造函数：初始化
     * @param grammar 文法
     */
    public PredictionTable(Grammar grammar) {
        predictionTable = new HashMap<>();
        createPredictionTable(grammar);
    }

    /**
     * 构造预测分析表
     * @param grammar 文法
     */
    private void createPredictionTable(Grammar grammar) {
        FirstSet firstSet = new FirstSet(grammar);
        FollowSet followSet = new FollowSet(grammar);
        for (Map.Entry<String, List<List<String>>> entry : grammar.getProduction().entrySet()) { // 遍历所有产生式
            String key = entry.getKey(); // 产生式左侧
            for (List<String> candidate: entry.getValue()) { // 候选式
                Set<String> tmpSet = firstSet.getFirstByString(grammar, candidate); // 右部的first集--first(beta)
                if (tmpSet.contains(epsilon)) { // 包含空串
                    tmpSet.addAll(followSet.getFollowByChar(grammar, key));
                    tmpSet.remove(epsilon);
                }
                // 填入预测分析表
                for (String vt: tmpSet) {
                    if (predictionTable.containsKey(Arrays.asList(key, vt))) {
                        System.out.println("Error：这不是一个LL（1）文法，含有多重入口");
                        System.exit(0);
                    }
                    predictionTable.put(Arrays.asList(key, vt), candidate);
                }
            }
        }
    }

    public Map<List<String>, List<String>> getPredictionTable() {
        return predictionTable;
    }

    /**
     * 格式化打印
     * @param grammar
     * @return
     */
    public String toString(Grammar grammar) {
        String retString = String.format("%-10s", " ");
        Set<String> terminals = new HashSet<>(grammar.getTerminalList());
        terminals.add("#");
        Set<String> notTerminals = new HashSet<>(grammar.getNotTerminalList());
        for (String vt: terminals) retString += String.format("%-20s", vt);

        retString += "\n";
        for (String vn: notTerminals) {
            retString += String.format("%-10s", vn);
            for (String vt: terminals) {
                retString += String.format("%-20s", predictionTable.get(Arrays.asList(vn, vt)));
            }
            retString += "\n";
        }
        return retString;
    }
}
