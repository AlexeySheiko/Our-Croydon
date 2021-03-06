package com.lbcinternal.sensemble.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.lbcinternal.sensemble.CroydonApp;
import com.lbcinternal.sensemble.CroydonApp.TrackerName;
import com.lbcinternal.sensemble.R;
import com.lbcinternal.sensemble.rest.ApiService;
import com.lbcinternal.sensemble.rest.RestClient;
import com.lbcinternal.sensemble.rest.model.Comment;
import com.lbcinternal.sensemble.rest.model.IdeaDetails;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        bar.setDisplayShowTitleEnabled(false);

        View abView = LayoutInflater.from(this).inflate(R.layout.actionbar_details, null);
        bar.setCustomView(abView);


        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);

        final String section = sp.getString("section", "");
        if (section.equals("ideas")) {
            findViewById(R.id.feedback_container).setVisibility(View.VISIBLE);
        }

        String title = sp.getString("ideaTitle", "");
        final String date = sp.getString("ideaDate", "");
        String body = sp.getString("ideaBody", "");

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        final TextView dateTextView = (TextView) findViewById(R.id.date);
        if (!section.equals("ideas")) {
            dateTextView.setText(date);
        }

        if (!section.equals("ideas")) {
            TextView bodyTextView = (TextView) findViewById(R.id.body);
            bodyTextView.setText(body);
        } else {
            String id = sp.getString("ideaId", "");
            ApiService service = new RestClient(this, "yyyy-MM-dd'T'HH:mm:ss").getApiService();
            service.getIdeaDetails(id, new Callback<IdeaDetails>() {
                @Override public void success(IdeaDetails ideaDetails, Response response) {
                    TextView bodyTextView = (TextView) findViewById(R.id.body);
                    bodyTextView.setText(ideaDetails.getBody());
                    Log.d("DetailActivity", "Idea body: " + ideaDetails.getBody());

                    TextView scoreTextView = (TextView) findViewById(R.id.rating);
                    scoreTextView.setText(ideaDetails.getRating() + " / 5");

                    if (section.equals("ideas")) {
                        dateTextView.setText(String.format(
                                "Idea by %s on %s",
                                ideaDetails.getAuthor(),
                                date));
                    }
                }

                @Override public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });

            service = new RestClient(this, "yyyy-MM-dd HH:mm").getApiService();
            service.listComments(id, new Callback<List<Comment>>() {
                @Override
                public void success(List<Comment> comments, Response response) {
                    TextView countTextView = (TextView) findViewById(R.id.comments_count);
                    countTextView.setText(comments.size() + "");
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }

        sendSessionInfo();
    }

    public void viewComments(View view) {
        startActivity(new Intent(this, CommentsActivity.class));
    }

    private void sendSessionInfo() {
        Tracker tracker = ((CroydonApp) getApplication()).getTracker(
                TrackerName.APP_TRACKER);
        tracker.setScreenName(getString(R.string.viewPostScreen));
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }
}
