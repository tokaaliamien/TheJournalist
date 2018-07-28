package com.example.android.thejournalist.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.thejournalist.Fragments.DetailsFragment;
import com.example.android.thejournalist.Models.News;
import com.example.android.thejournalist.R;

public class DetailsActivity extends AppCompatActivity {
    private final String LOG_TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        News news = (News) getIntent().getExtras().get("news");
        //Helper.displayToast(this,news.getId());
        if (savedInstanceState == null) {

            DetailsFragment detailFragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("news", news);
            detailFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_details, detailFragment)
                    .commit();
        }


        //News news = (Movie) getIntent().getSerializableExtra("movie");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
