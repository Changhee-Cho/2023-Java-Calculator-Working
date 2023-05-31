import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MyFrame extends JFrame implements ActionListener {
    JTextField tf;
    double num1 = 0.0;
    double num2 = 0.0;
    char operator = ' ';
    boolean newCalculation = true;

    public MyFrame() {
        final int COLS = 5;
        final int ROWS = 5;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();

        String[] name = {
                "Backspace", "x²", "π", "CE", "C",
                "7", "8", "9", "/", "sqrt",
                "4", "5", "6", "x", "%",
                "1", "2", "3", "-", "1/x",
                "0", "+/-", ".", "+", "="
        };

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(ROWS, COLS, 3, 3));
        tf = new JTextField(35);
        tf.setHorizontalAlignment(JTextField.RIGHT);
        tf.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        tf.setText("0");
        tf.setEditable(false);
        JButton[] btn = new JButton[name.length];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                btn[index] = new JButton(name[index]);
                btn[index].setBackground(Color.WHITE);
                if (index == 0)
                    btn[index].setForeground(Color.GRAY);
                if (j >= 3 || index == 1 || index == 2)
                    btn[index].setForeground(Color.RED);
                if (index == name.length - 1) {
                    btn[index].setBackground(Color.BLUE);
                    btn[index].setForeground(Color.WHITE);
                }
                btn[index].addActionListener(this);
                panel.add(btn[index]);
                index++;
            }
        }
        add(tf, BorderLayout.PAGE_START);
        add(panel, BorderLayout.CENTER);
        pack();
        setTitle("Changhee's Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocation(screen.width / 2 - 250, screen.height / 2 - 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String clicked = e.getActionCommand();

        // 숫자 및 소수점 입력
        if (clicked.matches("[0-9]") || (clicked.equals(".") && !tf.getText().contains("."))) {
            if (newCalculation) {
                tf.setText(clicked);
                newCalculation = false;
            } else {
                tf.setText(tf.getText() + clicked);
            }
        }
        //계산하는 식이 음수인 경우
        else if(clicked.charAt(0)=='-' && newCalculation){
            tf.setText("-");
            newCalculation=false;
        }
        // Backspace
        else if (clicked.equals("Backspace")) {
            String text = tf.getText();
            if (text.length() > 1) {
                tf.setText(text.substring(0, text.length() - 1));
            } else {
                tf.setText("0");
            }
        }
        // 초기화
        else if (clicked.equals("CE")) {
            tf.setText("0");
        } else if (clicked.equals("C")) {
            tf.setText("0");
            num1 = 0.0;
            num2 = 0.0;
            operator = ' ';
            newCalculation = true;
        }
        // π
        else if (clicked.equals("π")) {
            tf.setText(String.valueOf(Math.PI));
        }
        // 연산자
        else if (clicked.matches("[+\\-x/%]")) {
            if (operator != ' ') {
                num2 = Double.parseDouble(tf.getText());
                double result = calculate();
                tf.setText(String.valueOf(result));
                num1 = result;
            }
            num1 = Double.parseDouble(tf.getText());
            operator = clicked.charAt(0);
            newCalculation = true;
        }
        // 제곱
        else if (clicked.equals("x²")) {
            num1 = Double.parseDouble(tf.getText());
            double result = num1 * num1;
            tf.setText(String.valueOf(result));
            newCalculation = true;
        }
        // 루트
        else if (clicked.equals("sqrt")) {
            num1 = Double.parseDouble(tf.getText());
            double result = Math.sqrt(num1);
            tf.setText(String.valueOf(result));
            newCalculation = true;
        }
        // 역수
        else if (clicked.equals("1/x")) {
            num1 = Double.parseDouble(tf.getText());
            if (num1 != 0) {
                double result = 1 / num1;
                tf.setText(String.valueOf(result));
            } else {
                tf.setText("0으로 나눌 수 없습니다");
            }
            newCalculation = true;
        }
        // +/- 부호 전환
        else if (clicked.equals("+/-")) {
            double value = Double.parseDouble(tf.getText());
            if(value!=0.0){
                value = -value;
                tf.setText(String.valueOf(value));
            }
        }
        // 계산 결과
        else if (clicked.equals("=")) {
            if(operator!=' '){
                num2 = Double.parseDouble(tf.getText());
                double result = calculate();
                String complete = "" + result;
                if (complete.charAt(complete.length() - 1) == '0' && complete.charAt(complete.length() - 2) == '.') {
                    tf.setText(complete.substring(0, complete.length() - 2));
                }
                else {
                    tf.setText(complete);
                }
                num1 = result;
                operator = ' ';
                newCalculation = true;
            }
        }
    }

    private double calculate() {
        double result = 0.0;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case 'x':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    tf.setText("0으로 나눌 수 없습니다");
                    newCalculation = true;
                    return 0.0;
                }
                break;
            case '%':
                result = num1 % num2;
                break;
        }
        return result;
    }
}

public class Cal {
    public static void main(String[] args) {
        MyFrame f = new MyFrame();
    }
}