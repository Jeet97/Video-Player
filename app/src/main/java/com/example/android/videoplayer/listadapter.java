package com.example.android.videoplayer;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jeet on 26-06-2017.
 */

public class listadapter extends ArrayAdapter {
    ArrayList<getsetclass> arl ;
    Activity act;

    public listadapter(Activity act,ArrayList<getsetclass> arl)
    {

        super(act,R.layout.folderlistitem,arl);
      this.arl = arl;
        this.act = act;





    }







    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vw;
        vw = LayoutInflater.from(act).inflate(R.layout.folderlistitem,parent,false);

        vw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(act,MainActivity.class);
                in.putExtra("foldername",arl.get(position).getVname());
                act.startActivity(in);
            }
        });

        TextView tv1,tv2;

        tv1  =(TextView) vw.findViewById(R.id.foldername);
        tv2  =(TextView) vw.findViewById(R.id.filescount);

        getsetclass gt = arl.get(position);

        tv1.setText(gt.getVname());
        tv2.setText(gt.getArtname());


        return vw;
    }


}
