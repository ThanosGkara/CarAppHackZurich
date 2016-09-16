package com.hackzurich.carapp.carapphackzurich;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.content.BroadcastReceiver;

public class MainActivity extends Activity {

    private static final String TAG = "SensorsService";
    private Intent intent;

    private TextView tvAccel;
    private TextView tvGyro;
    private TextView tvGyroUn;
//    private TextView tvSigMotion;
    private TextView tvGravity;
    private TextView tvMagnitometer;
    private TextView tvMagnitometerUn;
    private TextView tvLinearAccel;
    private TextView tvRotationVector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, SensorsService.class);

        tvAccel = (TextView) findViewById(R.id.tvACCELEROMETER);
        tvGyro = (TextView) findViewById(R.id.tvGYROSCOPE);
        tvGyroUn = (TextView) findViewById(R.id.tvGYROSCOPE_UNCALIBRATED);
//        tvSigMotion = (TextView) findViewById(R.id.tvSIGNIFICANT_MOTION);
        tvGravity = (TextView) findViewById(R.id.tvGRAVITY);
        tvMagnitometer = (TextView) findViewById(R.id.tvMAGNETOMETER);
        tvMagnitometerUn = (TextView) findViewById(R.id.tvMAGNETOMETER_UNCALIBRATED);
        tvLinearAccel = (TextView) findViewById(R.id.tvLINEAR_ACCELERATION);
        tvRotationVector = (TextView) findViewById(R.id.tvROTATION_VECTOR);
    }

    private BroadcastReceiver sensorsSReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        startService(intent);
        registerReceiver(sensorsSReceiver, new IntentFilter(SensorsService.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(sensorsSReceiver);
        stopService(intent);
    }

    private void updateUI(Intent intent) {
//        String counter = intent.getStringExtra("counter");
//        String time = intent.getStringExtra("time");
        tvAccel.setText(intent.getStringExtra("TYPE_ACCELEROMETER"));
        tvGyro.setText(intent.getStringExtra("TYPE_GYROSCOPE"));
        tvGyroUn.setText(intent.getStringExtra("TYPE_GYROSCOPE_UNCALIBRATED"));
//        tvSigMotion.setText(intent.getStringExtra("TYPE_GRAVITY"));
        tvGravity.setText(intent.getStringExtra("TYPE_MAGNETIC_FIELD"));
        tvMagnitometer.setText(intent.getStringExtra("TYPE_MAGNETIC_FIELD_UNCALIBRATED"));
        tvMagnitometerUn.setText(intent.getStringExtra("TYPE_LINEAR_ACCELERATION"));
        tvLinearAccel.setText(intent.getStringExtra("TYPE_ROTATION_VECTOR"));
        Log.d(TAG, "TYPE_ACCELEROMETER");
        Log.d(TAG, "TYPE_GYROSCOPE");
        Log.d(TAG, "TYPE_GYROSCOPE_UNCALIBRATED");
        Log.d(TAG, "TYPE_GRAVITY");
        Log.d(TAG, "TYPE_MAGNETIC_FIELD");
        Log.d(TAG, "TYPE_MAGNETIC_FIELD_UNCALIBRATED");
        Log.d(TAG, "TYPE_LINEAR_ACCELERATION");
        Log.d(TAG, "TYPE_ROTATION_VECTOR");

//        TextView txtDateTime = (TextView) findViewById(R.id.txtDateTime);
//        TextView txtCounter = (TextView) findViewById(R.id.txtCounter);
//        txtDateTime.setText(time);
//        txtCounter.setText(counter);


    }
}
