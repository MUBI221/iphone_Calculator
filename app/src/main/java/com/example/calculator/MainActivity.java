package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTV, solutionTV;
    MaterialButton buttonC, buttonMultiply, buttonDivide, buttonPlus, buttonMinus,
            buttonEqual, buttonDot, buttonAC;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton button_closeBracket, button_openBracket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTV = findViewById(R.id.result_tv);
        solutionTV = findViewById(R.id.solution_tv);
        button_openBracket = assignID(R.id.button_openBracket);
        button_closeBracket = assignID(R.id.button_closeBracket);
        buttonMultiply = assignID(R.id.multiply);
        buttonDivide = assignID(R.id.buttonDivide);
        buttonPlus = assignID(R.id.buttonPlus);
        buttonMinus = assignID(R.id.buttonMinus);
        buttonEqual = assignID(R.id.equal);
        button1 = assignID(R.id.button_1);
        button0 = assignID(R.id.button_0);
        button2 = assignID(R.id.button_2);
        button3 = assignID(R.id.button_3);
        button4 = assignID(R.id.button_4);
        button5 = assignID(R.id.button_5);
        button6 = assignID(R.id.button_6);
        button7 = assignID(R.id.button_7);
        button8 = assignID(R.id.button_8);
        button9 = assignID(R.id.button_9);
        buttonC = assignID(R.id.button_C);
        buttonAC = assignID(R.id.button_ac);
        buttonDot = assignID(R.id.button_dot);

        // Assign other button IDs similarly

    }

    MaterialButton assignID(int id) {
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
        return btn;
    }

    @Override
    public void onClick(View v) {

        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTV.getText().toString();

        if (buttonText.equals("AC")) {

            solutionTV.setText("");
            resultTV.setText("");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTV.setText(resultTV.getText());
            return;
        }
        if (buttonText.equals("C")) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }
        solutionTV.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Error")) {
            resultTV.setText(finalResult);
        }

    }

    String getResult(String data) {

        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scope = context.initStandardObjects();
            String finalResult = context.evaluateString(scope, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";
        } finally {
            Context.exit();
        }
    }
}
