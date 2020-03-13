package com.example.currencycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    String[] currency = {"UAH","USD","EUR"};
    double[] rate = {26, 30};

    Spinner spinFrom = null;
    Spinner spinTo = null;

    double kurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinFrom = (Spinner)findViewById(R.id.CLFrom);
        spinTo = (Spinner)findViewById(R.id.CLTo);

        CurrencyList(currency, spinFrom);
        CurrencyList(currency, spinTo);

        kurs = GetRate(rate);
    }

    public void CurrencyList(String[] data, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        Spinner spinner = (Spinner) findViewById(R.id.CLFrom);
        spinner.setAdapter(adapter);

//        spinner.setPrompt("title");
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public double GetRate(final double[] data){
        spinTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                kurs = 1;
                switch (spinFrom.getSelectedItem().toString()){
                    case "UAH":
                        if(spinFrom.getSelectedItem().toString() != spinTo.getSelectedItem().toString()){
                            if(spinTo.getSelectedItem().toString() == "USD"){
                                kurs /= data[0];
                            }else {
                                kurs /= data[1];
                            }
                        }
                        break;
                    case "USD":
                        if(spinFrom.getSelectedItem().toString() != spinTo.getSelectedItem().toString()){
                            if(spinTo.getSelectedItem().toString() == "UAH"){
                                kurs *= data[0];
                            }else {
                                kurs *= data[0];
                                kurs /= data[1];
                            }
                        }
                        break;
                    case "EUR":
                        if(spinFrom.getSelectedItem().toString() != spinTo.getSelectedItem().toString()){
                            if(spinTo.getSelectedItem().toString() == "UAH"){
                                kurs *= data[1];
                            }else {
                                kurs *= data[1];
                                kurs /= data[0];
                            }
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + spinTo.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        return kurs;
    }

    public void onbtnCalc(View v) {
        TextView res = findViewById(R.id.result);
        EditText money = findViewById(R.id.inputMoney);
        res.setText(Double.toString(new BigDecimal(Double.parseDouble(money.getText().toString()) * kurs).setScale(4, RoundingMode.UP).doubleValue()));
    }
}
