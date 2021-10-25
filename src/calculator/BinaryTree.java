package calculator;

public class BinaryTree {
    public static final String operation[] = {"+", "-", "*", "÷"};

    protected BinaryTree leftChild;
    protected BinaryTree rightChild;
    //符号，如果该节点为非叶节点则该属性有意义
    protected String opetation;
    //分数，如果该节点为叶子节点则该属性有意义
    protected Fenshu fenshu;

    /***
     * 中序遍历
     * @return
     */
    public String midTraversing() {
        BinaryTree node = this;
        StringBuilder expression = new StringBuilder();
        if (node.opetation != null) {
            String symbol = node.opetation;
            String left = node.leftChild.midTraversing();
            String right = node.rightChild.midTraversing();
            if (addBrackets(symbol, node.leftChild.opetation, 1)) {
                expression.append("(" + left + ") " + symbol + " ");
            } else {
                expression.append(left + " " + symbol + " ");
            }
            if (addBrackets(symbol, node.rightChild.opetation, 2)) {
                expression.append("(" + right + ")");
            } else {
                expression.append(right);
            }
            return expression.toString();
        } else {
            return node.fenshu.toString();
        }
    }

    /**
     * 判断是否需要加括号
     *
     * @param operation1  父结点运算符
     * @param operation2  孩子结点运算符
     * @param leftOrRight 1表示left，2表示right
     * @return
     */

    public static boolean addBrackets(String operation1, String operation2, int leftOrRight) {
        if (operation2 == null) {
            return false;
        }
        if (operation1.equals(operation[1])) {          //父结点是“-”
            if (operation2.equals(operation[1]) || operation2.equals(operation[0])) { //孩子结点是“+，-”
                if (leftOrRight == 2) return true;      //形如 a-(b-c)或a-(b+c)，需加括号
            }
        }
        if (operation1.equals(operation[2])) {          //父结点是“*”
            if (operation2.equals(operation[0]) || operation2.equals(operation[1])) {
                return true;                //形如(a-b)*c或(a+b)*c或a*(b-c)或a*(b+c)，括号不可省略，需加
            }
        }
        if (operation1.equals(operation[3])) {          //父结点是“÷”
            if (operation2.equals(operation[0]) || operation2.equals(operation[1])) {
                return true;                    //同上
            }
            if (leftOrRight == 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两棵树是否代表同一表达式
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            BinaryTree binaryTree = (BinaryTree) obj;
            boolean l = false;
            boolean r = false;
            boolean f;
            if (binaryTree.fenshu != null) {
                f = binaryTree.fenshu.equals(this.fenshu);
            } else {
                f = this.fenshu == null;
            }
            boolean s;
            if (binaryTree.opetation != null) {
                s = binaryTree.opetation.equals(this.opetation);
            } else {
                s = this.opetation == null;
            }
            if (binaryTree.leftChild != null && binaryTree.rightChild != null && f && s) {
                l = binaryTree.leftChild.equals(this.leftChild);
                r = binaryTree.rightChild.equals(this.rightChild);
                if (l == false && r == false && (binaryTree.opetation.equals(operation[0]) || binaryTree.opetation.equals(operation[2]))) {
                    r = binaryTree.rightChild.equals(this.leftChild);
                    l = binaryTree.leftChild.equals(this.rightChild);
                }
            } else {
                l = this.leftChild == null;
                r = this.rightChild == null;
            }
            return l && r && f && s;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hashCode = fenshu != null ? fenshu.hashCode() * 31 : 0;
        hashCode += opetation != null ? opetation.hashCode() : 0;
        hashCode += leftChild != null ? leftChild.hashCode() : 0;
        hashCode += rightChild != null ? rightChild.hashCode() : 0;
        return hashCode;
    }
}
