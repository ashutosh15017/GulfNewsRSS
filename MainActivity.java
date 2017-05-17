package com.ashuguy.gulfnewsrss;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String fileContents;
    private ListView newsList;
    private Button btnParse;
    private ArrayList<News> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnParse = (Button) findViewById(R.id.btnParse);
        newsList = (ListView) findViewById(R.id.newsList);
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://gulfnews.com/cmlink/1.734148");

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseNews parseNews = new ParseNews(fileContents);
                parseNews.process();
                news = parseNews.getNews();
                ArrayAdapter<News> arrayAdapter = new ArrayAdapter<News>
                        (MainActivity.this,R.layout.list_item,news);
                newsList.setAdapter(arrayAdapter);


                // if click on news load the link!
                newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int position = i;
                        News value = (News) newsList.getItemAtPosition(position);
//                        Toast.makeText(MainActivity.this,"Title: " + value.getLink(),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this,OpenLink.class);
                        intent.putExtra("URL",value.getLink());
                        startActivity(intent);




                    }
                });
            }
        });

    }



    private class DownloadData extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            fileContents = downloadXML(strings[0]);
            if (fileContents == null)
            {
                Log.d("DownD","Error downloading");
            }

            return fileContents;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("DownD","Error Downloading, result = "+s);


        }

        private String downloadXML (String urlPath)
        {
            StringBuilder buffer = new StringBuilder();
            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("DownD","response="+response);

                // to read the file
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char [] inputBuffer = new char[500];    // read file 500 bytes at a time
                while (true)
                {
                    charRead = isr.read(inputBuffer);
                    if (charRead<1)
                    {
                        break;
                    }

                    buffer.append(String.valueOf(inputBuffer,0,charRead));

                }

                return buffer.toString();

            } catch (IOException e){
                Log.d("DownD","IOException"+e.getMessage());
            } catch (SecurityException e){
                Log.d("DownD","Security"+e.getMessage());
            }

            return null;

        }
    }
}


