package com.example.magda.systeminformacyjny.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;
import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.activities.LoginActivity;
import com.example.magda.systeminformacyjny.base.BaseFragment;
import com.example.magda.systeminformacyjny.base.Lifecycle;
import com.example.magda.systeminformacyjny.databinding.FragmentCreateRouteBinding;
import com.example.magda.systeminformacyjny.models.MainPlace;
import com.example.magda.systeminformacyjny.network.ErrorResponse;
import com.example.magda.systeminformacyjny.network.SuccessResponse;
import com.example.magda.systeminformacyjny.utils.RecyclerViewMainPlacesAdapter;
import com.example.magda.systeminformacyjny.view_models.FragmentCreateRouteViewModel;
import java.util.ArrayList;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static android.app.Activity.RESULT_OK;
import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD;
import static com.example.magda.systeminformacyjny.utils.Constants.CURRENT_ROAD_ACTIVITY_REQUEST_CODE;

/**
 * Created by piotrek on 10.04.17.
 */

public class CreateRouteFragment extends BaseFragment {

    private ArrayList<MainPlace> currentRoad;
    private static final String MAIN_PLACES_TAG = "currentRoad";
    private FragmentCreateRouteViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerViewMainPlacesAdapter recyclerViewAdapter;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            currentRoad = (ArrayList<MainPlace>) savedInstanceState.getSerializable(MAIN_PLACES_TAG);
        } else {
            currentRoad = new ArrayList<>();
            currentRoad.add(null);
        }

    }

    public static CreateRouteFragment getInstance() {
        return new CreateRouteFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MAIN_PLACES_TAG, currentRoad);
    }

    public void removeItem(int index) {
        recyclerViewAdapter.notifyItemRemoved(index);
        if(currentRoad.isEmpty()) {
            currentRoad.add(null);
            recyclerViewAdapter.notifyItemInserted(0);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCreateRouteBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_route, null, false);
        viewModel = new FragmentCreateRouteViewModel(this);
        viewModel.setCurrentRoad(currentRoad);
        binding.setViewModel(viewModel);
        recyclerView = binding.recyclerView;
        setUpRecyclerView();
        return binding.getRoot();
    }

    private void setUpRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewMainPlacesAdapter(currentRoad, viewModel, viewModel, getContext(),
                true);
        SlideInRightAnimator itemAnimation = new SlideInRightAnimator(new AccelerateInterpolator());
        recyclerView.setItemAnimator(itemAnimation);
        layoutManager.scrollToPosition(0);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(SuccessResponse successResponse) {
        hideProgressDialog();
    }

    @Override
    public void onError(ErrorResponse errorResponse) {
        hideProgressDialog();
        showToast(getString(R.string.send_route_dialog_error));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CURRENT_ROAD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                currentRoad.clear();
                currentRoad.addAll((ArrayList<MainPlace>) data.getSerializableExtra(CURRENT_ROAD));
                if (currentRoad.size() == 0)
                    currentRoad.add(null);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return viewModel;
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.send_route_dialog_title));
        progressDialog.setMessage(getString(R.string.send_route_dialog_info));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminateDrawable(ContextCompat.getDrawable(getContext(),
                R.drawable.progress_bar_circle));
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.hide();
    }
}
