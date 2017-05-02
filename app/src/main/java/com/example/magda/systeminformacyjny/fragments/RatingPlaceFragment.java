package com.example.magda.systeminformacyjny.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.base.BaseFragment;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.databinding.FragmentRatingPlaceBinding;
import com.example.magda.systeminformacyjny.models.Comment;
import com.example.magda.systeminformacyjny.models.IPlaceItem;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.example.magda.systeminformacyjny.utils.RecyclerViewCommentsAdapter;
import com.example.magda.systeminformacyjny.view_models.FragmentRatingPlaceViewModel;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.example.magda.systeminformacyjny.network.ErrorResponse.DOWNLOAD_ERROR;
import static com.example.magda.systeminformacyjny.network.ErrorResponse.SEND_OPINION_ERROR;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.DOWNLOAD_SUCCESS;
import static com.example.magda.systeminformacyjny.network.SuccessResponse.SEND_OPINION_SUCCESS;
import static com.example.magda.systeminformacyjny.utils.Constants.FULL_SCREEN_PROGRESS_BAR;

/**
 * Created by JB on 2017-04-09.
 */

public class RatingPlaceFragment extends BaseFragment {

    private IPlaceItem iPlaceItem;
    private RecyclerView recyclerView;
    private RecyclerViewCommentsAdapter recyclerAdapter;
    private ArrayList<Comment> comments;
    private FragmentRatingPlaceViewModel viewModel;
    private String placeType;

    private static final String PLACE_TAG = "place";
    private static final String PLACE_TYPE_TAG = "placeType";
    private static final String ADD_INFORMATION = "Dodano opinię!";
    private static final String ERROR_INFO = "Brak połączenia!";

    public static RatingPlaceFragment getInstance(IPlaceItem iPlaceItem, String placeType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PLACE_TAG, iPlaceItem);
        bundle.putSerializable(PLACE_TYPE_TAG, placeType);
        RatingPlaceFragment ratingPlaceFragment = new RatingPlaceFragment();
        ratingPlaceFragment.setArguments(bundle);
        return ratingPlaceFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iPlaceItem = (IPlaceItem) getArguments().getSerializable(PLACE_TAG);
        this.placeType = getArguments().getString(PLACE_TYPE_TAG);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
                                  Bundle savedInstanceState) {
        FragmentRatingPlaceBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating_place, null, false);
        binding.setPlace(iPlaceItem);
        binding.addOpinionButton.setOnClickListener(v ->  {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            DialogFragment dialogFragment = AddOpinionDialogFragment.getInstance();
            dialogFragment.show(fragmentTransaction, "dialog");
        });
        recyclerView = binding.recyclerView;
        this.comments = new ArrayList<>();
        comments.add(new Comment(FULL_SCREEN_PROGRESS_BAR));
        this.viewModel = new FragmentRatingPlaceViewModel(this);
        viewModel.setComments(comments);
        viewModel.setPlaceItem(iPlaceItem);
        viewModel.setPlaceType(placeType);
        setUpRecyclerView();
        viewModel.downloadComments();
        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerViewCommentsAdapter(recyclerView, comments,
                false, null, viewModel);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(SuccessResponse successResponse) {
        if(successResponse.getSuccessType() == DOWNLOAD_SUCCESS) {
            recyclerAdapter.notifyDataSetChanged();
        }else if(successResponse.getSuccessType() == SEND_OPINION_SUCCESS) {
            showToast(ADD_INFORMATION);
        }else {
            throw new IllegalArgumentException("Wrong success type " + successResponse.getSuccessType());
        }
    }

    @Override
    public void onError(ErrorResponse errorResponse) {
        if(errorResponse.getErrorType() == DOWNLOAD_ERROR) {
            recyclerAdapter.notifyDataSetChanged();
            showToast(ERROR_INFO);
        }else if(errorResponse.getErrorType() == SEND_OPINION_ERROR) {
            showToast(ERROR_INFO);
        }else {
            throw new IllegalArgumentException("Wrong error type " + errorResponse.getErrorType());
        }
    }

    public void recyclerViewNotify() {
        recyclerAdapter.notifyDataSetChanged();
    }
}
