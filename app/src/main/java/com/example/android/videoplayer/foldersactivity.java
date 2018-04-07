package com.example.android.videoplayer;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class foldersactivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<getsetclass>> {
    ListView lv;
   listadapter la;
   // ProgressBar pb;
    ArrayList<getsetclass> arl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foldersactivity);


        lv = (ListView) findViewById(R.id.folderlist);
//pb = (ProgressBar) findViewById(R.id.pd1);

        getSupportLoaderManager().initLoader(007,null,this).forceLoad();




    }


    @Override
    public Loader<ArrayList<getsetclass>> onCreateLoader(int id, Bundle args) {
      return   new listloader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<getsetclass>> loader, ArrayList<getsetclass> data) {

        arl = data;
        la = new listadapter(this,arl);
        lv.setAdapter(la);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<getsetclass>> loader) {

    }
}
