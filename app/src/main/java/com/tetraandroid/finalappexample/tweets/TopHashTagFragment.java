package com.tetraandroid.finalappexample.tweets;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tetraandroid.retrofitexample.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.tetraandroid.finalappexample.TweetUtil.RESULT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TopHashTagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopHashTagFragment extends Fragment implements HashListAdapter.HashTagClick {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootView)
    ViewGroup rootView;
    private Unbinder unbinder;

    private List<HashViewModel> resultList;
    private HashListAdapter listAdapter;
    Set<String> uniqueHashTag;
    private HashTagClickToActivity mListener;

    public TopHashTagFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TopHashTagFragment newInstance(List<HashViewModel> resultList) {
        TopHashTagFragment fragment = new TopHashTagFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(RESULT, (ArrayList) resultList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HashTagClickToActivity) {
            mListener = (HashTagClickToActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            resultList = args.getParcelableArrayList(RESULT);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_placeholder, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        listAdapter = new HashListAdapter(this, getActivity(), resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void hashTagClickItem(String str) {
        if (mListener != null) {
            mListener.hashTagClickItemToActivity(str);
        }
    }

    public interface HashTagClickToActivity {
        public void hashTagClickItemToActivity(String str);
    }
}
