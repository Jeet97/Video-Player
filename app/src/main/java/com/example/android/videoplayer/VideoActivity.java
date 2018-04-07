package com.example.android.videoplayer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.HashMap;

public class VideoActivity extends AppCompatActivity {
VideoView vd;
    custommediacontroller mc;
    Intent in;
    int currentposition;
    MediaPlayer mp;
    String url;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.video_activity);
getWindow().setStatusBarColor(Color.BLACK);
        vd = (VideoView) findViewById(R.id.vv);
        if (getResources().getConfiguration().orientation ==Configuration.ORIENTATION_LANDSCAPE)

        {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
            rlp.addRule(RelativeLayout.ALIGN_BOTTOM);
            rlp.addRule(RelativeLayout.ALIGN_TOP);
            rlp.addRule(RelativeLayout.ALIGN_LEFT);
            rlp.addRule(RelativeLayout.ALIGN_RIGHT);
            vd.setLayoutParams(rlp);

        }

        in = getIntent();

        url = ((HashMap<String,String>)in.getSerializableExtra("map")).get("url");

if (savedInstanceState!=null)
{
    url = savedInstanceState.getString("url");
    currentposition  = savedInstanceState.getInt("current");

}



        mc = new custommediacontroller(this);
    // mc.setToolbar (  ((HashMap<String,String>)in.getSerializableExtra("map")).get("name"));
mc.setToolbar(((HashMap<String,String>)in.getSerializableExtra("map")).get("name"));
        mc.setAnchorView(vd);
        mc.getanchorview(vd,url);

        Uri uri = Uri.parse(url);

        vd.setMediaController(mc);
        vd.setVideoURI(uri);
        vd.requestFocus();



        vd.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mc.setMediaPlayer( mediaPlayer);
                mp = mediaPlayer;

            }
        });





    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

if (newConfig.orientation ==Configuration.ORIENTATION_LANDSCAPE)

{
    DisplayMetrics metrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metrics);
    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
    rlp.addRule(RelativeLayout.ALIGN_BOTTOM);
    rlp.addRule(RelativeLayout.ALIGN_TOP);
    rlp.addRule(RelativeLayout.ALIGN_LEFT);
    rlp.addRule(RelativeLayout.ALIGN_RIGHT);
    vd.setLayoutParams(rlp);

}
       super.onConfigurationChanged(newConfig);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vd.stopPlayback();


    }

    @Override
    protected void onPause() {
        super.onPause();
       currentposition = vd.getCurrentPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vd.resume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("url",url);
        outState.putInt("current",currentposition);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        vd.start();
        if (currentposition!=-1)
        {
            vd.seekTo(currentposition-3000);

        }
    }


}
