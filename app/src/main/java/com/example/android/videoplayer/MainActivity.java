package com.example.android.videoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    String folder;
    LinearLayoutManager llm;
    static customadapter cs;
    ArrayList<getsetclass> arl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Intent in = getIntent();
        folder  = in.getStringExtra("foldername");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        llm = new LinearLayoutManager(this);
        rv = (RecyclerView) findViewById(R.id.recycle);
        rv.setLayoutManager(llm);
        cs = new customadapter(this,folder);
        rv.setAdapter(cs);
        rv.setHasFixedSize(true);



    }







}
