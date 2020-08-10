package com.brandon.lengthconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    Spinner spinner2;
    EditText edit;

    public double amount;
    public String fromUnit;
    public String toUnit;
    public double convertedValue;
    public BigDecimal amountOfMeters;
    public BigDecimal amountOfFeet;
    public boolean imperial1;
    public boolean imperial;
    public ArrayList<String> conversionList = new ArrayList<>();
    public String outputText = "";
    public int amountOfDecimals = 5;
    public MathContext mc = new MathContext(9);

    private AdView mAdView;

    public static ContextThemeWrapper getAlertDialogContext(Context context) {
        return new ContextThemeWrapper(context, R.style.AlertDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d("Ad Test", "Ad Finishes Loading");
            }

            @Override
            public void onAdFailedToLoad(int i ) {
                Log.d("Ad Test", "Ad Loading Failed");
            }

            @Override
            public void onAdOpened() {
                Log.d("Ad Test", "Ad is Visible Now");
            }

            @Override
            public void onAdLeftApplication() {
                Log.d("Ad Test", "User left the app");
            }

            @Override
            public void onAdClosed() {
                Log.d("Ad Test", "User return back to the app after tapping on ad");
            }

        });


        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);

        ArrayList<String> list = new ArrayList<>();
        list.add("Select Unit");
        list.add("Kilometer");
        list.add("Hectometer");
        list.add("Decameter");
        list.add("Meter");
        list.add("Decimeter");
        list.add("Centimeter");
        list.add("Millimeter");
        list.add("Inch");
        list.add("Foot");
        list.add("Yard");
        list.add("Mile");
        //extras
        list.add("Nautical League");
        list.add("Fathom");
        list.add("Nautical Mile");
        list.add("Chain");
        list.add("Rod");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_spinner_style, list);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);


        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final TextView textViewSb = (TextView)findViewById(R.id.textViewSb);
        textViewSb.setText(Integer.toString(seekBar.getProgress()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                amountOfDecimals = progress;
                textViewSb.setText(Integer.toString(progress));
            }
        });

    }

    public BigDecimal toMeters(){ //convert fromUnit to meters
        TextView textView = (TextView)spinner.getSelectedView();
        TextView textView2 =(TextView)spinner2.getSelectedView();

        try {
            fromUnit = textView.getText().toString(); //gets the name of the start unit (string value)
            toUnit = textView2.getText().toString(); //gets the name of the end unit (string value)
        } catch (NullPointerException e){
            System.out.println(e);
        }

        edit = (EditText)findViewById(R.id.editText);
        String result = edit.getText().toString();
        try {
            double num = Double.parseDouble(result);
            amount = (double)num;
        }catch(NumberFormatException e){
            System.out.println(e);
        }

        switch (fromUnit) {
            case "Kilometer":
                amountOfMeters = new BigDecimal(amount * 1000.0);
                imperial1 = true;
                break;
            case "Hectometer":
                amountOfMeters = new BigDecimal(amount * 100.0);
                imperial1 = true;
                break;
            case "Decameter":
                amountOfMeters = new BigDecimal(amount * 10.0);
                imperial1 = true;
                break;
            case "Meter":
                amountOfMeters = new BigDecimal(amount * 1.0);
                imperial1 = true;
                break;
            case "Decimeter":
                amountOfMeters = new BigDecimal(amount * 0.1);
                imperial1 = true;
                break;
            case "Centimeter":
                amountOfMeters = new BigDecimal(amount * 0.01);
                imperial1 = true;
                break;
            case "Millimeter":
                amountOfMeters = new BigDecimal(amount * 0.001);
                imperial1 = true;
                break;
            case "Inch":
                amountOfMeters = new BigDecimal(amount * 0.0254);
                amountOfFeet = new BigDecimal(amount / 12);
                imperial1 = false;
                break;
            case "Foot":
                amountOfMeters = new BigDecimal(amount * 0.3048);
                amountOfFeet = new BigDecimal(amount);
                imperial1 = false;
                break;
            case "Yard":
                amountOfMeters = new BigDecimal(amount * 0.9144);
                amountOfFeet = new BigDecimal(amount * 3);
                imperial1 = false;
                break;
            case "Mile":
                amountOfMeters = new BigDecimal(amount * 1609.34);
                amountOfFeet = new BigDecimal(amount * 5280);
                imperial1 = false;
                break;
            case "Nautical League":
                amountOfMeters = new BigDecimal(amount * 5556.0);
                imperial1 = true;
                break;
            case "Nautical Mile":
                amountOfMeters = new BigDecimal(amount * 1852.0);
                amountOfFeet = new BigDecimal(amount * 6076.115);
                imperial1 = false;
                break;
            case "Chain":
                amountOfMeters = new BigDecimal(amount * 20.1168);
                amountOfFeet = new BigDecimal(amount * 66);
                imperial1 = true;
                break;
            case "Rod":
                amountOfMeters = new BigDecimal(amount * 5.0292);
                amountOfFeet = new BigDecimal(amount * 16.5);
                imperial1 = true;
                break;
            case "Fathom":
                amountOfMeters = new BigDecimal(amount * 1.8288);
                amountOfFeet = new BigDecimal(amount * 6);
                imperial1 = true;
                break;
            case "Select Unit":
                amountOfMeters = new BigDecimal(0);
                break;

        }

        return amountOfMeters;

    }

    public void convertButtonClicked(View v){
        TextView textView = (TextView)spinner.getSelectedView();
        TextView textView2 =(TextView)spinner2.getSelectedView();

        try {
            fromUnit = textView.getText().toString(); //gets the name of the start unit (string value)
            toUnit = textView2.getText().toString(); //gets the name of the end unit (string value)
        } catch (NullPointerException e){
            System.out.println(e);
        }

        edit = (EditText)findViewById(R.id.editText);
        String result = edit.getText().toString();
        try {
            int num = Integer.parseInt(result);
            amount = (double)num;
        }catch(NumberFormatException e){
            System.out.println(e);
        }

        BigDecimal from = new BigDecimal(0);
        BigDecimal to = new BigDecimal(0);
        BigDecimal am = new BigDecimal(0);

        from = toMeters();

        try {

            switch (toUnit) {
                case "Kilometer":
                    am = new BigDecimal(0.001);
                    to = from.multiply(am, mc);

                    imperial = true;
                    break;
                case "Hectometer":
                    am = new BigDecimal(0.01);
                    to = from.multiply(am, mc);

                    imperial = true;
                    break;
                case "Decameter":
                    am = new BigDecimal(0.1);
                    to = from.multiply(am, mc);

                    imperial = true;
                    break;
                case "Meter":
                    to = from;

                    imperial = true;
                    break;
                case "Decimeter":
                    am = new BigDecimal(10);
                    to = from.multiply(am, mc);

                    imperial = true;
                    break;
                case "Centimeter":
                    am = new BigDecimal(100);
                    to = from.multiply(am, mc);

                    imperial = true;
                    break;
                case "Millimeter":
                    am = new BigDecimal(1000);
                    to = from.multiply(am, mc);

                    imperial = true;
                    break;
                case "Inch":
                    if (imperial1 == true) {
                        am = new BigDecimal(39.3701);
                        to = from.multiply(am, mc);

                    } else {
                        am = new BigDecimal(12);
                        to = amountOfFeet.multiply(am, mc);

                    }
                    break;
                case "Foot":
                    if (imperial1 == true) {
                        am = new BigDecimal(3.28084);
                        to = from.multiply(am, mc);

                    } else {
                        to = amountOfFeet;

                    }
                    break;
                case "Yard":
                    if (imperial1 == true) {
                        am = new BigDecimal(1.09361);
                        to = from.multiply(am, mc);

                    } else {
                        am = new BigDecimal(3);
                        to = amountOfFeet.divide(am, mc);

                    }
                    break;
                case "Mile":
                    if (imperial1 == true) {
                        am = new BigDecimal(0.000621371);
                        to = from.multiply(am, mc);

                    } else {
                        am = new BigDecimal(5280);
                        to = amountOfFeet.divide(am, mc);

                    }
                    break;
                case "Nautical League":
                    am = new BigDecimal(0.000179986);
                    to = from.multiply(am, mc);

                    break;
                case "Nautical Mile":
                    am = new BigDecimal(0.000539957);
                    to = from.multiply(am, mc);

                    break;
                case "Chain":
                    am = new BigDecimal(0.0497097);
                    to = from.multiply(am, mc);

                    break;
                case "Rod":
                    am = new BigDecimal(0.198839);
                    to = from.multiply(am, mc);

                    break;
                case "Fathom":
                    am = new BigDecimal(0.546807);
                    to = from.multiply(am, mc);

                    break;

            }
        } catch (NullPointerException e){}

        int ref = to.intValue();
        to = to.setScale(amountOfDecimals, to.ROUND_HALF_UP);

        String text = "";

        if (fromUnit.equals("Select Unit") && toUnit.equals("Select Unit")) {
            text = "Select a unit to convert to and from.";
        } else if (fromUnit.equals("Select Unit") && !toUnit.equals("Select Unit")) {
            text = "Select a unit to convert from.";
        } else if (toUnit.equals("Select Unit") && !fromUnit.equals("Select Unit")) {
            text = "Select a unit to convert to.";
        } else {
            if (amount != 1){
                if (fromUnit.equals("Foot")){
                    fromUnit = "Feet";
                } else if (fromUnit.equals("Inch")){
                    fromUnit = "Inches";
                } else {
                    fromUnit += "s";
                }
            }
            if (ref != 1){

                if (toUnit.equals("Foot")){
                    toUnit = "Feet";
                } else if (toUnit.equals("Inch")){
                    toUnit = "Inches";
                } else {
                    toUnit += "s";
                }
            }

            text = amount + " " + fromUnit + " = " + to + " " + toUnit;
        }

        String output = "";

        for (int i = conversionList.size() - 1; i >= 0; i--) {
            output += conversionList.get(i) + "\n";
        }
        conversionList.add(text);


        //set output to calculated result
        TextView tv = (TextView)findViewById(R.id.textView5); //blue top
        TextView tv2 = (TextView)findViewById(R.id.textView4); //gray bottom (list)
        tv2.setText(output);
        tv.setText(text);
        tv2.setMovementMethod(new ScrollingMovementMethod());

        TextView tvside = (TextView)findViewById(R.id.sideOutput);
        tvside.setText("" + to);

    }

    public void clearListButtonClicked(View v) {
        TextView tv = (TextView)findViewById(R.id.textView5); //blue top
        TextView tv2 = (TextView)findViewById(R.id.textView4); //gray bottom (list)
        tv.setText("");
        tv2.setText("");
        conversionList.clear();
    }

}
