package com.example.tingkuanlin.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private TextView ent_date_tv = null;
    private Button ent_date_btn = null;
    private TextView ent_time_tv = null;
    private Button ent_time_btn = null;

    private TextView exit_date_tv = null;
    private Button exit_date_btn = null;
    private TextView exit_time_tv = null;
    private Button exit_time_btn = null;

    private TextView parkingHr = null;
    private TextView parkingMin = null;
    private TextView parkingSec = null;

    private Button billing_btn = null;

    private TextView fee_tv = null;
    private TextView memo_tv = null;


    private int mYear, mMonth, mDay, mHour, mMinute;
    private int entryYear, entryMonth, entryDay, entryHour, entryMin, entrySec;
    private int exitYear, exitMonth, exitDay, exitHour, exitMin, exitSec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ent_date_tv = (TextView) findViewById(R.id.ent_date_txt);
        ent_date_btn = (Button) findViewById(R.id.ent_date_btn);
        ent_time_tv = (TextView) findViewById(R.id.ent_time_txt);
        ent_time_btn = (Button) findViewById(R.id.ent_time_btn);

        exit_date_tv = (TextView) findViewById(R.id.exit_date_txt);
        exit_date_btn = (Button) findViewById(R.id.exit_date_btn);
        exit_time_tv = (TextView) findViewById(R.id.exit_time_txt);
        exit_time_btn = (Button) findViewById(R.id.exit_time_btn);

        parkingHr = (TextView) findViewById(R.id.parkingHr);
        parkingMin = (TextView) findViewById(R.id.parkingMin);
        parkingSec = (TextView) findViewById(R.id.parkingSec);

        billing_btn = (Button) findViewById(R.id.billing_btn);

        fee_tv = (TextView) findViewById(R.id.fee_txt);
        memo_tv = (TextView) findViewById(R.id.memo_txt);


        //fee_tv.setText("gwgweg");

        ent_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEntryDatePickerDialog();
            }
        });

        ent_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEntryTimePickerDialog();
            }
        });

        exit_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExitDatePickerDialog();
            }
        });

        exit_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExitTimePickerDialog();
            }
        });

        billing_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diffDate();
            }
        });

    }

    public void diffDate(){

        memo_tv.setText("");
        fee_tv.setText("");
        boolean isCount = true;
        int totalFee = 0;

        Calendar entry = Calendar.getInstance();
        entry.set(entryYear, entryMonth, entryDay, entryHour, entryMin);
        Calendar exit = Calendar.getInstance();
        exit.set(exitYear, exitMonth, exitDay, exitHour, exitMin);

        if(exit.before(entry)) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("時間錯誤")
                    .setMessage("出場時間需晚於入場時間")
                    .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        else {

            long times = 0;
            long mill = exit.getTime().getTime() - entry.getTime().getTime();

            long sec = mill / 1000;
            long min = sec / 60;
            long hr = min / 60;

            long acuHr = hr;
            long acuMin = min - hr*60;
            long acuSec = sec - min*60;

            parkingHr.setText(String.valueOf(acuHr));
            parkingMin.setText(String.valueOf(acuMin));
            parkingSec.setText(String.valueOf(acuSec));


            if(acuHr < 1 && acuMin < 30){

                //fee_tv.setText("0");
                memo_tv.setText("30分鐘內離場免收費");

            }
            if(acuHr < 1 && acuMin >= 30){
                times = 2;
            }
            if(acuHr >= 1)
            {

                times = acuHr * 2;
                if(acuMin > 10 && acuMin <= 40) times = times + 1;
                if(acuMin > 40 && acuMin <= 59) times = times + 2;

            }

            for(int i=0;i<times;i++) totalFee += 15;
            Log.i("charge => ",String.valueOf(totalFee));
            fee_tv.setText(String.valueOf(totalFee));


        }



    }

    public void setEntryDatePickerDialog() {
        // 設定初始日期
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // 跳出日期選擇器
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // 完成選擇，顯示日期
                        ent_date_tv.setText(year + "/" + (monthOfYear + 1) + "/"
                                + dayOfMonth);

                        exit_date_tv.setText(year + "/" + (monthOfYear + 1) + "/"
                                + dayOfMonth);

                        entryYear = year;
                        entryMonth = monthOfYear;
                        entryDay = dayOfMonth;

                        exitYear = year;
                        exitMonth = monthOfYear;
                        exitDay = dayOfMonth;



                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void setExitDatePickerDialog() {
        // 設定初始日期
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // 跳出日期選擇器
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // 完成選擇，顯示日期
                        exit_date_tv.setText(year + "/" + (monthOfYear + 1) + "/"
                                + dayOfMonth);

                        exitYear = year;
                        exitMonth = monthOfYear;
                        exitDay = dayOfMonth;

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void setEntryTimePickerDialog() {
        // 設定初始時間
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // 跳出時間選擇器
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // 完成選擇，顯示時間
                        String minStr = String.valueOf(minute);
                        if(minStr.length() == 1) minStr = "0"+minStr;
                        ent_time_tv.setText(hourOfDay + ":" + minStr);

                        entryHour = hourOfDay;
                        entryMin = minute;
                    }
                }, mHour, mMinute, false);
        tpd.show();
    }
    public void setExitTimePickerDialog() {
        // 設定初始時間
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // 跳出時間選擇器
        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // 完成選擇，顯示時間
                        String minStr = String.valueOf(minute);
                        if(minStr.length() == 1) minStr = "0"+minStr;
                        exit_time_tv.setText(hourOfDay + ":" + minStr);

                        exitHour = hourOfDay;
                        exitMin = minute;

                    }
                }, mHour, mMinute, false);
        tpd.show();
    }

}
