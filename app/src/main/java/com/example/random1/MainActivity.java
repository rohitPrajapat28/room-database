package com.example.random1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.StatusBarManager;
import android.os.Bundle;

import com.example.random1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public NavController navController;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        navController = Navigation.findNavController(this, R.id.main_Fragment);

    }
}