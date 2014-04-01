package com.testtwo.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.concurrent.Callable;


public class MainActivity extends ActionBarActivity {

    private static final String KEY_TOTAL_AMOUNT = "KEY_TOTAL_AMOUNT";
    private static final String KEY_CURRENT_TIP = "KEY_CURRENT_TIP";
    private static final String KEY_BILL = "KEY_BILL";

    private double bill;
    private double tip;
    private double billAmount;

    EditText etBill;
    EditText etTip;
    EditText etBillAmount;

    SeekBar seekbarTip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            bill = 0.0;
            tip = 0.15;
            billAmount = 0.0;
        }else{
            bill = savedInstanceState.getDouble(KEY_BILL);
            tip = savedInstanceState.getDouble(KEY_CURRENT_TIP);
            billAmount = savedInstanceState.getDouble(KEY_TOTAL_AMOUNT);
        }


        etBill = (EditText) findViewById(R.id.editTextBill);
        etTip = (EditText) findViewById(R.id.editTextTip);
        etBillAmount = (EditText) findViewById(R.id.editTextFinalBill);

        etBill.addTextChangedListener(billChange);

        seekbarTip = (SeekBar) findViewById(R.id.seekBarTip);

        seekbarTip.setOnSeekBarChangeListener(seekBarTipChangeListener);

    }

    protected void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);

        outState.putDouble(KEY_TOTAL_AMOUNT, bill);
        outState.putDouble(KEY_CURRENT_TIP, tip);
        outState.putDouble(KEY_BILL, billAmount);

    }

    private SeekBar.OnSeekBarChangeListener seekBarTipChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            tip = (double) seekBar.getProgress() * 0.01;
            etTip.setText(String.format("%.02f", tip));
            updateFinalBill();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private TextWatcher billChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            try{
                bill = Double.parseDouble(charSequence.toString());
            }catch (NumberFormatException e){
                bill = 0.0;
            }

            updateFinalBill();

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void updateFinalBill(){

        double currentTip = Double.parseDouble(etTip.getText().toString());

        double finalAmount = bill + (bill * currentTip);

        etBillAmount.setText(String.format("%.02f", finalAmount));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
