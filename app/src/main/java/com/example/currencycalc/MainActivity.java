package com.example.currencycalc;

import androidx.annotation.NonNull;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String[] currency = {"UAH","USD","EUR"};
    double[] rate = {0, 0};

    Spinner spinFrom = null;
    Spinner spinTo = null;

    double kurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetRate();

        spinFrom = (Spinner)findViewById(R.id.CLFrom);
        spinTo = (Spinner)findViewById(R.id.CLTo);

        CurrencyList(currency, spinFrom);
        CurrencyList(currency, spinTo);

        kurs = GetRate(rate);
    }

    public void SetRate(){
        NetworkService.getInstance()
                .getExchApi()
                .getAll()
                .enqueue(new Callback<List<Excange>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Excange>> call, @NonNull Response<List<Excange>> response) {
                        List<Excange> list = response.body();

                        rate[0] = (list.get(0).getBuy() + list.get(0).getSale()) / 2;
                        rate[1] = (list.get(1).getBuy() + list.get(1).getSale()) / 2;

//                        res.append(Double.toString(new BigDecimal(rate[0]).setScale(2, RoundingMode.UP).doubleValue()) + "\n");
//                        res.append(Double.toString(new BigDecimal(rate[1]).setScale(2, RoundingMode.UP).doubleValue()));
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Excange>> call, @NonNull Throwable t) {
                        TextView res = findViewById(R.id.result);
                        res.append("error connect to bank");
                        t.printStackTrace();
                    }
                });
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
        res.setText(Double.toString(new BigDecimal(Double.parseDouble(money.getText().toString()) * kurs)
                .setScale(2, RoundingMode.UP).doubleValue()));
    }
}
