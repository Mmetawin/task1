
import java.util.Scanner;
class stack
{
    public int size;
    public double[] array;
    public int top=-1;
    public stack(int size)     //��ʼ��ջ�ĳ���
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
            //ջ����
            double[] array1=new double[(size+size/2)];
            System.arraycopy(array1,0,array,0,size);  //���ݸ���
            size=size+size/2;
            array=array1;
        }
        top++;
        array[top]=data;
    }
    public double pop(){
        if(isempty()){
            throw new RuntimeException("ջ��");   //�׳���ջ����
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
        return array[top];   //����ջ������
    }
    public boolean isoper(char c){
        return c=='+'||c=='-'||c=='*'||c=='/'||c=='(';
    }
}
class Calculation {
    public double calculate(double num1, char c, double num2) {
        if((num1+"").length()>=9||(num2+"").length()>=9) {   //��������쳣����
            System.out.println("��������쳣������");
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
                try {   //��ʽ�쳣��׽
                    if(num1==0){
                        System.out.println(1/0);
                    }
                    data = 1.0 * num2 / num1;
                }catch(ArithmeticException ae){
                    System.out.println("��ʽ������󣨳�0���󣩣�����ȷ���룡����");
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
                //�жϵ�ǰ�ķ���ջ�Ƿ�Ϊ��
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
                keepnum += ch;    //�Զ�λ������
                //���ch�����һλֱ����ջ
                if (index == str.length()-1){
                    numstack.push(Double.parseDouble(keepnum));
                } else if (operstack.isoper(str.charAt(index+1))||str.charAt(index+1)==')'){
                    //�����һλ�����������ջ
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
        try {    //��ʽ�쳣��׽
            char[] str1 = str.toCharArray();
            int len = str.length();
            for(int i=0;i<len;i++){
                if(str1[i]==' '||str1[i]=='��'||str1[i]=='��'){
                    System.out.println(Integer.parseInt(str));
                    break;
                }
            }
        }catch(NumberFormatException ne){
            System.out.println("��ʽ��ʽ�쳣�������пո��������ŵȣ�������");
            System.exit(1);
        }
        Calculation m = new Calculation();
        double result = m.compute(str);
        System.out.println(String.format("%.2f",result));
    }
}

