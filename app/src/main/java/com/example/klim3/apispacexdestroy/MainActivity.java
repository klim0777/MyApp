package com.example.klim3.apispacexdestroy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    // url for request
    private String url = "https://api.spacexdata.com/v2/launches?launch_year=2017";
    private String urlBase = "https://api.spacexdata.com/v2/launches?launch_year=";

    private String[] launchYears = {"2006", "2007", "2008", "2009", "2010",
                                    "2011", "2012", "2013", "2014", "2015",
                                    "2016", "2017", "2018",};


    private ProgressDialog dialog;
    private List<Item> array = new ArrayList<>();
    private ListView listView;
    private Adapter adapter;
    private Spinner spinner;

    private Button getButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listItem);
        getButton = (Button) findViewById(R.id.getButton);
        spinner = (Spinner) findViewById(R.id.spinner);

        // adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                launchYears);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);
        spinner.setPrompt("Title");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view,
                                      int position, long id) {
               String urlAdd = String.valueOf(position+2006);
               url = urlBase + urlAdd;

           }
           @Override
           public void onNothingSelected(AdapterView<?> arg0) {
           }

        });

        // adapter for List<item> array
        adapter = new Adapter(this,array);

        listView.setAdapter(adapter);

        final Context context = this;

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(context);
                dialog.setMessage("Loading...");
                dialog.show();

                array.clear();

                // request jsonArray which contains JSONObjects
                // one object contains info about one rocket launch
                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(
                    url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            hideDialog();
                            // getting info and save
                            for(int i=0;i<response.length();i++){
                                try{

                                    // launch number i
                                    JSONObject launch =response.getJSONObject(i);

                                    // get details
                                    String details = launch.getString("details");

                                    // get JSONObject "links", which contains
                                    // our imageLink and article_link
                                    JSONObject links = launch.getJSONObject("links");
                                    String imageLink = links.getString("mission_patch");
                                    String articleLink = links.getString("article_link");

                                    // rocket JSONObject contains our rocketName
                                    JSONObject rocket = launch.getJSONObject("rocket");
                                    String rocketName = rocket.getString("rocket_name");

                                    // getting unix launch date and converting to long
                                    String launchDateUnix = launch.getString("launch_date_unix");
                                    long launchTime = Long.parseLong(launchDateUnix);

                                    // converting to miliseconds
                                    Date date = new Date(launchTime * 1000L);
                                    // date-time format
                                    SimpleDateFormat dateFormat =
                                                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    // setting timeZone
                                    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+3"));
                                    // formating date to dateFormat
                                    String formattedLaunchDate = dateFormat.format(date);

                                    // saving data to item
                                    Item item  = new Item();
                                    item.setRocketName(rocketName);
                                    item.setLaunchDate(formattedLaunchDate);
                                    item.setDetails(details);
                                    item.setImage(imageLink);
                                    item.setArticleLink(articleLink);

                                    // add item to our array
                                    array.add(item);

                                } catch(JSONException ex){
                                    ex.printStackTrace();
                                }
                            }

                            // data saved, notify adapter
                            adapter.notifyDataSetChanged();

                        }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("My log", "Response error");
                        }
                }
                );//request end

                AppController.getInstance().addToRequesQueue(jsonArrayRequest);


            }// onClick end
        });// getButton OnClickListener end

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String articleLink = array.get(i).getArticleLink();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleLink));
                startActivity(browserIntent);

            }
        });

    }// onCreateEnd

    public void hideDialog() {
        if(dialog != null){
            dialog.dismiss();
            dialog=null;
        }
    }

}
