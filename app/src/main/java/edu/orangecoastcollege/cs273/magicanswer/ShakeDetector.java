package edu.orangecoastcollege.cs273.magicanswer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by gbyers on 10/27/2016.
 */
//gforce is a2 *b2 = C2


public class ShakeDetector implements SensorEventListener {
    private static final float SHAKE_THRESHHOLD = 25;
    private static final int SHAKE_TIME_LAPSE = 2000;
 private long occurLast;
    //define a listener to register on shake events

    private onShakeListener shakeListener;

    public ShakeDetector(onShakeListener listener)
    {
        shakeListener = listener;
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//determine if the even is an accolrometer
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            //get x y and z values
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z =sensorEvent.values[2];
            //compare all three variables to gravity
            float gForceX = x-SensorManager.GRAVITY_EARTH;
            float gForceY = y-SensorManager.GRAVITY_EARTH;
            float gForceZ = z-SensorManager.GRAVITY_EARTH;

             double vector= Math.pow(gForceX, 2)+Math.pow(gForceY,2)+Math.pow(gForceZ, 2);

            float gForce = (float) Math.sqrt(vector);
            if(gForce > SHAKE_THRESHHOLD)
            {
                //current time
                long time = System.currentTimeMillis();
                //see if current time is at least 2000 ms greater than time of last shake
                if(time - SHAKE_TIME_LAPSE >= occurLast)
                {
                    occurLast = time;
                    //register shake event
                    shakeListener.onShake();

                }

            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface onShakeListener{
        void onShake();

    }


}
