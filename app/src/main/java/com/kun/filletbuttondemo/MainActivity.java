package com.kun.filletbuttondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kun.filletbuttondemo.view.FilletButton;

public class MainActivity extends AppCompatActivity {
    private FilletButton btn_test_1;
    private FilletButton change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_test_1 = (FilletButton) findViewById(R.id.btn_1);
        change = (FilletButton) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_test_1.setText("已更改").setBackgroundColor(R.color.c_fff833,R.color.c_97d51c).refresh();
            }
        });
    }
}
