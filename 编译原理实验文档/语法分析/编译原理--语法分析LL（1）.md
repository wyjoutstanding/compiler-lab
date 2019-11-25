# 编译原理--语法分析LL（1）

[TOC]

## 1、需求分析

实现的具体功能如下：

+ **任意的LL（1）文法**，以四元式形式输入`<开始符，非终结符，终结符，产生式>`
+ 支持**符号长度任意**的文法描述输入，比如`Expr -> id ExprTail`
+ 支持自定义空串符号
+ 可处理follow集中符号**循环依赖**的情况
+ **出错处理**：可判断文法是否为LL（1）；可判断输入串是否满足指定文法规则

## 2、算法设计

LL(1)分析流程：得到文法描述，构造first集，构造follow集，构造预测分析表，主控分析程序。

以上步骤具有线性依赖关系，后面的输入必须依赖前面的输出。

### 2.1 符号约定

+ `VN`,`VT`分别表示非终结符、终结符；希腊字母（`alpha、 beta`)表示字符串，`epsilon`表示空串

  

### 2.2 数据结构

```
// 产生式：每个终结符和非终结符用string表示，增强泛化能力；
//        一个非终结符可对应多个产生式
// 例如：存在产生式 id -> Expr id Type | D : Type
// 即可用以下方式存储: VN -> List<List<stirng>>
map<string, List<List<string> > > production

// first和follow集
Set<String,List<String>> firstSet, followSet

// 预测分析表:(VN,VT) -> 产生式右部
map<List<String>,List<string>> predictTable
```



### 2.3 First集构造

+ 递归或反复迭代均可

伪代码如下

```pseudocode

// 获取每个符号对应的first集合
// first[V]表示符号V的first集合
set getFirstByChar(char V) { 
    if (status[V] == true) return first[V] // first[V]已找完，直接返回（剪枝）
    for (beta in product[V] { // beta是V的产生式右部
        set = getFirstByString(beta)
        first[V].insert(set)
    }
    status[V] = true // 表示First[V]求解完毕
    return first[V]
}
// 根据产生式（字符串）得到相应的First集合
// 便于构造预测分析表时直接调用
// beta：产生式右部； retSet：保存要返回的集合（即beta对应的first）
set getFirstByString(string beta) { 
        if (beta[0] == epislon) { // 产生式第一个符号为空串
            retSet.insert(epislon)
        }
        else if (beta[0] == VT) { // 产生式第一个符号为终结符VT
            retSet.insert(beta[0])
        }
        else { // 产生式第一个符号为非终结符VN
            tset = getFirstByChar(beta[0])
            if (tset.has(epislon)) { // first集包含空串
                retSet.insert(tset-epislon) // 去除空串后加入集合
                if (beta.length == 1) retSet.insert(epislon) // 所有非终结符均可推出空串
                else retSet.insert(getFirstByString(beta[1..n-1])) // 对剩余产生式递归求解
            }
            else retSet.insert(tset) // first不含空串
        }
        return retSet
}
// 创建每个非终结符的first集
void createFirstSet() {
    for (VN in VNSet) { // 遍历每个非终结符
        if (!status[VN]) getFirstByChar(VN) // first[VN]尚未求解完成，继续求解，dfs思想
    }
}
```



### 2.4 Follow集构造

构造难点分析--依赖处理：

+ 单向依赖
  + 它依赖：A->B
  + 自依赖：A->A
+ 双向依赖：
  + 一次循环：A->B->A
  + N次循环：A->B->C->D->....->A

#### 迭代实现

+ 机器：反复遍历求解非终结符的FOLLOW集，直到当前遍历没有集合增加元素，停止。

#### 递归实现

+ 人：按需调用，设计依赖表，发现双向依赖则不再深入递归

伪代码如下（详细见Follow集实现）

```pseudocode
// 获取每个符号对应的follow集合
// follow[V]表示符号V的follow集合
set getFollowByChar(char VN) {
    if (status[VN] == true) return follow[VN]  // follow[V]已找完，直接返回（剪枝）
    for (beta in 所有候选式) { // 遍历所有候选式（即所有产生式右部）
        if (beta.has(VN)) { // 候选式包含非终结符VN
            idx = VN在beta中的位置
            if (VN 是最后一个符号 && VN != VN1) {
                retSet.insert(getFollowByChar(VN1)) // VN1是产生式的左侧非终结符,将其follow加入VN
            }
            else if (beta[idx+1] == VT) { // 非终结符后面紧跟终结符
                retSet.insert(beta[idx+1])
            }
            else if(beta[idx+1] == VN) { // 非终结符后面紧跟非终结符
                tset = getFirstByString(beta[idx+1...n-1]) // 获取VN后面子串对应的first集合
                retSet.insert(tset - epislon)
                if (tset.has(epislon) && VN != VN1) { // VN可推出空
                    retSet.insert(getFollowByChar(VN1)) // VN1是产生式的左侧非终结符
                }
            }
        }
    }
    status[VN] = true
    return retSet
}

// 创建每个非终结符的Follow集
void createFollowSet() {
    follow[S].insert('#') // 初始化，在文法开始符的Follow加上‘#’
    status均初始化为false
    for (VN in VNSet) { // 遍历每个非终结符
        // follow[VN]尚未求解完成，继续求解，dfs思想
        if (!status[VN]) follow[VN].insert(getFollowByChar(VN))
    }
}
```

### 2.5 预测分析表构造

遍历每个产生式，按规则求解即可。

### 2.6 主控程序

符号栈和输入栈初始化时记得压入`#`

仅当两个栈顶均为`#`时分析成功

## 3、分工

+ 算法设计：吴扬俊、张铮
+ 代码实现：武起龙、荆顺吉、常为
+ PPT演示：荆顺吉