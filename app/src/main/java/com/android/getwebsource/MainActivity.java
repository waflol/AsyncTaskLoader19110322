package com.android.getwebsource;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.StrictMode;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView tv;
    Spinner sp;
    EditText Getinput;
    String pre = "http://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        sp = (Spinner) findViewById(R.id.planets_spinner);
        Getinput = (EditText) findViewById(R.id.EditText);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
        tv.setText("Page source will appear here");
    }
    public String getWebsite(String website){
        String resString ="";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(website);
        try{
            HttpResponse response;
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"windows-1251"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine())!=null){
                sb.append(line+"\n");
                resString = sb.toString();
                is.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            //Toast.makeText(MainActivity.this,"Error!",Toast.LENGTH_SHORT).show();
        }
        return resString;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String pre = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),pre,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        String pre = "http://";
    }

    public void GetSource(View view) {
        String url = pre+Getinput.getText().toString();
        try{
            tv.setText(getWebsite(url));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}