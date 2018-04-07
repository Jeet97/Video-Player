package com.example.android.videoplayer;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by Jeet on 27-06-2017.
 */

public class listloader extends AsyncTaskLoader<ArrayList<getsetclass>> implements ProgressCallbacks.LoaderProgress{
ArrayList<getsetclass> arl;
    Cursor csr;
    Activity act;
    Set<String> s = new HashSet<String>();
    ArrayList<String> count = new ArrayList<String>();

    public listloader(Activity act) {
        super(act);
this.act = act;
        arl = new ArrayList<getsetclass>();
    }

    @Override
    public ArrayList<getsetclass> loadInBackground() {


      //  findfiles(new File(Environment.getExternalStorageDirectory().getPath()+"/"));
findfiles();
        Collections.sort(arl, new Comparator<getsetclass>() {
            @Override
            public int compare(getsetclass getsetclass, getsetclass t1) {
                return getsetclass.getVname().compareTo(t1.getVname());
            }
        });

        return arl;
    }

    public void findfiles()
    {


      String[] proj = new String[]{
              MediaStore.Video.Media.DATA
      };


csr = act.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,proj,null,null,null);

        while (csr.moveToNext())
        {
            int ind = csr.getColumnIndex(MediaStore.Video.Media.DATA);
            String path = csr.getString(ind);

            Log.v("efinwefnwef",""+path);



            s.add((new File(path).getParentFile()).getName());
            count.add((new File(path).getParentFile()).getName());







        }



        try {
           for (String x: s) {

int freq = Collections.frequency(count,x);
                arl.add(new getsetclass(x, freq+" Files"));
               Log.v("foiwehfuw",""+x);

            }
        }
        catch (NoSuchElementException ne)
        {
            ne.printStackTrace();
        }


finally {
            csr.close();
        }


    }

    private void countoccurence()
    {

    }

    @Override
    public void onLoaderProgressUpdate(getsetclass gt) {

        Log.v("Loading","Loading success");
    }
}
