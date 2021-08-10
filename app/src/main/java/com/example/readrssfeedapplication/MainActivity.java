package com.example.readrssfeedapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.readrssfeedapplication.Adapter.FeedAdapter;
import com.example.readrssfeedapplication.Common.HTTPDataHandler;
import com.example.readrssfeedapplication.Model.RSSObject;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {



    RecyclerView recyclerView;
    RSSObject rssObject;


    private  final String RSS_link="https://rss.nytimes.com/services/xml/rss/nyt/Science.xml";
    private final String RSS_to_API=" https://api.rss2json.com/v1/api.json?rss_url=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=(RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayout);

        
        loadRSS();
    }

    private void loadRSS() {
        AsyncTask<String,String,String> loadRSSAsync=new AsyncTask<String, String, String>() {

            ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                String result;
                HTTPDataHandler http=new HTTPDataHandler();
                result=http.GetHTTPData(params[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                rssObject=new Gson().fromJson(s,RSSObject.class);
                FeedAdapter adapter=new FeedAdapter(rssObject,getBaseContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        StringBuilder url_get_data=new StringBuilder(RSS_to_API);
        url_get_data.append(RSS_link);
        loadRSSAsync.execute(url_get_data.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        if (item.getItemId()==R.id.menu_refresh)
            loadRSS();
        return true;
    }
}