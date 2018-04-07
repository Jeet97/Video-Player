package com.example.android.videoplayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadActivity extends AppCompatActivity {
    Button download;
    ProgressBar pb;
    private static String FILE_SAVE_PATH = Environment.getExternalStorageDirectory().getPath()+"/Experiments/";
    private static String SERVER = "192.168.65.107";
    private static int PORT = 1234;
    private static String url = "http://192.168.1.103:1234/cgi-bin/myvideo.mp4";
   // private Socket s = null;
    URL ourl;
    private int bytesRead;

    private InputStream fls;
    private BufferedOutputStream bis;
    private FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);


        pb = (ProgressBar) findViewById(R.id.dprogress);
        download = (Button) findViewById(R.id.download);


        asyncit as = new asyncit();
        as.execute();

    }

    class asyncit extends AsyncTask<Void,Void,Void>
    {


        @Override
        protected Void doInBackground(Void... voids) {
            fls = null;
            bis = null;
            fos = null;
            int current  =0;
try {
    //s = new Socket(SERVER, PORT);
ourl = new URL(url);

byte[] bytep = new byte[14000000];

    fls = ourl.openStream();

    fos = new FileOutputStream(FILE_SAVE_PATH);
    bis = new BufferedOutputStream(fos);

    do {
        bytesRead =
                fls.read(bytep, current, (bytep.length-current));
        if(bytesRead >= 0) current += bytesRead;

    } while(bytesRead > -1);

    bis.write(bytep, 0 , current);
    bis.flush();
    // System.out.println("File " + FILE_TO_RECEIVED
    //       + " downloaded (" + current + " bytes read)");
    Toast.makeText(DownloadActivity.this,"Completed",Toast.LENGTH_SHORT).show();
}




catch (IOException e)
{

e.printStackTrace();
}

finally {
    try {
        if (fos != null) fos.close();
        if (bis != null) bis.close();
       // if (s != null) s.close();
    }

    catch (IOException e)
    {
        e.printStackTrace();

    }
}


            return null;
        }
    }
}
