
import java.util.Scanner;
class stack
{
    public int size;
    public double[] array;
    public int top=-1;
    public stack(int size)     //初始化栈的长度
    {
        this.size=size;
        array=new double[size];
    }
    public boolean isfull() {
        if(top==size-1)
            return true;
        return false;
    }
    public boolean isempty(){
        if(top==-1)
            return true;
        return false;
    }
    public void push(double data){
        if(isfull()){
            //栈扩容
            double[] array1=new double[(size+size/2)];
            System.arraycopy(array1,0,array,0,size);  //数据复制
            size=size+size/2;
            array=array1;
        }
        top++;
        array[top]=data;
    }
    public double pop(){
        if(isempty()){
            throw new RuntimeException("栈空");   //抛出空栈错误
        }
        return array[top--];
    }
    public int priority(char c){
        if(c=='*'||c=='/')
            return 1;
        else if(c=='+'||c=='-')
            return 0;
        else
            return -1;
    }
    public double peek(){
        return array[top];   //返回栈顶数据
    }
    public boolean isoper(char c){
        return c=='+'||c=='-'||c=='*'||c=='/'||c=='(';
    }
}
class Calculation {
    public double calculate(double num1, char c, double num2) {
        if((num1+"").length()>=9||(num2+"").length()>=9) {   //数据溢出异常处理
            System.out.println("数据溢出异常！！！");
            System.exit(1);
        }
        double data = 0;
        switch (c) {
            case '+':
                data = num1 + num2;
                break;
            case '-':
                data = num2 - num1;
                break;
            case '*':
                data = 1.0 * num1* num2;
                break;
            case '/':
                try {   //算式异常捕捉
                    if(num1==0){
                        System.out.println(1/0);
                    }
                    data = 1.0 * num2 / num1;
                }catch(ArithmeticException ae){
                    System.out.println("算式运算错误（除0错误），请正确输入！！！");
                    System.exit(1);
                }
                break;
            default:
                break;
        }
        return data;
    }
    public double compute(String str){
        stack numstack = new stack(20);
        stack operstack = new stack(20);
        int index = 0;
        double num1, num2, data;
        char oper, ch;
        String keepnum = "";
        while (true){
            ch = str.charAt(index);
            if (ch==')'){
                while (true){
                    if (operstack.peek()=='('){
                        operstack.pop();
                        break;
                    }
                    num1 = numstack.pop();
                    num2 = numstack.pop();
                    oper =(char) operstack.pop();
                    data= calculate(num1,oper,num2);
                    numstack.push(data);
                }
            }
            if (operstack.isoper(ch)) {
                //判断当前的符号栈是否为空
                if (!operstack.isempty()){
                    if (operstack.priority(ch)<=operstack.priority((char) operstack.peek())&&ch!='('){
                        num1 = numstack.pop();
                        num2 = numstack.pop();
                        oper =(char) operstack.pop();
                        data= calculate(num1,oper,num2);
                        numstack.push(data);
                        operstack.push(ch);
                    }else {
                        operstack.push(ch);
                    }
                }else {
                    operstack.push(ch);
                }
            }else if (ch!=')'){
                keepnum += ch;    //对多位数处理
                //如果ch是最后一位直接入栈
                if (index == str.length()-1){
                    numstack.push(Double.parseDouble(keepnum));
                } else if (operstack.isoper(str.charAt(index+1))||str.charAt(index+1)==')'){
                    //如果后一位是运算符则入栈
                    numstack.push(Double.parseDouble(keepnum));
                    keepnum="";
                }
            }
            index++;
            if (index>=str.length())
                break;
        }
        while (true){
            if (operstack.isempty()){
                break;
            }
            num1 = numstack.pop();
            num2 = numstack.pop();
            oper =(char) operstack.pop();
            data= calculate(num1,oper,num2);
            numstack.push(data);
        }
        return numstack.pop();
    }
}
public class secondweek1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        try {    //格式异常捕捉
            char[] str1 = str.toCharArray();
            int len = str.length();
            for(int i=0;i<len;i++){
                if(str1[i]==' '||str1[i]=='（'||str1[i]=='）'){
                    System.out.println(Integer.parseInt(str));
                    break;
                }
            }
        }catch(NumberFormatException ne){
            System.out.println("算式格式异常（不能有空格、中文括号等）！！！");
            System.exit(1);
        }
        Calculation m = new Calculation();
        double result = m.compute(str);
        System.out.println(String.format("%.2f",result));
    }
}

