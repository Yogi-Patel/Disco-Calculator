import java.awt.*;
import java.awt.event.*;
import java.util.*;
class Calculator extends Frame implements ActionListener
{
    boolean flag = true; 
    TextField tf,tf2;
    
    Button b[];
    String buttons[] = {"C","/","*","-","7","8","9","+","4","5","6",".","1","2","3","=","<-","0"};
    //                   0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15   16   17
    String operand1, operand2, operator, answer, tf2Values;
    Calculator()
    {
        super("Disco Calculator");
        setSize(400,400);
        setVisible(true);
        setLayout(new GridBagLayout());

        Random rand = new Random();

        operand1 = operand2 = operator = answer  = "0"; 
        tf2Values = "Eqn: ";
        b = new Button[18];
        tf = new TextField(10);
        tf2 = new TextField(10);
        tf.setFont(new Font("Serif", Font.PLAIN ,75));
        tf.setText("0");
        tf.setForeground(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
        tf2.setFont(new Font("Serif", Font.PLAIN ,20));
        tf2.setForeground(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        Panel pButtons = new Panel();        
        pButtons.setLayout(new GridBagLayout());

        Panel pTextField = new Panel();
        pTextField.setLayout(new GridBagLayout());

        Panel containerPanel = new Panel();
        containerPanel.setLayout(new GridBagLayout());

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        pTextField.add(tf2, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        pTextField.add(tf , gbc);

        for(int i = 0; i < 18; i++)
        {
            gbc.gridx = i%4;
            gbc.gridy = i/4 +1;
            b[i] = new Button(buttons[i]);
            b[i].setBackground(new Color(rand.nextInt(150)+105,rand.nextInt(150)+105,rand.nextInt(150)+105));
            b[i].addActionListener(this);
            pButtons.add(b[i],gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        containerPanel.add(pTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        containerPanel.add(pButtons, gbc);
        add(containerPanel, gbc);
        this.addWindowListener( new WindowAdapter()
            { 
                public void windowClosing(WindowEvent we)
                {
                    System.exit(0);
                }
            }
        );
    }

    public void actionPerformed (ActionEvent ae)
    {

        for( int i = 0; i<18 ; i++ )
        {

            if(ae.getSource() == b[0])
            {
                operand1 = operand2 = operator = answer = "0"; 
                flag = true; tf2Values = "Eqn: ";
                tf.setText(operand1);
                tf2.setText(tf2Values);
                break;
            }
            if( b[i] == ae.getSource() && i == 15 ) // equals sign 
            {
                answer = calculate();
                tf.setText(answer);
                tf2.setText(tf2Values + operand1 +" "+ operator +" "+ operand2 +" = "+ answer);  
                operand1 = answer; 
                operand2 = operator = answer = "0";
                flag = false;
                break;
            }
            else if( (b[i] == ae.getSource() && i >= 1 && i <= 3 ) || (b[i] == ae.getSource() && i == 7) ) // operators 
            {
                operator = buttons[i];
                tf2.setText(tf2Values + operand1 +" "+ operator);
                flag = false;
                tf.setText("0");
                break;
            }
            else if( ae.getSource() == b[i] && i != 16) // operands 
            {
                if(flag)
                {
                    operand1 += b[i].getLabel();
                    if(operand1.charAt(0) == '0' && operand1.charAt(1) != '.') 
                    {
                        operand1 = operand1.substring(1,operand1.length());
                    }
                    tf.setText(operand1);
                }
                else
                {
                    operand2 += b[i].getLabel();
                    if(operand2.charAt(0) == '0' && operand2.charAt(1) != '.') 
                    {
                        operand2 = operand2.substring(1,operand2.length());
                    }
                    tf.setText(operand2);
                    tf2.setText(tf2Values + operand1 + " "+ operator +" "+ operand2 +" = "+ calculate()); 
                }
                break;
            }
            else if(ae.getSource() == b[16]) //Eat 
            {
                if((tf.getText()).length() == 0) {break;}
                else
                {
                    if(flag)
                    {
                        operand1 = operand1.substring(0,operand1.length()-1);
                        tf.setText(operand1);
                    }
                    else
                    {
                        operand2 = operand2.substring(0,operand2.length()-1);
                        if(operand2.length() == 0) { operand2 = "0"; }
                        tf.setText(operand2);
                        tf2.setText(tf2Values + operand1 + " "+ operator +" "+ operand2 +" = "+ calculate()); 
                    }
                    break;
                }
            }
        }
    }

    public String calculate()
    {
        double a = Double.valueOf(operand1);
        double b = Double.valueOf(operand2);
        char ch = operator.charAt(0);
        double result = 0; 
        int resultInt;
        switch(ch)
        {
            case '/' : result = a/b;
                       break;
            case '*' : result = a*b;
                       break;
            case '-' : result = a-b;
                       break;
            case '+' : result = a+b;
                       break;
            case '0' : tf.setText("Math Error");
                       tf2.setText("Press 'C'");
                       break;
        }
        resultInt = (int)result;
        if((result - resultInt) != 0)
        {
            return Double.toString(result);
        }
        else 
        {
            return Integer.toString(resultInt);
        }

    }
}


public class DiscoCalculator 
{
	public static void main(String args[])
	{
		Calculator obj = new Calculator();
	}
}