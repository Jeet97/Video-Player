package com.example.android.videoplayer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jeet on 25-06-2017.
 */

public class customadapter extends RecyclerView.Adapter<customadapter.myviewholder> implements LoaderManager.LoaderCallbacks<ArrayList<getsetclass>>{
    public  ArrayList<getsetclass> arl;
    AppCompatActivity act;
    String foldername;
    Cursor csr;

    public customadapter(AppCompatActivity act,String foldername)
    {
        this.act = act;
        this.foldername = foldername;
      arl = new ArrayList<getsetclass>();

        act.getSupportLoaderManager().initLoader(106,null,this).forceLoad();
        /*
            asny as = new asny();
            as.execute();
*/

        }

    @Override
    public myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vw = LayoutInflater.from(act).inflate(R.layout.custom_layout,parent,false);


        return new myviewholder(vw);
    }

    public void setArrayList(getsetclass gt)
    {
        arl.add(gt);
    }


    @Override
    public void onBindViewHolder(final myviewholder holder, int position) {

       final getsetclass gt = arl.get(position);


        holder.vname.setText(gt.getVname());
      //  holder.artname.setText(gt.getArtname());

        Glide.with(act).load(gt.getUrl())
                .into(holder.vthumb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(act,VideoActivity.class);
                HashMap<String,String> hs = new HashMap<String, String>();
                hs.put("name",gt.getVname());
                hs.put("url",gt.getUrl());

                in.putExtra("map",hs);
                act.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arl.size();
    }

    @Override
    public Loader<ArrayList<getsetclass>> onCreateLoader(int id, Bundle args) {
    return new customloader(act,foldername,this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<getsetclass>> loader, ArrayList<getsetclass> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<getsetclass>> loader) {


    }


    public class myviewholder extends RecyclerView.ViewHolder
    {
         TextView vname;
       // TextView artname;
         ImageView vthumb;
        public myviewholder(View vw)
        {
            super(vw);
            vname =   (TextView)vw.findViewById(R.id.custvname);
           // artname = (TextView)vw.findViewById(R.id.custartname);
            vthumb =(ImageView) vw.findViewById(R.id.custimage);



        }



    }


    /*
    public class asny extends AsyncTask<Void,getsetclass,Void>
    {

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected Void doInBackground(Void... urls) {


            String[] proj = {
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.ARTIST,
                    MediaStore.Video.Thumbnails.DATA

            };

            String selection= MediaStore.Video.Media.DATA +" like?";
            String[] selectionArgs=new String[]{"%"+foldername+"%"};

            csr = act.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,proj,selection,selectionArgs,null);
            int i1,i2,i3;
            Bitmap bt ;
            while (csr.moveToNext())
            {

                if (act.isDestroyed()) {
                   cancel(true);
                    Log.v("Canceled","eiufgy9edg9pygdy9gd7ug9epufg9pdfhugf");



                }
                i1 = csr.getColumnIndex( MediaStore.Video.Media.DISPLAY_NAME);
                i2 = csr.getColumnIndex( MediaStore.Video.Media.ARTIST);
                i3 = csr.getColumnIndex( MediaStore.Video.Thumbnails.DATA);
                bt =  ThumbnailUtils.createVideoThumbnail(csr.getString(i3), MediaStore.Video.Thumbnails.MINI_KIND);


                publishProgress(new getsetclass(csr.getString(i1),csr.getString(i2),bt));

            }
            csr.close();

return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.v("completed","coprjgbuign");

        }

        @Override
        protected void onProgressUpdate(getsetclass... values) {
            arl.add(values[0]);
            customadapter.this.notifyDataSetChanged();
        }


    }
*/


}
