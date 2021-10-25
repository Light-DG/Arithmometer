package calculator;

public class Expression {
    public static final String Operators[] = {"+", "-", "×", "÷"};

    //表达式的总长度 包括运算符和运算数，但不包括等号
    private int LengthOfExp;

    //这个表达式生成的二叉树
    private BinaryTree tree;

    //计算结果
    private Fenshu result;

    //表达式的字符串
    private String expression;


    public Expression() {
        super();
    }


    /**
     * 按照限制的最大自然数随机生成表达式二叉树的构造方法
     *
     * @param maxNum
     * @throws Exception
     */
    public Expression(int maxNum) throws Exception {
        //随机决定符号数量
        int OperatorNum = (int) (Math.random() * 3) + 1;
        tree = generateBinaryTree(maxNum, OperatorNum);
        result = getResult(tree);
        expression = tree.midTraversing() + " = ";
    }

    /**
     * 按照传递进来的表达式生成二叉树
     *
     * @param expression
     */
    public Expression(String expression) throws Exception {
        String nibolanExp = nibolan.rpn(expression);
        String[] elements = nibolanExp.split(" ");
        String[] realElements = new String[elements.length];
        int j = 0;
        //去掉split分割后可能出现的空字符串元素
        for (int i = 0; i < elements.length; i++) {
            if (!elements[i].equals("")) {
                realElements[j++] = elements[i];
            }
        }
        LengthOfExp = j - 1;
        BinaryTree binaryTree = new BinaryTree();
        nibolanToTree(realElements, binaryTree);
        tree = binaryTree;
        result = getResult(tree);
    }


    /**
     * 由逆波兰表达式生成二叉树
     *
     * @param elements
     * @param node
     */
    public void nibolanToTree(String[] elements, BinaryTree node) {
        if (isOperator(elements[LengthOfExp])) {
            node.opetation = elements[LengthOfExp];
            LengthOfExp--;
            node.rightChild = new BinaryTree();
            nibolanToTree(elements, node.rightChild);
            node.leftChild = new BinaryTree();
            nibolanToTree(elements, node.leftChild);
        } else {
            node.fenshu = new Fenshu(elements[LengthOfExp]);
            LengthOfExp--;
            return;
        }
    }

    /**
     * 随机生成二叉树
     *
     * @param maxNum
     * @param operationNum
     * @return
     */
    public BinaryTree generateBinaryTree(int maxNum, int operationNum) throws Exception {
        BinaryTree binaryTree = new BinaryTree();
        if (operationNum == 0) {
            //随机生成一个数
            //判断是否生成分数，这里有1/3的概率生成分数 2/3的概率生成整数
            boolean isfenshu = (int) (Math.random() * 3) == 0 ? true : false;
            if (isfenshu) {
                binaryTree.fenshu = Fenshu.newfenshu(maxNum);
            } else {
                binaryTree.fenshu = Fenshu.intTofenshu((int) (Math.random() * maxNum));
            }
        } else {
            //随机生成0-3个运算符，并随机分配给左右子树
            binaryTree.opetation = Operators[(int) (Math.random() * 4)];
            int leaveOperationNum = operationNum - 1;
            int OperationNumToLeft = (int) (Math.random() * (leaveOperationNum + 1));
            binaryTree.leftChild = generateBinaryTree(maxNum, OperationNumToLeft);
            binaryTree.rightChild = generateBinaryTree(maxNum, leaveOperationNum - OperationNumToLeft);
        }
        return binaryTree;
    }

    /**
     * 计算二叉树的结果
     *
     * @param binaryTree
     * @return
     * @throws Exception
     */
    public Fenshu getResult(BinaryTree binaryTree) throws Exception {
        if (binaryTree.leftChild == null && binaryTree.rightChild == null) {
            return binaryTree.fenshu;
        } else {
            String operation = binaryTree.opetation;
            Fenshu leftChildFenshu = getResult(binaryTree.leftChild);
            Fenshu rightChildFenshu = getResult(binaryTree.rightChild);
            //如果除数为0 则将除号改为乘号
            if (operation == "÷" && getResult(binaryTree.rightChild).toString().equals("0")) {
                binaryTree.opetation = "×";
                operation = "×";
            }
            binaryTree.fenshu = operation(operation, leftChildFenshu, rightChildFenshu);
            //若结果为负数，左右子树交换，值取绝对值
            if (binaryTree.fenshu.getfenzi() < 0) {
                binaryTree.fenshu.setfenzi(Math.abs(binaryTree.fenshu.getfenzi()));
                BinaryTree node = binaryTree.leftChild;
                binaryTree.leftChild = binaryTree.rightChild;
                binaryTree.rightChild = node;
            }
            return binaryTree.fenshu;
        }
    }

    public Fenshu operation(String Operator, Fenshu leftChildFenshu, Fenshu rightChildFenshu) throws Exception {
        switch (Operator) {
            case "+":
                return Fenshu.add(leftChildFenshu, rightChildFenshu);
            case "-":
                return Fenshu.subtraction(leftChildFenshu, rightChildFenshu);
            case "×":
                return Fenshu.multiplication(leftChildFenshu, rightChildFenshu);
            case "÷":
                return Fenshu.division(leftChildFenshu, rightChildFenshu);
            default:
                System.out.println("error");
                return null;
        }
    }

    private boolean isOperator(String element) {
        for (int i = 0; i < 4; i++) {
            if (element.equals(Operators[i])) {
                return true;
            }
        }
        return false;
    }



    /**
     * 判断两个表达式是否是同一个
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        Expression expression = (Expression) obj;
        //先判断表达式的结果是否相同
        if (expression.getResult().toString().equals(this.result.toString())) {
            //再判断二叉树是否相同
            if (expression.gettree().equals(this.tree)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return tree.hashCode() + result.hashCode();
    }


    public Fenshu getResult() {
        return result;
    }

    public void setResult(Fenshu result) {
        this.result = result;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public BinaryTree gettree() {
        return tree;
    }

    public void setRoot(BinaryTree tree) {
        this.tree = tree;
    }

    public int getLengthOfExp() {
        return LengthOfExp;
    }

    public void setLengthOfExp(int lengthOfExp) {
        this.LengthOfExp = lengthOfExp;
    }
}
