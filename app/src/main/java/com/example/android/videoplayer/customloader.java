package com.example.android.videoplayer;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Jeet on 17-07-2017.
 */

public class customloader extends AsyncTaskLoader<ArrayList<getsetclass>> implements ProgressCallbacks.LoaderProgress{

    Cursor csr;
    AppCompatActivity act;
    ArrayList<getsetclass> arl;
    customadapter cst;
    String folder;

    public customloader(AppCompatActivity context, String folder,customadapter cst) {
        super(context);
        arl = new ArrayList<getsetclass>();
        this.folder = folder;
        act = context;
        this.cst = cst;

    }

    @Override
    public ArrayList<getsetclass> loadInBackground() {


        String[] proj = {
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.ARTIST,
                MediaStore.Video.Thumbnails.DATA

        };

        String selection= MediaStore.Video.Media.DATA +" like?";
        String[] selectionArgs=new String[]{"%"+folder+"%"};

        csr = act.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,proj,selection,selectionArgs,null);
        int i1,i2,i3;

        while (csr.moveToNext())
        {
            i1 = csr.getColumnIndex( MediaStore.Video.Media.TITLE);
            i2 = csr.getColumnIndex( MediaStore.Video.Media.ARTIST);
            i3 = csr.getColumnIndex( MediaStore.Video.Thumbnails.DATA);
   //         bt =  ThumbnailUtils.createVideoThumbnail(csr.getString(i3), MediaStore.Video.Thumbnails.MINI_KIND);

            getsetclass gt = new getsetclass(csr.getString(i1),csr.getString(i2),csr.getString(i3));

            act.runOnUiThread(new ProgressCallbacks(this,gt));

        }
        csr.close();

        return arl;
    }


    @Override
    public void onLoaderProgressUpdate(getsetclass gt) {
       cst.setArrayList(gt);
        Log.v("ejafbwiejfbiwe",""+arl.size());
        cst.notifyDataSetChanged();
    }
}