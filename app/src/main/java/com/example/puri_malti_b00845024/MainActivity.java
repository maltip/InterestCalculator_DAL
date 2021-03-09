package com.example.puri_malti_b00845024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mInitialInvestment;
    private EditText mRegularPayment;
    private Spinner mFrequency;
    private EditText mInterestRate;
    private SeekBar mGrowthRateSeekbar;
    private TextView mGrowthRate;
    private Button mCalculate;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInitialInvestment = (EditText) findViewById(R.id.initial_investment);
        mRegularPayment = (EditText) findViewById(R.id.regular_payment);
        mFrequency = (Spinner) findViewById(R.id.frequency);
        mInterestRate = (EditText) findViewById(R.id.interst_rate);
        mGrowthRate = (TextView) findViewById(R.id.growth_rate);
        mGrowthRateSeekbar = (SeekBar) findViewById(R.id.years_seekbar);
        mCalculate = (Button) findViewById(R.id.calculate);
        mResult = (TextView) findViewById(R.id.result);

        mGrowthRateSeekbar.setMax(10);
        mGrowthRateSeekbar.setProgress(1);
        mGrowthRate.setText("1 ");

        mGrowthRateSeekbar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue = 1;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        mGrowthRate.setText("" + progressValue + " ");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mGrowthRate.setText("" + progressValue + " ");
                    }
                }
        );

        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    double initialInvestment = Double.parseDouble(mInitialInvestment.getText().toString());
                    double regularPayment = Double.parseDouble(mRegularPayment.getText().toString());
                    String frequency = mFrequency.getSelectedItem().toString();
                    int period = mGrowthRateSeekbar.getProgress();
                    double interestRate = Double.parseDouble(mInterestRate.getText().toString());

                    int depositeFrequency = 1;
                    switch (frequency) {
                        case "Monthly":
                            depositeFrequency = 12;
                            break;
                        case "Quarterly":
                            depositeFrequency = 4;
                            break;
                        case "Semi-annually":
                            depositeFrequency = 2;
                            break;
                        case "Annually":
                            depositeFrequency = 1;
                            break;
                    }

                    int compoundingFrequency = 1;
                    double r = (interestRate / 100.0) * (1 / compoundingFrequency);
                    double p = period * compoundingFrequency;
                    double amount = initialInvestment * Math.pow(1+r, p) + (regularPayment * depositeFrequency) * ((Math.pow(1+r, p) - 1)/r);

                    mResult.setVisibility(View.VISIBLE);

                    //Format the value to 2 decimal places
                    String finalAmount = "Compound interest: $" + String.format("%.2f", amount);
                    mResult.setText(finalAmount);
                }
            }
        });
    }

    // Check if the value is empty or invalid
    public Boolean isValid() {
        Boolean isOK = true;
        if (mInitialInvestment.getText().toString().trim().length() == 0) {
            mInitialInvestment.setError("Intial Investment Cannot be empty");
            isOK = false;
        } else if (!mInitialInvestment.getText().toString().matches("-?\\d+(\\.\\d+)?")) {
            mInitialInvestment.setError("Intial Investment should only be numeric value");
            isOK = false;
        }

        if (mRegularPayment.getText().toString().trim().length() == 0) {
            mRegularPayment.setError("Regular Payment Cannot be empty");
            isOK = false;
        } else if (!mRegularPayment.getText().toString().matches("-?\\d+(\\.\\d+)?")) {
            mRegularPayment.setError("Regular Payment should only be numeric value");
            isOK = false;
        }

        if (mInterestRate.getText().toString().trim().length() == 0) {
            mInterestRate.setError("Rate Cannot be empty");
            isOK = false;
        } else if (!mInterestRate.getText().toString().matches("-?\\d+(\\.\\d+)?")) {
            mInterestRate.setError("Rate  should only be numeric value");
            isOK = false;
        }

        return isOK;
    }
}
