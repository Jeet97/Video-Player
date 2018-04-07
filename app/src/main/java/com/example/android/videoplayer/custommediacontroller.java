package com.example.android.videoplayer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jeet on 13-07-2017.
 */

public class custommediacontroller extends MediaController implements GestureDetector.OnGestureListener {

    Activity cxt;
    ImageButton mPauseButton,mFfwdButton,mbackbutton,mFullscreenButton,menusetting,menuinfo;
    SeekBar sk;
    private int originalheight,originalwidth;
private MediaMetadataRetriever mmd = new MediaMetadataRetriever();
    HashMap<String,String> details = new HashMap<String,String>();
    File fl;
    VideoView anchorview;

PopupWindow pw;
    private static final int    FADE_OUT = 1;
    private static final int    SHOW_PROGRESS = 2;
    TextView mCurrentTime,mEndTime,videonm;
    TextView filename,location,size,format,date,resolution,duration;
    StringBuilder               mFormatBuilder;
    private static Handler myh = new Handler();
    private static final int    sDefaultTimeout = 3000;
    View mRoot;
  String  name;
    Toolbar tb;
    Formatter mFormatter;
    GestureDetector gdt;
    MediaPlayer mPlayer;

    boolean mDragging,mShowing;

    public custommediacontroller(Activity cxt) {
        super(cxt);
        this.cxt = cxt;
gdt = new GestureDetector(cxt,this);


    }





    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        initControllerView(mRoot);

    }

    public void getanchorview(VideoView anchorview, String path)
    {
        this.anchorview = anchorview;
        mmd.setDataSource(path);
fl = new File(path);


details.put("File name",fl.getName());
        details.put("Location",fl.getAbsolutePath());
        details.put("Size",String.valueOf(fl.length()/1000000)+" Mb");
        details.put("Format",mmd.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE));

        details.put("Date",new Date(fl.lastModified()).toString());
        details.put("Resolution",(mmd.extractMetadata(mmd.getFrameAtTime(5).getHeight()))+"x"+(mmd.extractMetadata(mmd.getFrameAtTime(5).getHeight())));
        details.put("Length",String.format("%02d:%02d",
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long)Long.valueOf(mmd.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))),
                java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds((long)Long.valueOf(mmd.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))) -
                        TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long)Long.valueOf(mmd.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)))
        )));

/*
        DisplayMetrics metrics = new DisplayMetrics();
        cxt.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(metrics.widthPixels,metrics.heightPixels);
        anchorview.setLayoutParams(lp);

       originalheight = metrics.heightPixels;
        originalwidth = metrics.widthPixels;

  */
    }


    public void setMediaPlayer(MediaPlayer player) {

        mPlayer  = player;
        updateplaypause();

        sk.setMax(mPlayer.getDuration());

        sk.setProgress(mPlayer.getCurrentPosition());

        mEndTime.setText(String.format("%02d:%02d",
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long)mPlayer.getDuration()),
                java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds((long)mPlayer.getDuration())-
                        TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long)mPlayer.getDuration()))
        ));
        myh.postDelayed(rn,100);
    }


    protected View makeControllerView() {
        LayoutInflater inflate = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflate.inflate(R.layout.mediacontrols, null);

          initControllerView(mRoot);


        return mRoot;
    }

    public void setToolbar(String title)
    {
name = title;
    }

    private void setDetails(View vw)
    {
        filename  = (TextView) vw.findViewById(R.id.filename);
        location  = (TextView) vw.findViewById(R.id.location);
        size  = (TextView) vw.findViewById(R.id.size);
        format  = (TextView) vw.findViewById(R.id.format);
        date  = (TextView) vw.findViewById(R.id.date);
        resolution  = (TextView) vw.findViewById(R.id.resolution);
        duration  = (TextView) vw.findViewById(R.id.length);

        filename.setText(details.get("File name"));
        location.setText(details.get("Location"));
        size.setText(details.get("Size"));
        format.setText(details.get("Format"));
        date.setText(details.get("Date"));
        resolution.setText(details.get("Resolution"));
        duration.setText(details.get("Length"));
    }

    private void initControllerView(View v) {


        v.setOnTouchListener(new OnTouchListener() {


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if ( gdt.onTouchEvent(motionEvent))
          return false;

        return true;
    }
});



        tb =(Toolbar) v.findViewById(R.id.my_toolbar);
        tb.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        menuinfo = (ImageButton) v.findViewById(R.id.infor);
        menusetting = (ImageButton) v.findViewById(R.id.settings);
        menuinfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup();
            }
        });
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();


                switch(id)
                {
                    case android.R.id.home:
                        NavUtils.navigateUpFromSameTask(cxt);
return  true;




                }




                return false;
            }
        });
videonm = (TextView) v.findViewById(R.id.videoname);
        videonm.setText(name);

        mPauseButton = (ImageButton) v.findViewById(R.id.pause);
        if (mPauseButton != null) {
            mPauseButton.requestFocus();
            mPauseButton.setImageResource(R.drawable.ic_pause_black_24dp);
            mPauseButton.setOnClickListener(mPauseListener);
        }

        mbackbutton = (ImageButton) v.findViewById(R.id.backb);
        if (mbackbutton != null) {
           // mbackbutton.setVisibility(GONE);
        }

        mFfwdButton = (ImageButton) v.findViewById(R.id.ffwd);
        if (mFfwdButton != null) {
         //   mFfwdButton.setVisibility(GONE);
        }

        mFullscreenButton = (ImageButton) v.findViewById(R.id.fullscreen);
        if (mFullscreenButton != null) {
            mFullscreenButton.requestFocus();
            mFullscreenButton.setOnClickListener(mFullscreenListener);
        }

        sk = (SeekBar)  v.findViewById(R.id.mediacontroller_progress);
sk.setOnSeekBarChangeListener(mSeekListener);

        mCurrentTime = (TextView) v.findViewById(R.id.time_current);
        mEndTime = (TextView) v.findViewById(R.id.time);



    }


    private View.OnClickListener mPauseListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            updateplaypause();
        }
    };

    public void updateplaypause()
    {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            mPauseButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);

        }

        else
        {


            mPlayer.start();
            mPauseButton.setImageResource(R.drawable.ic_pause_black_24dp);




        }

        sk.setProgress(mPlayer.getCurrentPosition());

        mCurrentTime.setText(String.format("%02d:%02d",
                java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) mPlayer.getCurrentPosition()),
                java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds((long) mPlayer.getCurrentPosition()) -
                        TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) mPlayer.getCurrentPosition()))
        ));


        myh.postDelayed(rn, 100);
    }

    private Runnable rn = new Runnable() {
        @Override
        public void run() {
            try {
                if (mPlayer != null) {
                    sk.setProgress(mPlayer.getCurrentPosition());
                    myh.postDelayed(this, 100);
                    mCurrentTime.setText(String.format("%02d:%02d",
                            java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) mPlayer.getCurrentPosition()),
                            java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds((long) mPlayer.getCurrentPosition()) -
                                    TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) mPlayer.getCurrentPosition()))
                    ));

                    if (mCurrentTime.getText().equals(mEndTime.getText())) {
                        mPlayer.seekTo(0);
                        mPlayer.pause();
                        mPauseButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }
                }
            }

            catch (IllegalStateException i)
            {
                i.printStackTrace();
                if (mPlayer!=null)
                    mPlayer.release();

                mPlayer = null;

                sk.setOnSeekBarChangeListener(null);
            }
        }
    };

    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mPlayer.seekTo(seekBar.getProgress());


        }
    };

    private void onhorizontalscroll(float deltax)
    {
        int x = (int) deltax;
        float scale;
        float progress = sk.getProgress();
        final int max = sk.getMax();


        if (x < 0) {
            // scrolling back
            scale = (float) (x) / (float) (max) ;
            progress = progress - (-(scale * max));
        } else {
            // scrolling forwards

            scale = (float) (x) / (float) max;
            progress += scale * max;
        }

        sk.setProgress((int)progress);
        mPlayer.seekTo(sk.getProgress());
    }


private OnClickListener mFullscreenListener  = new OnClickListener() {
    @Override
    public void onClick(View view) {
  if (cxt.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
      cxt.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        else
            cxt.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       // updateFullScreen();


    }
};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void updateFullScreen() {
        if (mRoot == null || mFullscreenButton == null || mPlayer == null) {

            return;
        }

      /*  if (anchorview.getLayoutParams().height==originalheight&&anchorview.getLayoutParams().width==originalwidth) {

            mFullscreenButton.setImageResource(R.drawable.ic_fullscreen_black_24dp);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(originalwidth/2,originalheight/2);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            anchorview.setLayoutParams(lp);

        }


        else {


            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(originalwidth,originalheight);
            anchorview.setLayoutParams(lp);





            mFullscreenButton.setImageResource(R.drawable.ic_fullscreen_exit_black_24dp);

        }
        */
      mPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.v("This is on down","");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.v("This is on showpress","");

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.v("This is on onsingleup","");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
       float deltax = motionEvent1.getX() - motionEvent.getX();
        Math.abs(deltax);
        onhorizontalscroll(deltax);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.v("This is longpress","");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.v("This is on Fling",v+" and "+v1);
        return false;
    }

    private void showpopup()
    {
        if (cxt.getRequestedOrientation()== ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            try {
                if (mPlayer.isPlaying())
updateplaypause();
                View layout = LayoutInflater.from(cxt).inflate(R.layout.infopopup, null);
                DisplayMetrics metrics = new DisplayMetrics();
                cxt.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                pw = new PopupWindow(layout, (int) (metrics.widthPixels * 0.7), (int)(metrics.heightPixels*0.8), true);
setDetails(layout);
                pw.showAtLocation(anchorview, Gravity.CENTER, 0, 0);
                pw.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                  updateplaypause();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }


}
