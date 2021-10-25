package calculator;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        long l1 = System.currentTimeMillis();

        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-e")) {
                check(args);
                long l2 = System.currentTimeMillis();
                System.out.println(l2 - l1 + "ms");
                return;
            } else if (args[i].contains("-r")) {
                generate(args);
                long l2 = System.currentTimeMillis();
                System.out.println(l2 - l1 + "ms");
                return;
            }
        }
        System.out.println("请输入正确的参数格式\n如\t-r 10 -n 10\n 使用 -n 参数控制生成题目的个数  \n " +
                "使用 -r 参数控制题目中数值（自然数、真分数和真分数分母）的范围");
        System.out.println("或\t-e <exercisefile>.txt -a <answerfile>.txt");

    }

    private static void check(String[] args) throws Exception {
        List<Integer> errorList = new ArrayList<Integer>();
        List<Integer> correctList = new ArrayList<Integer>();
        int errorNum = 0;
        int correctNum = 0;
        String exerciseFileName = null;
        String answerFileName = null;

        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-e")) {
                    exerciseFileName = args[i + 1];
                }
                if (args[i].equals("-a")) {
                    answerFileName = args[i + 1];
                }
            }
            if (exerciseFileName == null || answerFileName == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("输入的参数异常，请重新输入\n-e <exercisefile>.txt -a <answerfile>.txt");
            return;
        }
        File exercisesFile = new File(exerciseFileName);
        File answersFile = new File(answerFileName);
        BufferedReader exercisesBW = new BufferedReader(new FileReader(exercisesFile));
        BufferedReader answersBW = new BufferedReader(new FileReader(answersFile));
        String expressionLine = null;
        String realanswer = null;
        while ((expressionLine = exercisesBW.readLine()) != null) {
            int indexofdenghao = expressionLine.indexOf("=");
            int indexofdian = expressionLine.indexOf(".");
            String myanswer = expressionLine.substring(indexofdenghao + 1, expressionLine.length());
            myanswer = myanswer.trim();
            realanswer = answersBW.readLine();
            realanswer = realanswer.substring(indexofdian + 1, realanswer.length());
            realanswer = realanswer.trim();

            if (realanswer.equals(myanswer)) {
                correctList.add(Integer.parseInt(expressionLine.substring(0, indexofdian)));
                correctNum++;
            } else {
                errorList.add(Integer.parseInt(expressionLine.substring(0, indexofdian)));
                errorNum++;
            }
        }
        exercisesBW.close();
        answersBW.close();
        File gradeFile = new File("Grade.txt");
        BufferedWriter gradeBW = new BufferedWriter(new FileWriter(gradeFile));
        gradeBW.write("Correct:" + correctNum + "\n" + correctList.toString() + "\n");
        gradeBW.write("Wrong:" + errorNum + "\n" + errorList.toString() + "\n");
        gradeBW.close();
        System.out.println("校对完毕，结果已存入 Grade.txt");
    }

    public static void generate(String[] args) throws Exception {
        int maxNum = 0;
        int expressionNum = 0;
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-n")) {
                    expressionNum = Integer.parseInt(args[i + 1]);
                }
                if (args[i].equals("-r")) {
                    maxNum = Integer.parseInt(args[i + 1]);
                }
            }
            if (expressionNum == 0 || maxNum == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("输入参数异常，请重新输入！");
            return;
        }

        Set<Expression> expressions = new HashSet<Expression>();
        File exercisesFile = new File("exercises.txt");
        File answersFile = new File("answers.txt");
        BufferedWriter exercisesBW = new BufferedWriter(new FileWriter(exercisesFile));
        BufferedWriter answersBW = new BufferedWriter(new FileWriter(answersFile));
        for (int i = 1; i <= expressionNum; ) {
            try {
                String answer = "";
                Expression expression = new Expression(maxNum);
                //如果表达式不重复，则写入输出文件
                if (expressions.add(expression)) {
                    answer = expression.getResult().toString();
                    exercisesBW.write(i + ". " + expression.getExpression() + "\n");
                    answersBW.write(i + ". " + answer + "\n");
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        exercisesBW.flush();
        exercisesBW.close();
        answersBW.flush();
        answersBW.close();
        System.out.println("题目生成成功，已存入 exercises.txt\n\t答案存放在answers.txt中");
    }
}
