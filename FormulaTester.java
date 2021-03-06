

/**
 * @author tim
 */
public class FormulaTester {

    public static void main(String[] args) {
        String f;
        f = "四舍五入进位(4,1)";
        System.out.println(Formula.eval(f));
        String[][] p = {
                {"重量", "100.6"},
                {"体积", "0.1"},
        };

        //注意 表达式记得换行，如果不换行会有异常，或者兼容js写法用 {} 括号编写逻辑
        f = "如果 重量 > 100 那么 四舍五入进位(重量,1)\n" +
                "如果 重量 < 100 那么 100";
        System.out.println(Formula.eval(p, f));
    }

}
