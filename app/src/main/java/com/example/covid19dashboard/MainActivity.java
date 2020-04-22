package com.example.covid19dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    HashMap<String,String> myMap;
    Spinner states;
    TextView confirmed;
    TextView deaths;
    TextView recovered;
    TextView active;
    TextView state;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress= new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Fetching Data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

// To dismiss the dialog

        confirmed=(TextView)findViewById(R.id.textViewConfirmed);
        active=(TextView)findViewById(R.id.textViewActive);
        recovered=(TextView)findViewById(R.id.textViewRecovered);
        deaths=(TextView)findViewById(R.id.textViewDeaths);
        states=(Spinner)findViewById(R.id.spinner);
        //state=(TextView)findViewById(R.id.state);
        states.setSelection(20);
        myMap=new HashMap<String,String>();
        myMap.put("Andaman and Nicobar Islands","AN");
        myMap.put("Andhra Pradesh","AP");
        myMap.put("Telangana","TS");
        myMap.put("Arunachal Pradesh","AR");
        myMap.put("Assam","AS");
        myMap.put("Bihar","BH");
        myMap.put("Chandigarh","CH");
        myMap.put("Chattisgarh","CT");
        myMap.put("Dadra and Nagar Haveli","DN");
        myMap.put("Daman and Diu","DD");
        myMap.put("Delhi","DL");
        myMap.put("Goa","GA");
        myMap.put("Gujarat","GJ");
        myMap.put("Haryana","HR");
        myMap.put("Himachal Pradesh","HP");
        myMap.put("Jammu and Kashmir","JK");
        myMap.put("Jharkhand","JH");
        myMap.put("Karnataka","KA");
        myMap.put("Kerala","KL");
        myMap.put("Lakshadweep Islands","LD");
        myMap.put("Madhya Pradesh","MP");
        myMap.put("Maharashtra","MH");
        myMap.put("Manipur","MN");
        myMap.put("Meghalaya","ME");
        myMap.put("Mizoram","MI");
        myMap.put("Nagaland","NL");
        myMap.put("Odisha","OR");
        myMap.put("Pondicherry","PY");
        myMap.put("Punjab","PB");
        myMap.put("Rajasthan","RJ");
        myMap.put("Sikkim","SK");
        myMap.put("Tamil Nadu","TN");
        myMap.put("Telangana","TS");
        myMap.put("Tripura","TR");
        myMap.put("Uttar Pradesh","UP");
        myMap.put("Uttarakhand","UT");
        myMap.put("West Bengal","WB");

        states.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                progress.show();
                confirmed.setText("...");
                active.setText("...");
                recovered.setText("...");
                deaths.setText("...");
                sendReq(i);
                //Toast.makeText(MainActivity.this,"pkj",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

    }
    public void sendReq(int i)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
       // state.setText(states.getSelectedItem().toString());
        String url ="https://covid19india.p.rapidapi.com/getStateData/"+myMap.get(states.getSelectedItem());

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));
                        JSONObject data = null;
                        try {
                            data=response.getJSONObject("response");
                            confirmed.setText(data.getString("confirmed"));
                            active.setText(data.getString("active"));
                            recovered.setText(data.getString("recovered"));
                            deaths.setText(data.getString("deaths"));
                            progress.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-rapidapi-host", "covid19india.p.rapidapi.com");
                params.put("x-rapidapi-key", "e29748ae57msh1b2c87207917c00p1bd043jsn1cd850205d0c");

                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
