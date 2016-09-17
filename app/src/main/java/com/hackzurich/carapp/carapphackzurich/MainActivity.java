package com.hackzurich.carapp.carapphackzurich;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.BroadcastReceiver;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "SensorsService";
    private Intent serviceIntent;
    private Intent backgroundIntent;
    private Intent webIntent;
    final Context context = this;

    private Button btCarID;
    private Button btCarType;
    private Button btStartSensorService;
    private Button btStopSensorService;
    private Button btDisplaySensors;
    private TextView tvCarID;
    private TextView tvCarType;
    private TextView tvLogArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceIntent = new Intent(this, SensorsViewActivity.class);
        backgroundIntent = new Intent(this, SensorsService.class);
        webIntent = new Intent(this, FrontEndActivity.class);

        btCarID = (Button) findViewById(R.id.btCarID);
        btCarType = (Button) findViewById(R.id.btCarType);
        tvCarID = (TextView) findViewById(R.id.tvCarID);
        tvCarType = (TextView) findViewById(R.id.tvCarType);
        btStartSensorService = (Button) findViewById(R.id.btStarSensorService);
        btStopSensorService = (Button) findViewById(R.id.btStopSensorService);
        btDisplaySensors = (Button) findViewById(R.id.btDisplaySensors);
        tvLogArea = (TextView) findViewById(R.id.tvLogArea);
    }

    private BroadcastReceiver sensorsSReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateLogArea(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        tvCarID.setText("Car ID: ");
        tvCarType.setText("Car Type: ");
        tvLogArea.setText("Test text");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void showSensors(View view) {
        startActivity(serviceIntent);
    }

    public void setCarID(View view) {
        showInputDialog("ID");
    }

    public void setCarType(View view) {
        showInputDialog("Type");
    }

    protected void showInputDialog(final String type) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.prompts, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (type.equals("ID")) {
                            tvCarID.setText("Car ID: " + editText.getText());
                        }
                        else if (type.equals("Type")){
                            tvCarType.setText("Car Type: " + editText.getText());
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void StarSensorService(View view) {
        startService(backgroundIntent);
        registerReceiver(sensorsSReceiver, new IntentFilter(SensorsService.BROADCAST_ACTION));
    }

    public void StopSensorService(View view) {
        try {
            unregisterReceiver(sensorsSReceiver);
            stopService(backgroundIntent);
        }
        catch (java.lang.IllegalArgumentException e){
            Toast.makeText(context, "Service Already Stoppped", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLogArea(Intent intent){
        tvLogArea.setText("");
        tvLogArea.setText("TYPE_ACCELEROMETER: " + intent.getStringExtra("TYPE_ACCELEROMETER") + "\n\n"
                + "TYPE_GYROSCOPE: " + intent.getStringExtra("TYPE_GYROSCOPE") + "\n\n"
                + "TYPE_GYROSCOPE_UNCALIBRATED: " + intent.getStringExtra("TYPE_GYROSCOPE_UNCALIBRATED") + "\n\n"
                + "TYPE_MAGNETIC_FIELD: " + intent.getStringExtra("TYPE_MAGNETIC_FIELD") + "\n\n"
                + "TYPE_MAGNETIC_FIELD_UNCALIBRATED: " + intent.getStringExtra("TYPE_MAGNETIC_FIELD_UNCALIBRATED") + "\n\n"
                + "TYPE_LINEAR_ACCELERATION: " + intent.getStringExtra("TYPE_LINEAR_ACCELERATION") + "\n\n"
                + "TYPE_ROTATION_VECTOR: " + intent.getStringExtra("TYPE_ROTATION_VECTOR") + "\n\n");
    }

    public void startWebview(View view) {
        startActivity(webIntent);
    }
}
