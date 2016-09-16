package com.hackzurich.carapp.carapphackzurich;

import android.hardware.SensorEvent;

import android.util.Log;


public class SensorsCalulations {

    private double[] gravity;
    private double[] linear_acceleration;
    private static final int INT_ROUNDVAL = 100;
    private static final double DOUBLE_ROUNDVAL = 100.0;
    // Create a constant to convert nanoseconds to seconds.
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float[] deltaRotationVector;
    private float timestamp;
    private static final float EPSILON = 0.000001f;

    public SensorsCalulations(){

        gravity = new double[3];
        linear_acceleration = new double[3];
        deltaRotationVector = new float[4];
    }

    public String Calculate_Accel(SensorEvent event){

        final double alpha = 0.8;
        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        String disp = event.sensor.getName() + "\n";
        disp += "X: " + Double.toString(Math.round(linear_acceleration[0] * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + " m/s²" + "\n"
                + "Y: " + Double.toString(Math.round(linear_acceleration[1]* INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + " m/s²" + "\n"
                + "Z: " + Double.toString(Math.round(linear_acceleration[2]* INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + " m/s²";

        return disp;
    }

    public String Calculate_Gyro(SensorEvent event) {

        // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.

        float axisX = event.values[0];
        float axisY = event.values[1];
        float axisZ = event.values[2];

        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        //  rotationCurrent = rotationCurrent * deltaRotationMatrix;
        String disp = event.sensor.getName() + "\n";
        disp += "X: " + Double.toString(Math.round(axisX * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad/s" + "\n"
                + "Y: " + Double.toString(Math.round(axisY * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad/s" + "\n"
                + "Z: " + Double.toString(Math.round(axisZ * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad/s";

        return disp;
    }

    public String Calculate_Gyro_Un(SensorEvent event) {

        // This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.

        float axisX = event.values[0];
        float axisY = event.values[1];
        float axisZ = event.values[2];


        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        //  rotationCurrent = rotationCurrent * deltaRotationMatrix;
        String disp = event.sensor.getName() + "\n";
        disp += "X: " + Double.toString(Math.round(axisX * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad/s" + "\n"
                + "Y: " + Double.toString(Math.round(axisY * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad/s" + "\n"
                + "Z: " + Double.toString(Math.round(axisZ * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad/s" + "\n";

        return disp;
    }

    public String Calculate_Gravity(SensorEvent event) {

        double axisX = event.values[0];
        double axisY = event.values[1];
        double axisZ = event.values[2];

        String disp = event.sensor.getName() + "\n";
        disp += "X: " + Double.toString(Math.round(axisX * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + " m/s²" + "\n"
                + "Y: " + Double.toString(Math.round(axisY * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + " m/s²" + "\n"
                + "Z: " + Double.toString(Math.round(axisZ * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + " m/s²" + "\n";

        return disp;
    }

    public String Calculate_Magnetometer(SensorEvent event) {

        double mT = event.values[0];

        String disp = event.sensor.getName() + "\n";
        disp += Double.toString(Math.round(mT * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + " μT";

        return disp;
    }

    public String Calculate_MagnetometerUn(SensorEvent event) {

        double mT = event.values[0];

        String disp = event.sensor.getName() + "\n";
        disp += Double.toString(Math.round(mT * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + " μT";

        return disp;
    }

    public String Calculate_LinearAccel(SensorEvent event) {

        double la = event.values[0];
        double la1 = event.values[1];
        double la2 = event.values[2];

        String disp = event.sensor.getName() + "\n";
        disp += Double.toString(Math.round(la * INT_ROUNDVAL) / DOUBLE_ROUNDVAL)  + " m/s²" + "\n"
                + Double.toString(Math.round(la1 * INT_ROUNDVAL) / DOUBLE_ROUNDVAL)  + " m/s²" + "\n"
                + Double.toString(Math.round(la2 * INT_ROUNDVAL) / DOUBLE_ROUNDVAL)  + " m/s²";

        return disp;
    }

    public String Calculate_RotationVector(SensorEvent event) {

        double axisX = event.values[0];
        double axisY = event.values[1];
        double axisZ = event.values[2];
        double W = event.values[3];

        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        //  rotationCurrent = rotationCurrent * deltaRotationMatrix;
        String disp = event.sensor.getName() + "\n";
        disp += "X: " + Double.toString(Math.round(axisX * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad" + "\n"
                + "Y: " + Double.toString(Math.round(axisY * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad" + "\n"
                + "Z: " + Double.toString(Math.round(axisZ * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad" + "\n"
                + "W(scalar): " + Double.toString(Math.round(W * INT_ROUNDVAL) / DOUBLE_ROUNDVAL) + "  rad";

        return disp;
    }
}
