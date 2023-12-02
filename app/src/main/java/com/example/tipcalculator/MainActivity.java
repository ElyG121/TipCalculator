package com.example.tipcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tipcalculator.databinding.ActivityMainBinding;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTip();
            }
        });
    }

    private void calculateTip() {
        String stringInTextField = binding.costOfService.getText().toString();
        Double cost = parseDoubleOrNull(stringInTextField);
        if (cost == null) {
            binding.tipResult.setText("");
            return;
        }

        double tipPercentage = getTipPercentage(binding.tipOptions.getCheckedRadioButtonId());
        double tip = tipPercentage * cost;
        if (binding.roundUpSwitch.isChecked()) {
            tip = Math.ceil(tip);
        }

        String formattedTip = NumberFormat.getCurrencyInstance().format(tip);
        binding.tipResult.setText(getString(R.string.tip_amount, formattedTip));
    }

    private Double parseDoubleOrNull(@NonNull String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private double getTipPercentage(@IdRes int checkedRadioButtonId) {
        if (checkedRadioButtonId == R.id.option_twenty_percent) {
            return 0.20;
        } else if (checkedRadioButtonId == R.id.option_eighteen_percent) {
            return 0.18;
        } else {
            return 0.15;
        }
    }

}
