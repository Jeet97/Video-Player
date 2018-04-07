package com.example.android.videoplayer;

import android.util.Log;

/**
 * Created by Jeet on 17-07-2017.
 */

public class ProgressCallbacks implements Runnable {



    public interface LoaderProgress {
        public void onLoaderProgressUpdate(getsetclass gt);
    }

    LoaderProgress l;
    getsetclass gt;
    public ProgressCallbacks(LoaderProgress l, getsetclass gt) {
this.l = l;
        this.gt = gt;

    }

    @Override
    public void run() {
l.onLoaderProgressUpdate(gt);
        Log.v("djfbuwouf",gt.getVname());

    }


}