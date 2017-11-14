package com.example.pc.databindingdemo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.pc.databindingdemo.databinding.ActivityMainBinding;
import com.example.pc.databindingdemo.databinding.ListItemBinding;

public class MainActivity extends AppCompatActivity {

    User user = new User("Test", "User");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setUser(user);
        binding.listView.setAdapter(adapter);
        new Thread(){
            @Override
            public void run(){
                user.firstName = "firstName";
                user.lastName = "lastName";
            }
        }.start();
    }

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListItemBinding binding;
            if (convertView == null) {
                binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.list_item, parent, false);
                convertView = binding.getRoot();
                convertView.setTag(binding);
            } else {
                binding = (ListItemBinding) convertView.getTag();
            }
            binding.setUser(user);
            return convertView;
        }
    };
}
