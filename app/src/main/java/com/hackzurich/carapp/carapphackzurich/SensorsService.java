package com.hackzurich.carapp.carapphackzurich;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.Toast;


public class SensorsService extends Service implements SensorEventListener, LocationListener {

    private static final String TAG = "SensorsService";
    public static final String BROADCAST_ACTION = "com.hackzurich.carapp.carapphackzurich";
    private final Handler handler = new Handler();
    Intent intent;
    int counter = 0;
    int mStartMode;       // indicates how to behave if the service is killed

    private SensorManager mSensorManager;
    private SensorsCalulations calcs;
    private Sensor mAccel;
    private Sensor mGyro;
    private Sensor mGyroUn;
    private Sensor mGravity;
    private Sensor mMagnitometer;
    private Sensor mMagnitometerUn;
    private Sensor mLinearAccel;
    private Sensor mRotionVector;

    protected LocationManager locationManager;
    protected Location location;

    private String tvAccel;
    private String tvGyro;
    private String tvGyroUn;
    private String tvGravity;
    private String tvMagnitometer;
    private String tvMagnitometerUn;
    private String tvLinearAccel;
    private String tvRotationVector;

    private String mCurrentLatitude;
    private String mCurrentLongitude;
    private String sLongitude;
    private String sLatitude;

    @Override
    public void onCreate() {
        super.onCreate();

        intent = new Intent(BROADCAST_ACTION);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        calcs = new SensorsCalulations(mSensorManager);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mGyroUn = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mMagnitometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mMagnitometerUn = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        mLinearAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mRotionVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

    }

//    @Override
//    public void onStart(Intent intent, int startId) {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        handler.removeCallbacks(sendUpdatesToUI);
//        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
        handler.post(sendUpdatesToUI);

        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroUn, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnitometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagnitometerUn, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLinearAccel, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mRotionVector, SensorManager.SENSOR_DELAY_NORMAL);

//        try{
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    mCurrentLatitude = Double.toString(latitude);
                    mCurrentLongitude = Double.toString(longitude);
                    sLongitude = "Latitude: " + latitude;
                    sLatitude = "Longitude: " + longitude;
                    Log.d("Longitude: ", sLongitude);
                    Log.d("Latitude: ", sLatitude);
                }
            }
             else{
                Context context = this;
                Toast.makeText(context, "Please allow Location Services", Toast.LENGTH_SHORT).show();
            }

        return mStartMode;
    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            DisplayLoggingInfo();
//            handler.postDelayed(this, 10000); // 10 seconds
            handler.postDelayed(this, 100); // 10 seconds
//            handler.post(this); // instantly post the obtained data to the caller class
        }
    };

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.

        // Do something with this sensor value.
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                tvAccel = calcs.Calculate_Accel(event);
                break;

            case Sensor.TYPE_GYROSCOPE:
                tvGyro = calcs.Calculate_Gyro(event);
                break;

            case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                tvGyroUn = calcs.Calculate_Gyro_Un(event);
                break;

            case Sensor.TYPE_GRAVITY:
                tvGravity = calcs.Calculate_Gravity(event);
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                tvMagnitometer = calcs.Calculate_Magnetometer(event);
                break;

            case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                tvMagnitometerUn = calcs.Calculate_MagnetometerUn(event);
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                tvLinearAccel = calcs.Calculate_LinearAccel(event);
                break;

            case Sensor.TYPE_ROTATION_VECTOR:
                tvRotationVector = calcs.Calculate_RotationVector(event);
                break;
        }
    }

    private void DisplayLoggingInfo() {
//        Log.d(TAG, "entered DisplayLoggingInfo");
        intent.putExtra("TYPE_ACCELEROMETER", tvAccel);
        intent.putExtra("TYPE_GYROSCOPE", tvGyro);
        intent.putExtra("TYPE_GYROSCOPE_UNCALIBRATED", tvGyroUn);
        intent.putExtra("TYPE_GRAVITY", tvGravity);
        intent.putExtra("TYPE_MAGNETIC_FIELD", tvMagnitometer);
        intent.putExtra("TYPE_MAGNETIC_FIELD_UNCALIBRATED", tvMagnitometerUn);
        intent.putExtra("TYPE_LINEAR_ACCELERATION", tvLinearAccel);
        intent.putExtra("TYPE_ROTATION_VECTOR", tvRotationVector);
        intent.putExtra("GPS", sLongitude + "\n" + sLatitude);
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        mSensorManager.unregisterListener(this, mAccel);
        mSensorManager.unregisterListener(this, mGyro);
        mSensorManager.unregisterListener(this, mGyroUn);
        mSensorManager.unregisterListener(this, mGravity);
        mSensorManager.unregisterListener(this, mMagnitometer);
        mSensorManager.unregisterListener(this, mMagnitometerUn);
        mSensorManager.unregisterListener(this, mLinearAccel);
        mSensorManager.unregisterListener(this, mRotionVector);
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location loc) {
        double longitude = loc.getLongitude();
        double latitude = loc.getLatitude();
        mCurrentLatitude = Double.toString(latitude);
        mCurrentLongitude = Double.toString(longitude);
        sLongitude = "Longitude: " + longitude;
        sLatitude = "Latitude: " + latitude;
        Log.d("Longitude: ", sLongitude);
        Log.d("Latitude: ", sLatitude);
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

}
