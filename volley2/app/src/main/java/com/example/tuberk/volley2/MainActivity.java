package com.example.tuberk.volley2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private TextView mTextViewResult;
   // private TextView mTextViewResult2;
    private RequestQueue mQueue;
    String item="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult=findViewById(R.id.text_view_result);
       // mTextViewResult2=findViewById(R.id.text_view_result2);
        Button buttonParse=findViewById(R.id.button_parse);
        mQueue= Volley.newRequestQueue(this);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              item = parent.getItemAtPosition(position).toString();
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });


        List<String> categories = new ArrayList<>();
        categories.add("Ankara");
        categories.add("izmir");
        categories.add("istanbul");
        categories.add("Adana");
        categories.add("Trabzon");
        categories.add("Malatya");
        categories.add("Tokat");
        categories.add("Samsun");
        categories.add("Gaziantep");
        categories.add("Kocaeli");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse(){
        String url="http://api.openweathermap.org/data/2.5/weather?q="+item+",tr&APPID=c3c6ea07b65501dcece9bfc95e534102";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String hava;
                try{
                JSONArray jsonArray=response.getJSONArray("weather");

                JSONObject weather = jsonArray.getJSONObject(0);
                hava = weather.getString("description");

                JSONObject jsonObject=response.getJSONObject("main");
                String temp=String.valueOf(jsonObject.get("temp"));
                String temp_min=String.valueOf(jsonObject.get("temp_min"));
                String temp_max=String.valueOf(jsonObject.get("temp_max"));
                mTextViewResult.setText("Temparature (F):"+temp+"\n"+"Temparature_min (F):"+temp_min+"\n"+"Temparature_max (F):"+temp_max+"\n"+"Weather: "+hava);
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
mQueue.add(request);
    }
}
