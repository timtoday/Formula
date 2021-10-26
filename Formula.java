import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author tim
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
public class Formula {

    /**
     * 内置函数
     */
    static String addjs = "" +
            "function round(a,b){return Math.round(a+b)}\n" +
            "function floor(a,b){return Math.floor(a+b)}\n" +
            "function ceil(a,b){return Math.ceil(a+b)}\n";

    static String runjs(String js) {
        String result = "";
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
        try {
            //先运行内置函数
            scriptEngine.eval(addjs);
            result = scriptEngine.eval(js).toString();
        } catch (ScriptException e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n\n===> JS START" + js);
            System.out.println("<=== JS END\n\n");
            return result;
        }

    }

    static String eval(String formulaStr) {
        String result = parse(formulaStr);
        return runjs(result);
    }

    static String eval(Map<String, String> params, String formulaStr) {
        String result = parse(params, formulaStr);
        return runjs(result);
    }

    static String parse(Map<String, String> params, String formulaStr) {
        String result = formulaStr;
        result = parseParams(params, result);
        result = parse2js(result);
        return result;
    }

    static String parse(String formulaStr) {
        String result = formulaStr;
        result = parse2js(result);
        return result;
    }

    static String parseParams(Map<String, String> params, String formulaStr) {
        String result = formulaStr;
        Iterator it = params.entrySet().iterator();
        System.out.println("\n\n =====> PARAMS START");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            result = result.replaceAll(entry.getKey().toString(), "'" + entry.getValue() + "'");
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }
        System.out.println("<===== PARAMS END \n\n");
        return result;
    }

    static String parse2js(String formulaStr) {
        String result = formulaStr;
        // 如果
        result = result.replaceAll("如果", " if( ");
        //那么
        result = result.replaceAll("那么", " ) ");
        //否则
        result = result.replaceAll("否则", " else ");
        //返回
        result = result.replaceAll("返回", " return ");
        //或者
        result = result.replaceAll("或者", " || ");
        //并且
        result = result.replaceAll("并且", " && ");
        //min
        result = result.replaceAll("取最小", "Math.min");
        //max
        result = result.replaceAll("取最大", "Math.max");
        //四舍五入进位
        result = result.replaceAll("四舍五入进位", "round");
        //向上进位
        result = result.replaceAll("向上进位", "ceil");
        //向下进位
        result = result.replaceAll("向下进位", "floor");

        return result;
    }

}
