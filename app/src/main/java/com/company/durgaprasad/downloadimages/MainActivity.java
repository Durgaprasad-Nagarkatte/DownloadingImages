package com.company.durgaprasad.downloadimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button downloadImageButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadImageButton = (Button) findViewById(R.id.downloadImageButton);
        imageView = (ImageView) findViewById(R.id.imageView);

        downloadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDownloader task = new ImageDownloader();
                try {
                    Bitmap myImage = task.execute("https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png").get();
                    imageView.setImageBitmap(myImage);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return(bitmap);

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
