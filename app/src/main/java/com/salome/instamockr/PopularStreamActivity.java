package com.salome.instamockr;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.salome.instamockr.adapter.InstagramPostAdapter;
import com.salome.instamockr.model.InstagramPost;
import com.salome.instamockr.utility.InstagramHttpClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PopularStreamActivity extends ActionBarActivity {

    private SwipeRefreshLayout swipeContainer;
    private InstagramPostAdapter postsAdapter;
    private ArrayList<InstagramPost> postList;
    private ListView lvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_stream);
        //set up the swipe refresh
        setupSwipeRefresh();
        //kick off the asyn task to download the api result
        getPostsData(false);
        // get the listview
        lvPosts = (ListView) findViewById(R.id.lvPosts);
        //instantiate a new array list of posts
        postList= new ArrayList<InstagramPost>();
        // initialize the posts adapter
        postsAdapter= new InstagramPostAdapter(this, postList);
        //Bind the listview to the adapter
        lvPosts.setAdapter(postsAdapter);
        
    }

    private void setupSwipeRefresh() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void fetchTimelineAsync(int page) {
        getPostsData(true);
    }
    
    private void getPostsData(final boolean isRefresh) {
        InstagramHttpClient.get("media/popular?client_id=40743279c17448fca1ca36feeb4e9633", null, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                System.out.println("well lets see what we got");
                try {
                    JSONArray data = response.getJSONArray("data");
                    ArrayList<InstagramPost> myposts = InstagramPost.fromJson(data);

                    if (isRefresh) {
                        postsAdapter.clear();
                    }

                    postsAdapter.addAll(myposts);

                    if (isRefresh) {
                        swipeContainer.setRefreshing(false);
                        postsAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Updated Posts", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                /* Not used*/
                // Pull out the first event on the public timeline
                JSONObject firstEvent = null;
                try {
                    firstEvent = (JSONObject) timeline.get(0);
                    String tweetText = firstEvent.getString("text");
                    // Do something with the response
                    System.out.println(tweetText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("hello");
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println("hello");
                // called when request is retried
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_stream, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
