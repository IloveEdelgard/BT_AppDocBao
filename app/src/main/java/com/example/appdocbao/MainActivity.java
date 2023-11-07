package com.example.appdocbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    ListView lvnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvnews = findViewById(R.id.lv_news);

        new ReadRSS().execute("https://vnexpress.net/rss/tin-noi-bat.rss");

        lvnews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("linkTinTuc", ((RSSItem) parent.getItemAtPosition(position)).getLink());
                startActivity(intent);
            }
        });
    }


    private class ReadRSS extends AsyncTask<String, Void, ArrayList<RSSItem>> {

        @Override
        protected ArrayList<RSSItem> doInBackground(String... strings) {
            ArrayList<RSSItem> rssItems = new ArrayList<>();
            try {
                URL url = new URL(strings[0]);

                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                StringBuilder content = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }

                bufferedReader.close();

                XMLDOMParser parser = new XMLDOMParser();
                Document document = parser.getDocument(content.toString());
                NodeList nodeList = document.getElementsByTagName("item");

                String description = "", imgURL = "";
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    Node descriptionNode = element.getElementsByTagName("description").item(0);
                    if (descriptionNode != null) {
                        String descriptionContent = descriptionNode.getTextContent();
                        Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                        Matcher matcher = pattern.matcher(descriptionContent);
                        if (matcher.find()) {
                            imgURL = matcher.group(1);
                        }
                        description = descriptionContent.replaceAll("\\<.*?\\>", "");
                    }

                    String title = parser.getValue(element, "title");
                    String link = parser.getValue(element, "link");
                    String pubDate = parser.getValue(element, "pubDate");

                    RSSItem rssItem = new RSSItem(title, description, pubDate, imgURL, link);
                    rssItems.add(rssItem);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rssItems;
        }

        @Override
        protected void onPostExecute(ArrayList<RSSItem> rssItems) {
            super.onPostExecute(rssItems);

            ListAdapter adapter = new ListAdapter(MainActivity.this, rssItems);
            lvnews.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}