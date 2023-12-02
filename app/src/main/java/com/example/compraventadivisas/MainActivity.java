package com.example.compraventadivisas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Spinner spB, spC;
    private Button btnCalc;
    private String divisaC, divisaB;
    private TextView tvResDivi, tvTCompra, tvTVenta;
    private HashMap<String, Double> mapita;
    private EditText editTextCalcular;
    private Double compra, venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.declarator();
        this.addToSpinners();
        this.addOptionsToMapita();
        this.loadPrices();
        this.activateButton();
    }

    private void declarator() {
        spB = findViewById(R.id.spinnerDBase);
        spC = findViewById(R.id.spinnerDCotizada);
        btnCalc = findViewById(R.id.buttonCalc);
        tvResDivi = findViewById(R.id.tvResDivi);
        mapita = new HashMap<>();
        editTextCalcular = findViewById(R.id.editTextCalcular);
        tvTCompra = findViewById(R.id.tvTCompra);
        tvTVenta = findViewById(R.id.tvTVenta);
    }
    private void addToSpinners() {
        String[] sta = getResources().getStringArray(R.array.divisas);
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sta);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spB.setAdapter(arrayAdapter);
        this.spC.setAdapter(arrayAdapter);
    }
    private void addOptionsToMapita() {
        this.mapita.put("Euro-Dolar", 0.92);
        this.mapita.put("Euro-GBP", 0.87);
        this.mapita.put("Dolar-Euro", 1.0);
        this.mapita.put("Dolar-GBP", 0.8);
        this.mapita.put("GBP-Dolar", 1.25);
        this.mapita.put("GBP-Euro", 1.15);
    }
    private void loadPrices() {
        this.spB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divisaB = spB.getSelectedItem().toString();
                if (divisaC == null)
                    tvResDivi.setText("No se puede aplicar nada. Divisa cotizada no seleccionada");
                else {
                    if (divisaB.equals(divisaC))
                        tvResDivi.setText("Las divisas no pueden ser iguales");
                    else {
                        compra = mapita.get(divisaB + "-" + divisaC);
                        venta = mapita.get(divisaC + "-" + divisaB);
                        tvResDivi.setText("Cotización Compra: " + compra + " - Venta: " + venta);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        this.spC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                divisaC = spC.getSelectedItem().toString();
                if (divisaB == null)
                    tvResDivi.setText("No se puede aplicar nada. Divisa base no seleccionada");
                else {
                    if (divisaC.equals(divisaB))
                        tvResDivi.setText("Las divisas no pueden ser iguales");
                    else {
                        Double compra = mapita.get(divisaB + "-" + divisaC);
                        Double venta = mapita.get(divisaC + "-" + divisaB);
                        tvResDivi.setText("Cotización Compra: " + compra + " - Venta: " + venta);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }
    private void activateButton() {
        this.btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(editTextCalcular.getText().toString() == null))
                {
                    Double divisas = Double.parseDouble(editTextCalcular.getText().toString());
                    if (!(compra == null ||venta == null)) {
                        tvTCompra.setText("Total compra: " + (divisas * compra));
                        tvTVenta.setText("Total venta: " + (divisas * venta));
                    }
                }
            }
        });
    }
}