# Formula
简单粗暴的公式解析实现，用ScriptEngine来解析Javascript脚本


* 中文表达式解析，支持中文公式，支持JS常见语法
 * // 如果
 * // 那么
 * // 否则
 * // 取最大(数值1, 数值2)
 * // 取最小(数值1, 数值2)
 * // > => <= < !===
 * // 或者
 * // 并且
 * // 四舍五入进位(数值, 进位单位)
 * // 向上进位(数值, 进位单位)
 * // 向下进位(数值, 进位单位)
 * TODO::缺少 返回，包含扩展，可用JS自行编写逻辑
 */

```
public class FormulaTester {

    public static void main(String[] args) {
        String f = "1+2";
        f = "四舍五入进位(4,1)";
        System.out.println(Formula.eval(f));
        Map<String, String> p = new HashMap<>();
        p.put("重量", "100.6");
        p.put("体积", "0.1");
        //注意 表达式记得换行，如果不换行会有异常，或者兼容js写法用 {} 括号编写逻辑
        f = "如果 重量 > 100 那么 四舍五入进位(重量,1)\n" +
                "如果 重量 < 100 那么 100";
        System.out.println(Formula.eval(p, f));
    }

}
```
