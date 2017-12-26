package com.tetraandroid.finalappexample.tweets;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.tetraandroid.finalappexample.TweetUtil;
import com.tetraandroid.finalappexample.http.AuthenticateEvent;
import com.tetraandroid.finalappexample.http.Communicator;
import com.tetraandroid.finalappexample.http.ErrorEvent;
import com.tetraandroid.finalappexample.http.ServerEvent;
import com.tetraandroid.finalappexample.http.apimodel.ServerResponse;
import com.tetraandroid.finalappexample.root.App;
import com.tetraandroid.retrofitexample.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tetraandroid.finalappexample.TweetUtil.HASH_TAG;
import static com.tetraandroid.finalappexample.TweetUtil.RESULT;

public class TwitterTweetsActivity extends AppCompatActivity implements TopHashTagFragment.HashTagClickToActivity {

    private final String TAG = TwitterTweetsActivity.class.getName();

    public static final int TWEETS_PAGE = 0;
    public static final int TOP_10 = 1;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs_sort_filter)
    TabLayout mTabs;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    HashSet<String> uniqueHashTag;
    Communicator communicator;
    private ProgressDialog dialog;
    private List<ViewModel> resultList;
    private List<ViewModel> tagList = new ArrayList<>();
    private List<HashViewModel> hashResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweets_activity);

        ((App) getApplication()).getComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Tweets");
        dialog.setMessage("Please wait");
        dialog.show();
        resultList = new ArrayList<>();
        hashResultList = new ArrayList<>();
        communicator = new Communicator();
        communicator.loginPost();
    }

    private void initViews() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabs.setupWithViewPager(mViewPager);
    }

    @Subscribe
    public void onServerEvent(ServerEvent serverEvent) {
        dialog.dismiss();
        if (serverEvent.getServerResponse() != null) {
            uniqueHashTag = new HashSet<>();
            for (ServerResponse serverResponse : serverEvent.getServerResponse()) {
                if (serverResponse.getText().contains("#")) {
                    String str = TweetUtil.getMeHashTag(serverResponse.getText());
                    uniqueHashTag.add(str);
                }
                ViewModel viewModel = new ViewModel();
                viewModel.setContent(serverResponse.getText());
                viewModel.setTimeCreated(serverResponse.getTimeStamp());
                viewModel.setUserName(serverResponse.getUser().getUserName());
                viewModel.setUserProfileImage(serverResponse.getUser().getImageProfile());
                updateData(viewModel);
            }
            getTopHashTag((HashSet) uniqueHashTag);
            initViews();
        }
    }

    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);
    }

    public void updateTag(ViewModel viewModel) {
        tagList.add(viewModel);
    }

    public void updateHashData(String str) {
        HashViewModel hashViewModel = new HashViewModel();
        hashViewModel.setContent(str);
        hashResultList.add(hashViewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onServerEvent(AuthenticateEvent authenticateEvent) {
        if (authenticateEvent.getServerResponse() != null) {
            if (authenticateEvent != null && authenticateEvent.getServerResponse().getToken_type().equals("bearer")) {
                Log.i("Anshul", "Inside to hit loginGet");
                communicator.loginGet(authenticateEvent.getServerResponse().getAccess_token());
            }
        }
    }

    @Subscribe
    public void onErrorEvent(ErrorEvent errorEvent) {
        dialog.dismiss();
        Log.i("Anshul", "Error TMA==" + errorEvent.getErrorMsg());
    }

    Fragment fragment;

    public void getTopHashTag(HashSet<String> item) {
        for (String str : item) {
            updateHashData(str);
        }
    }

    @Override
    public void hashTagClickItemToActivity(String str) {
        for (ViewModel viewModel : resultList) {
            if (viewModel.getContent().contains(str)) {
                updateTag(viewModel);
            }
        }
        Intent intent = new Intent(this, HashTagDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RESULT, (ArrayList<? extends Parcelable>) tagList);
        bundle.putString(HASH_TAG, str);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case TWEETS_PAGE:
                    return "Tweets";
                case TOP_10:
                    return "Top 10";
                default:
                    break;
            }
            return "";
        }

        private Fragment initTopHashFragment() {
            fragment = getFlightFilterByPosition(TOP_10);
            if (fragment == null) {
                Log.i("Agarwal", "fragment called");
                fragment = TopHashTagFragment.newInstance(hashResultList);
            }
            return fragment;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case TWEETS_PAGE:
                    return PlaceholderFragment.newInstance(resultList);
                case TOP_10:
                    return initTopHashFragment();
                default:
                    break;
            }
            return null;
        }

        private Fragment getFlightFilterByPosition(int position) {
            return getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
