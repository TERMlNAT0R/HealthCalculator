package com.example.practic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class liveindex extends AppCompatActivity {
    private EditText l_Capacity, mass;
    TextView result;
    String resStr;
    Button btn, next;
    SharedPreferences sp;
    public boolean isDouble = true;
    public boolean isStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveindex);
        l_Capacity = findViewById(R.id.editTextNumberDecimal0);
        mass = findViewById(R.id.editTextNumberDecimal2);
        result = findViewById(R.id.result_txt);
        btn = findViewById(R.id.calculate_btn);
        next = findViewById(R.id.buttonNEXT);
        next.setOnClickListener(view -> {
            Intent intent2 = new Intent(liveindex.this, Skibi.class);
            startActivity(intent2);
            if (isStart == true) {
                finish();
            }
        });
        sp = getSharedPreferences("TestResult", Context.MODE_PRIVATE);
        btn.setOnClickListener(view -> {
            if (l_Capacity.getText().length() == 0 || mass.getText().length() == 0) {
                Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                isDouble = true;
                Double.parseDouble(l_Capacity.getText().toString());
                Double.parseDouble(mass.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Что-то пошло не так, проверьте данные", Toast.LENGTH_SHORT).show();
                result.setText("Ошибка");
                isDouble = false;
            }
            if (isDouble) {
                double lC = Double.parseDouble(l_Capacity.getText().toString());
                double kilo = Double.parseDouble(mass.getText().toString());
                double li = (lC / kilo);
                if (li > 0) {
                    resStr = String.format("%.2f", li);
                    if (li <= 49) {
                        result.setText(String.format("%s низкий показатель", resStr));
                    } else if (li >= 50 && li <= 61) {
                        result.setText(String.format("%s норма", resStr));
                    } else if (li >= 62) {
                        result.setText(String.format("%s высокий показатель", resStr));
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("LiveInVal", resStr);
                    editor.apply();
                } else {
                    Toast.makeText(getApplicationContext(), "Значение не может быть отрицательным", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}