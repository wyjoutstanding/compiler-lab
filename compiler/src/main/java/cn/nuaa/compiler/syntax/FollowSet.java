package cn.nuaa.compiler.syntax;

import java.util.*;

/**
 * @Author: wuyangjun
 * @Date: 2019/11/24 21:07
 * @Version 1.0
 */
public class FollowSet {
    private Map<String, Set<String>> followSet;
    private Map<String, Boolean> status; // 标记VN的follow集是否求解完成
    private FirstSet firstSet;
    private String epsilon = "ɛ"; // 空串定义
    Map<String, String> dependTable; // 依赖表:Map<v1,v2>表示v1依赖v2的follow集

    /**
     * 构造函数：初始化对象，求解所有非终结符的follow集
     * @param grammar 文法
     */
    public FollowSet(Grammar grammar) {
        followSet = new HashMap<>();
        status = new HashMap<>();
        firstSet = new FirstSet(grammar);
        dependTable = new HashMap<>();
        createFollowSet(grammar);
    }
    /**
     * 递归求解指定非终结符vn对应的follow集
     * @param grammar 文法
     * @param vn 非终结符
     * @return follow集合
     */
    public Set<String> getFollowByChar(Grammar grammar, String vn) {
        if (status.get(vn)) return new HashSet<>(followSet.get(vn)); // follow[V]已找完，直接返回（剪枝）
        Set<String> retSet = new HashSet<>(); // 保存返回follow集
        for (Map.Entry<String, List<List<String>>> product: grammar.getProduction().entrySet()) { // 每个产生式
            String vn1 = product.getKey(); // 产生式左侧非终结符
            for (List<String> candidate: product.getValue()) { // 每个候选式
                if (candidate.contains(vn)) { // 候选式中出现vn
                    int idx = candidate.indexOf(vn);
                    if (idx == candidate.size() - 1) { // vn在候选式末尾
                        if (!vn.equals(vn1)) {
                            // 处理循环依赖：v1依赖于v2；v2依赖于v1
                            if (dependTable.containsKey(vn) && dependTable.get(vn).equals(vn1)) {
                                if (dependTable.containsKey(vn1) && dependTable.get(vn1).equals(vn)) {
                                    continue;
                                }
                            }
                            dependTable.put(vn,vn1);
                            // VN1是产生式的左侧非终结符,将其follow加入VN
                            // 若是循环依赖，则调用关系为vn->vn1->vn，递归回溯过程自动做并集
                            retSet.addAll(getFollowByChar(grammar, vn1));
                        }
                    }
                    else { // vn后跟其它字符
                        if (grammar.getTerminalList().contains(candidate.get(idx+1))) {// 非终结符后面紧跟终结符
                            retSet.add(candidate.get(idx+1));
                        }
                        else {// 非终结符后面紧跟非终结符
                            // 获取VN后面子串对应的first集合
                            Set<String> tmpSet = firstSet.getFirstByString(grammar, candidate.subList(idx+1,candidate.size()));
                            if (tmpSet.contains(epsilon) && !vn.equals(vn1)) {// VN可推出空
                                if (dependTable.containsKey(vn) && dependTable.get(vn).equals(vn1)) {
                                    if (dependTable.containsKey(vn1) && dependTable.get(vn1).equals(vn)) {
                                        continue;
                                    }
                                }
                                dependTable.put(vn,vn1);
                                retSet.addAll(getFollowByChar(grammar,vn1));
                            }
                            tmpSet.remove(epsilon);
                            retSet.addAll(tmpSet);
                        }
                    }
                }
            }
        }
        status.put(vn, true);
        // 必须在这里添加保存follow集
        if (followSet.containsKey(vn))followSet.get(vn).addAll(retSet);
        else followSet.put(vn, retSet);
        return new HashSet<>(followSet.get(vn));
    }

    /**
     * 求解每个非终结符VN的follow集
     * @param grammar 文法
     */
    private void createFollowSet(Grammar grammar) {
        followSet.put(grammar.getStartVN(), new HashSet<String>(Arrays.asList("#"))); // 起始符号初始化
        for (String vn: grammar.getNotTerminalList()) { // 状态初始化，表示非终结符vn未找到Follow集
            status.put(vn,false);
        }
        // 遍历所有非终结符
        for (String vn: grammar.getNotTerminalList()) {
            // follow[vn]尚未求解完成，继续求解，dfs思想
            if (!status.get(vn)) {
//                if (followSet.containsKey(vn)) followSet.get(vn).addAll(getFollowByChar(grammar, vn));
//                else followSet.put(vn,getFollowByChar(grammar,vn));
                getFollowByChar(grammar, vn);
            }
        }
    }

    public Map<String, Set<String>> getFollowSet() {
        return followSet;
    }
}