package com.example.android.videoplayer;

import android.graphics.Bitmap;

/**
 * Created by Jeet on 25-06-2017.
 */

public class getsetclass {
    String vname;
    String artname;
    String url;
    Bitmap bt;







    public String getUrl() {

        return url;
    }

    public getsetclass(String vname, String artname, Bitmap bt, String url)
    {
        this.vname = vname;
        this.artname = artname;
        this.bt = bt;
        this.url = url;
    }

    public getsetclass(String vname, String artname) {
        this.vname = vname;
        this.artname = artname;
    }

    public getsetclass(String vname, String artname, String url) {
        this.vname = vname;
        this.artname = artname;
        this.url = url;
    }



    public String getVname() {

        return vname;
    }

    public Bitmap getBt() {
        return bt;
    }

    public String getArtname() {
        return artname;
    }
}
