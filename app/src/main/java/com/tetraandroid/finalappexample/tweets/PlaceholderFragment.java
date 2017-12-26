package com.tetraandroid.finalappexample.tweets;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tetraandroid.retrofitexample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PlaceholderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String RESULT = "result";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.listActivity_rootView)
    ViewGroup rootView;
    private Unbinder unbinder;

    private ListAdapter listAdapter;
    private List<ViewModel> resultList = new ArrayList<>();

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(List<ViewModel> resultList) {
        PlaceholderFragment placeholderFragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RESULT, (ArrayList<ViewModel>) resultList);
        placeholderFragment.setArguments(bundle);
        return placeholderFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            resultList = args.getParcelableArrayList(RESULT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_placeholder, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        listAdapter = new ListAdapter(getActivity(), resultList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
}
