package com.tamer.vlsm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.tamer.vlsm.model.Address;
import com.tamer.vlsm.model.InputSubnet;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {
    private RecyclerView recyclerView;
    ResultSubnetAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();
        List<Address> resultSubnets = (List<Address>) intent.getSerializableExtra("LIST");
        recyclerView=findViewById (R.id.recyclerview);
        adapter=new ResultSubnetAdapter(resultSubnets,this);
        recyclerView.setLayoutManager (new LinearLayoutManager(this));
        recyclerView.setAdapter (adapter);

    }
}