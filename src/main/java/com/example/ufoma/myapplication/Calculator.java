package com.example.ufoma.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import static java.util.regex.Pattern.quote;

public class Calculator extends AppCompatActivity implements OnClickListener {

    private TextView _screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnClickListener(this);
        _screen = (TextView) findViewById(R.id.textView);
        _screen.setText(display);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch1:
                Intent intent = new Intent(this, ActivityTwo.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void updateScreen() {
        _screen.setText(display);
    }

    public void onClickNumber(View v) {
        System.out.printf("Метод: onClickNumber%s%n", display);
        if (Objects.equals(_screen.getText(), "Ошибка")) {
            clear();
            AllClean();
        }
        if (!Objects.equals(result, "") & !result.contains(".")) {
            clear();
        }
        Button b = (Button) v;
        if (display.length() > 0 && v.getId() == R.id.btn0 & display.charAt(0) == '0') {
            return;
        }
        display += b.getText();
        updateScreen();
        sizeTextField();
        if (!Objects.equals(result, "")) {
            result = "";
        }
    }

    private boolean isOperator(char op) {
        System.out.printf("Метод: isOperator%s%n", currentOperator);
        switch (op) {
            case '+':
            case '-':
            case 'x':
            case '÷':
                return true;
            default:
                return false;
        }
    }

    public void onClickOperator(View v) {
        System.out.printf("Метод: onClickOperator%s%n", display);
        Button b = (Button) v;
        if (Objects.equals(String.valueOf((_screen.getText())), "Ошибка")
                | Objects.equals(String.valueOf((_screen.getText())), "")
                | Objects.equals(String.valueOf((_screen.getText())), currentOperator)) {
            clear();
            AllClean();
            return;
        }
        if (Objects.equals(display, "")) return;

        if (!Objects.equals(result, "") & !result.contains(".")) {
            String _display = result;
            clear();
            display = _display;
            System.out.printf("onClickOperator очитска%s%n 1", result);
        }

        if (!Objects.equals(currentOperator, "") & !result.contains(".")) {
            Log.d("CalcX", "" + display.charAt(display.length() - 1));
            if (isOperator(display.charAt(display.length() - 1))) {
                display = display.substring(0, display.length() - 1) + b.getText().charAt(0);
                sizeTextField();
                updateScreen();
                System.out.printf("onClickOperator очитска%s%n 2", result);
                result = "";
                return;
            } else {
                sizeTextField();
                getResult();
                display = result;
                result = "";
                System.out.printf("onClickOperator очитска%s%n 3", result);
            }
            currentOperator = b.getText().toString();
        }
        String temp = "";
        if (Objects.equals(result, "")) {
            display += b.getText();
            System.out.printf("onClickOperator %%s%%n 3.1%s%n", result);
        } else {
            temp = display;
            display = result + b.getText();
            System.out.printf("onClickOperator %%s%%n 3.2%s%n", result);
        }
        if (!Objects.equals(result, "") && result.charAt(result.length() - 1) == '.') {
            display = temp + b.getText();
        }
        currentOperator = b.getText().toString();
        updateScreen();
        sizeTextField();
        System.out.printf("onClickOperator очитска%%s%%n 4%s%n", currentOperator);
    }

    private void clear() {
        System.out.printf("Метод: clear%s%n", display);
        display = "";
        currentOperator = "";
        result = "";
    }

    public void onClickClear(View v) {
        System.out.printf("Метод: onClickClear%s%n", display);
        clear();
        updateScreen();
    }

    private double operate(String a, String b, String op) throws Exception {
        System.out.printf("Метод: operate%s%n", display);
        if (Objects.equals(String.valueOf((_screen.getText())), "Ошибка")) {
            clear();
            AllClean();
        }
        try {
            switch (op) {
                case "+":
                    return Double.valueOf(a) + Double.valueOf(b);
                case "-":
                    return Double.valueOf(a) - Double.valueOf(b);
                case "x":
                    return Double.valueOf(a) * Double.valueOf(b);
                case "÷":
                    try {
                        if (Double.valueOf(b) == 0) {
                            throw new Exception("Ошибка деление на ноль");
                        }
                        return Double.valueOf(a) / Double.valueOf(b);
                    } catch (Exception e) {
                        Log.d("Calc", e.getMessage());
                    }
                default:
                    return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    private boolean getResult() {
        System.out.printf("Метод: getResult%s%n", display);
        try {
            if (Objects.equals(String.valueOf((_screen.getText())), "Ошибка")) {
                clear();
                AllClean();
            }
            if (Objects.equals(currentOperator, "")) return false;
            String[] operation = display.split(quote(currentOperator));
            if (Objects.equals(operation[0], "")) {
                operation = display.replaceFirst("-", "").split(quote(currentOperator));
                operation[0] = "-" + operation[0];
            }
            if (operation.length < 2) return false;
            try {
                result = MyNumberFormat(operate(operation[0], operation[1], currentOperator)).replaceAll(",", ".");
            } catch (Exception e) {
                e.printStackTrace();
                _screen.setText("Ошибка");
                clear();
            }
            sizeTextField();
            System.out.printf("Метод:catch getResult%s%n", currentOperator);
            System.out.printf("Метод: getResult%s%n ", result);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("Метод:catch getResult%s%n", currentOperator);
            return false;

        }

    }

    public void onClickEqual(View v) {
        System.out.printf("Метод: onClickEqual%s%n", display);
        if (Objects.equals(String.valueOf((_screen.getText())), "Ошибка")) {
            clear();
            AllClean();
            return;
        }
        if (Objects.equals(display, "")) return;
        if (!getResult()) return;
        _screen.setText(display + "=" + "\n" + result);
        sizeTextField();

    }


    public void Sqrt(View v) {
        System.out.printf("Метод: Sqrt%s%n", v);

        try {
            if (Objects.equals(String.valueOf((_screen.getText())), "Ошибка")) {
                clear();
                AllClean();
                return;
            }
            Double root;
            if (Objects.equals(result, "") & Objects.equals(currentOperator, "")) {
                root = Double.valueOf(String.valueOf(_screen.getText()));
                if (root < 0) {
                    _screen.setText("Ошибка");
                    clear();
                    return;
                }
                display = MyNumberFormat(Math.sqrt(root));
            } else if (!Objects.equals(result, "")) {
                root = Double.valueOf(String.valueOf(result));
                if (root < 0) {
                    _screen.setText("Ошибка");
                    clear();
                    return;
                }
                display = MyNumberFormat(Math.sqrt(root));
                result = display;
                _screen.setText(display);
            } else if (!Objects.equals(currentOperator, "") & Objects.equals(result, "")) {
                String[] operation = display.split(quote(currentOperator));
                root = Double.valueOf(String.valueOf(operation[1]));
                if (root < 0) {
                    _screen.setText("Ошибка");
                    clear();
                    return;
                }
                display = operation[0] + currentOperator + MyNumberFormat(Math.sqrt(root));

            }
            sizeTextField();
            updateScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String MyNumberFormat(double numB) {
        System.out.printf("Метод: MyNumberFormat%s%n", display + result);
        String numA = NumberFormat.getInstance(Locale.ROOT).format(numB);
        sizeTextField();
        AllClean();
        return numA;
    }

    private void AllClean() {
        _screen.setText("");
    }

    private void sizeTextField() {
        if (display.length() > 14 & (display.length() < 20)) {
            _screen.setTextSize(30);

        } else if (display.length() < 14) {
            _screen.setTextSize(45);

        } else if (display.length() > 20) {
            _screen.setTextSize(20);
        }
    }

    public void backSpace(View v) {
        System.out.printf("Метод: backSpace%s%n", display);
        if (_screen.length() > 0 & Objects.equals(result, "")) {
            display = display.substring(0, display.length() - 1);
        } else if (!Objects.equals(result, "")) {
            display = (display + result).substring(display.length(), (display.length() + result.length()) - 1);
            result = display;
        }
        updateScreen();
    }

    public void plusMinus(View v) {
        System.out.printf("Метод: plusMinus%s%n", display + "" + result);
        double plusMinus;

        try {
            if (!Objects.equals(display, "") & Objects.equals(currentOperator, "")) {
                display = MyNumberFormat((-1) * Double.parseDouble(display));
                System.out.printf("Метод: plusMinus%s%n", currentOperator + "1");
            } else if (!Objects.equals(result, "") & Objects.equals(currentOperator, "")) {
                result = MyNumberFormat((-1) * Double.parseDouble(result));
                display = result;
                System.out.printf("Метод: plusMinus%s%n", currentOperator + "2");
            } else if (!Objects.equals(currentOperator, "") & Objects.equals(result, "")) {
                if (Objects.equals(String.valueOf(currentOperator), "-")) {
                    display = display.substring(0, display.lastIndexOf(currentOperator)) + "+" + display.substring(display.lastIndexOf(currentOperator), display.length());
                    currentOperator = "+";
                    System.out.printf("Метод: plusMinus%s%n", currentOperator + "3");
                } else if (Objects.equals(String.valueOf(currentOperator), "+")) {
                    System.out.printf("Метод: plusMinus%s%n", currentOperator + "4");
                    display = display.substring(0, display.lastIndexOf(currentOperator)) + "-" + display.substring(display.lastIndexOf(currentOperator) + 1, display.length());
                    currentOperator = "-";
                    updateScreen();
                    return;
                }
                System.out.printf("Метод: plusMinus%s%n", currentOperator + "5");
                String[] operation = display.split(quote(currentOperator));
                plusMinus = (Double.valueOf(String.valueOf(operation[1]))) * (-1);
                display = operation[0] + currentOperator + MyNumberFormat(plusMinus);
            } else {
                System.out.printf("Метод: plusMinus%s%n", currentOperator + "6");
                plusMinus = Double.valueOf(String.valueOf(result)) * (-1);
                display = MyNumberFormat(plusMinus);
                result = MyNumberFormat(plusMinus);
            }
            System.out.printf("Метод: plusMinus%s%n", result);
            updateScreen();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public void onClickPoint(View v) {
        System.out.printf("Метод: onClickPoint%s%n", display);
        try {
            if (Objects.equals(currentOperator, "") & !display.contains(".")) {
                System.out.println(display.contains(".") + "1");
                if (display.isEmpty()) {
                    display = display + "0.";
                } else {
                    display = display + ".";
                }
                updateScreen();
            } else if (!Objects.equals(currentOperator, "") & !result.contains(".")) {
                System.out.println(display.contains(".") + "2");
                String[] operation = display.split(quote(currentOperator));
                if (operation.length == 1) {
                    if (display.endsWith(currentOperator)) {
                        display = operation[0] + currentOperator + "0.";
                    } else {
                        display = operation[0] + currentOperator + ".";
                    }
                } else {
                    if (operation.length == 2) {
                        display = operation[0] + currentOperator + operation[1] + ".";
                    }
                }
            } else if (!Objects.equals(result, "") & !result.contains(".")) {
                System.out.println(display.contains(".") + "3");
                result = result + ".";
                display = result;

            }

            updateScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




