package com.hackzurich.carapp.carapphackzurich;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.BroadcastReceiver;

public class MainActivity extends Activity {

    private static final String TAG = "SensorsService";
    private Intent intent;
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

        intent = new Intent(this, SensorsViewActivity.class);

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
        startActivity(intent);
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
}
