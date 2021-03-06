package com.myapplication.webbrowser;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private EditText url;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView = findViewById(R.id.browse);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
//        ws.setAllowContentAccess(true);
//        ws.setAllowFileAccess(true);
//        ws.setBuiltInZoomControls(true);
//        ws.setJavaScriptCanOpenWindowsAutomatically(true);
//        ws.setLoadsImagesAutomatically(true);
//        ws.setAllowContentAccess(true);


        url = findViewById(R.id.url);
        Button ok = findViewById(R.id.button);
        ok.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            try {
                final URL uri = new URL(url.getText().toString());
//                final URL uri = new URL("http://10.65.26.10");
                final Handler handler = new Handler();

                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        try {
                            HttpURLConnection urlConnection;
                            urlConnection = (HttpURLConnection) uri.openConnection();

                            String encoded = Base64.getEncoder().encodeToString(("operator:password").getBytes(StandardCharsets.UTF_8));  //Java 8
                            urlConnection.setRequestProperty("Authorization", "Basic "+encoded);
                            urlConnection.setRequestMethod("GET");
                            urlConnection.setConnectTimeout(10000);
                            urlConnection.connect();

                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        final String result = in.lines().collect(Collectors.joining("\n"));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                webView.loadData(result, "text/html; charset=utf-8", "utf-8");
                                Log.d("auth", result); //Смотрим, что получаем в качестве результата result
                            }
                        });
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    };
}
