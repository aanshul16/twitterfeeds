package com.tetraandroid.finalappexample.tweets;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tetraandroid.retrofitexample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.tetraandroid.finalappexample.TweetUtil.HASH_TAG;
import static com.tetraandroid.finalappexample.TweetUtil.RESULT;

public class HashTagDetailActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootView)
    ViewGroup rootView;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar_title)
    TextView toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_placeholder);
        ButterKnife.bind(this);
        appBarLayout.setVisibility(View.VISIBLE);
        if (getIntent() != null && getIntent().getExtras() != null) {
            List<ViewModel> resultList = getIntent().getExtras().getParcelableArrayList(RESULT);
            toolbar.setText(getIntent().getExtras().getString(HASH_TAG));
            ListAdapter listAdapter = new ListAdapter(this, resultList);
            recyclerView.setAdapter(listAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

}
