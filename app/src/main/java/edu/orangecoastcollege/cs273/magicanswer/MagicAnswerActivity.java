package edu.orangecoastcollege.cs273.magicanswer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class MagicAnswerActivity extends AppCompatActivity {

    MagicAnswer magicAnswer;
    private TextView answerTextView;
    private EditText questionEditText;
    private ShakeDetector shakedetector;
    private MagicAnswer ma;

    //reference to sensor manager
    private SensorManager sensorManager;
    //reference to Acceloromter
    private Sensor accelorometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_answer);

        // TASK 1: SET THE REFERENCES TO THE LAYOUT ELEMENTS
        answerTextView = (TextView) findViewById(R.id.answerTextView);
        questionEditText= (EditText) findViewById(R.id.questionEditText);

        // TASK 2: CREATE A NEW MAGIC ANSWER OBJECT
          ma = new MagicAnswer(this);

        // TASK 3: REGISTER THE SENSOR MANAGER AND SETUP THE SHAKE DETECTION
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelorometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakedetector = new ShakeDetector(new ShakeDetector.onShakeListener() {
            @Override
            public void onShake() {
                //get random answer
                String ans = ma.getRandomAnswer();
                answerTextView.setText(ans);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(shakedetector, accelorometer,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakedetector, accelorometer);
    }
}
