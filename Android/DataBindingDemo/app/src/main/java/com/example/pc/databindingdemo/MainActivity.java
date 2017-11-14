package com.example.pc.databindingdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActivityBasicBinding binding = DataBindingUtil.setContentView(
//                this, R.layout.activity_main);
//        User user = new User("fei", "Liang");
//        binding.setUser(user);
    }
}
