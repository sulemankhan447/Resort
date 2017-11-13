package com.suleman.resort;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnGetToken;
    EditText getToken;
    RequestQueue requestQueue;
    TelephonyManager telephonyManager;
    private String server_url = "http://192.168.1.105/resort/getToken.php";
        TextView tvCount;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        btnGetToken = (Button) findViewById(R.id.btnGetToken);
        getToken = (EditText) findViewById(R.id.tokenText);*/
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tvCount =findViewById(R.id.tvCount);
/*        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToken.setText(FirebaseInstanceId.getInstance().getToken());
            }
        });*/
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
 //               Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            // no of bookings displayed
                tvCount.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("token_id", FirebaseInstanceId.getInstance().getToken());
                params.put("imei", telephonyManager.getDeviceId());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        Toast.makeText(MainActivity.this, telephonyManager.getDeviceId(), Toast.LENGTH_SHORT).show();
    }
}
