import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


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
     * 预内置函数
     * 可增加Lodash之类的引用
     */
    static String ADD_JS = "" +
            "function round(a,b){return Math.round(a+b)}\n" +
            "function floor(a,b){return Math.floor(a+b)}\n" +
            "function ceil(a,b){return Math.ceil(a+b)}\n";
    /**
     * 简单标记，可直接替换
     */
    static String[][] SAMPLE_TAGS = {
            {"如果", " if( "},
            {"那么", " ) "},
            {"否则", " else "},
            {"或者", " || "},
            {"并且", " && "},
            {"取最小", "Math.min"},
            {"取最大", "Math.max"},
            {"四舍五入进位", "round"},
            {"向上进位", "ceil"},
            {"向下进位", "floor"}
    };


    /**
     * 运行JS，返回结果
     */
    static String runs(String js) {
        String result = "";
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
        try {
            //先运行内置函数
            scriptEngine.eval(ADD_JS);
            result = scriptEngine.eval(js).toString();
        } catch (ScriptException e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n\n===> JS START" + js);
            System.out.println("<=== JS END\n\n");
        }
        return result;

    }

    /**
     * 无参数执行
     */
    static String eval(String formulaStr) {
        String result = formulaStr;
        result = parse2js(result);
        return runs(result);
    }

    /**
     * 带参数执行
     */
    static String eval(String[][] params, String formulaStr) {
        String result = formulaStr;
        result = parseParams(params, result);
        result = parse2js(result);
        return runs(result);
    }


    /**
     * 翻译参数
     */
    static String parseParams(String[][] params, String formulaStr) {
        String result = formulaStr;
        System.out.println("\n\n =====> PARAMS START");
        for (String[] param : params) {
            if (param.length > 1) {
                result = result.replaceAll(param[0], "'" + param[1] + "'");
                System.out.println("key = " + param[0] + ", value = " + param[1]);
            }
        }
        System.out.println("<===== PARAMS END \n\n");
        return result;
    }

    /**
     * 翻译成JS
     */
    static String parse2js(String formulaStr) {
        String result = formulaStr;
        for (String[] tag : SAMPLE_TAGS) {
            if (tag.length > 1) {
                result = result.replaceAll(tag[0], tag[1]);
            }
        }
        //TODO::增加复杂自定义标签逻辑
        return result;
    }

}
