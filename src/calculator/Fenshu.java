package calculator;

public class Fenshu {
    //分子
    private int fenzi;

    //分母
    private int fenmu;

    public Fenshu() {
        super();
    }
    public Fenshu(int fenzi, int fenmu) {
        this.fenzi = fenzi;
        this.fenmu = fenmu;
    }

    /**
     * 按照Fraction的string形式生成Fraction对象
     *
     * @param fenshuString
     */
    public Fenshu(String fenshuString) {
        if (!fenshuString.contains("/")) {    //整数
            int num = Integer.parseInt(fenshuString);  //字符串转化为整型
            this.fenmu = 1;                 //分母为1
            this.fenzi = num;                  //分子为该整数
        } else if (!fenshuString.contains("'")) {            //真分数
            int separator = fenshuString.indexOf("/");
            this.fenzi = Integer.parseInt(fenshuString.substring(0, separator));
            this.fenmu = Integer.parseInt(fenshuString.substring(separator + 1, fenshuString.length()));
        } else {
            //将带分数化为假分数
            int separatorOne = fenshuString.indexOf("'");
            int separatorTwo = fenshuString.indexOf("/");
            this.fenmu = Integer.parseInt(fenshuString.substring(separatorTwo + 1, fenshuString.length()));//分母
            int prefix = Integer.parseInt(fenshuString.substring(0, separatorOne));   //带分数的整数部分
            int fakeMolecule = Integer.parseInt(fenshuString.substring(separatorOne + 1, separatorTwo));//原分子
            this.fenzi = prefix * fenmu + fakeMolecule;    //新分子
        }
    }

    /**
     * 生成随机分数
     * @param max
     * @return
     */
    public static Fenshu newfenshu(int max) {
        //控制分母的范围在（0，max）
        int fenmu = (int) (Math.random() * max);
        if (fenmu == 0) {
            fenmu += 1;
        }

        //控制分数的范围在(0,max)
        int fenzi = (int) (Math.random() * (fenmu * max));
        if (fenzi == 0) {
            fenzi += 1;
        }

        Fenshu fenshu = new Fenshu();
        fenshu.setfenzi(fenzi);
        fenshu.setfenmu(fenmu);
        return fenshu;
    }

    /**
     * 将整数转为分数
     * @param num
     * @return
     */
    public static Fenshu intTofenshu(int num) {
        Fenshu fenshu = new Fenshu();
        fenshu.setfenmu(1);   //分母为1
        fenshu.setfenzi(num);
        return fenshu;
    }

    /**
     * 加法
     * @param fenshuA
     * @param fenshuB
     * @return
     */
    public static Fenshu add(Fenshu fenshuA, Fenshu fenshuB) {
        Fenshu fenshu = new Fenshu();
        int fenzi = fenshuA.fenzi * fenshuB.fenmu +
                fenshuB.fenzi * fenshuA.fenmu;
        int fenmu = fenshuA.fenmu * fenshuB.fenmu;
        fenshu.setfenzi(fenzi);
        fenshu.setfenmu(fenmu);
        return fenshu;
    }

    /**
     * 减法
     * @param fenshuA
     * @param fenshuB
     * @return
     */
    public static Fenshu subtraction(Fenshu fenshuA, Fenshu fenshuB) {
        Fenshu fenshu = new Fenshu();
        int fenzi = fenshuA.fenzi * fenshuB.fenmu -
                fenshuB.fenzi * fenshuA.fenmu;
        int fenmu = fenshuA.fenmu * fenshuB.fenmu;
        fenshu.setfenzi(fenzi);
        fenshu.setfenmu(fenmu);
        return fenshu;
    }

    /**
     * 乘法
     * @param fenshuA
     * @param fenshuB
     * @return
     */
    public static Fenshu multiplication(Fenshu fenshuA, Fenshu fenshuB) {
        Fenshu fenshu = new Fenshu();
        int fenzi = fenshuA.fenzi * fenshuB.fenzi;
        int fenmu = fenshuA.fenmu * fenshuB.fenmu;
        fenshu.setfenzi(fenzi);
        fenshu.setfenmu(fenmu);
        return fenshu;
    }

    /**
     * 除法
     * @param fenshuA
     * @param fenshuB
     * @return
     */
    public static Fenshu division(Fenshu fenshuA, Fenshu fenshuB) throws Exception {

        Fenshu fenshu = new Fenshu();
        int fenzi = fenshuA.fenzi * fenshuB.fenmu;
        int fenmu = fenshuA.fenmu * fenshuB.fenzi;
        fenshu.setfenzi(fenzi);
        fenshu.setfenmu(fenmu);
        return fenshu;
    }

    /**
     * 将分数转化为真分数
     * @return String
     */
    @Override
    public String toString() {
        if (fenmu == 1) {
            return fenzi + "";
        } else {
            int realint = fenzi / fenmu;           //真分数的整数部分
            int realfenzi = fenzi % fenmu;      //真分数的分子
            if (realfenzi == 0) {
                return realint + "";
            }
            //约分
            int maxCD = Math.abs(maxCommonDivisor(fenmu, realfenzi));
            if (realint == 0) {
                return realfenzi / maxCD + "/" + fenmu / maxCD;
            } else
                return realint + "'" + realfenzi / maxCD + "/" + fenmu / maxCD;
        }
    }

    /**
     * 辗转相除法求两个数的最大公约数
     * @param a
     * @param b
     * @return
     */
    private int maxCommonDivisor(int a, int b) {
        int c = a % b;
        if (c == 0) return b;
        else {
            return maxCommonDivisor(b, c);
        }
    }

    //判断两分数是否相等
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return ((Fenshu) obj).fenmu == this.fenmu && ((Fenshu) obj).fenzi == this.fenzi;
    }


    @Override
    public int hashCode() {
        return fenzi * 31 + fenmu;
    }

    public int getfenmu() {
        return fenmu;
    }

    public void setfenmu(int denominator) {
        this.fenmu = denominator;
    }

    public int getfenzi() {
        return fenzi;
    }

    public void setfenzi(int molecule) {
        this.fenzi = molecule;
    }

}
