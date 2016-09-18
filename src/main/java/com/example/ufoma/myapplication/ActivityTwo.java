package com.example.ufoma.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class ActivityTwo extends AppCompatActivity implements View.OnClickListener {
    final String TAG = "lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_two);
        Switch switch2 = (Switch) findViewById(R.id.switch2);
        switch2.setOnClickListener(this);
        Log.d(TAG, "Activity создано");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch2:
                Intent intent1 = new Intent(this, Calculator.class);
                startActivity(intent1);
                onDestroy();
                break;
            default:
                break;
        }
    }
//
//    @Override
//    protected void onStart() {
//    super.onStart();
//        Log.d(TAG,"Activity становится видимым");
//
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG,"Activity получает фокус(состояние Resumed)");
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG,"Activity приостановлено(состояние Pause)");
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG,"Activity остановлено(состояние Stop)");
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG,"Activity уничтожено");
//    }
}
