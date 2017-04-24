package com.example.magda.systeminformacyjny.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.magda.systeminformacyjny.databinding.EmptyInfoBinding;
import com.example.magda.systeminformacyjny.databinding.FullScreenProgressBarBinding;
import com.example.magda.systeminformacyjny.databinding.ProgressBarBinding;
import com.example.magda.systeminformacyjny.databinding.SomethingWrongInfoBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotrek on 2016-07-28.
 */
public abstract class AbstractRecyclerViewEndlessAdapter<T> extends RecyclerView.Adapter
        <RecyclerView.ViewHolder> {

    private final int VISIBLE_THRESHOLD = 5;
    private ArrayList<T> dataSet;
    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;
    private boolean loading = true;
    private RecyclerView recyclerView;
    private OnLoadMoreListener onLoadMore;
    private RecyclerView.OnScrollListener onScrollListener;
    //protected IErrorViewModel viewModel;

    protected static final int FULL_SCREEN_EMPTY_VIEW_HOLDER = 0;
    protected static final int NORMAL_VIEW_HOLDER = 1;
    protected static final int PROGRESS_BAR_VIEW_HOLDER = 2;
    protected static final int FULL_SCREEN_PROGRESS_BAR_VIEW_HOLDER = 3;
    protected static final int ERROR_INFO = 4;
    protected static final int REMOVED_ITEM_VIEW_HOLDER = 5;

    //private static final String EMPTY_INFO = "Jeszcze nic tu nie ma...";

    public AbstractRecyclerViewEndlessAdapter(RecyclerView recyclerView, ArrayList<T> dataSet,
                                              boolean scrollListener, final OnLoadMoreListener onLoadMoreListener) {
        //przypisanie listy z danymi
        this.dataSet = dataSet;
        this.recyclerView = recyclerView;
        this.onLoadMore = onLoadMoreListener;
        if (scrollListener)
            setOnScrollListener();
    }



    protected abstract IErrorViewModel getViewModel();

    public void setOnScrollListener() {
        //jeśli recyclerView ma ustawiony linearManager to dodaj listener dla endless scrolling
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            onScrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    //widoczne + niewidoczne elementy
                    totalItemCount = linearLayoutManager.getItemCount();
                    //widoczne elementy
                    visibleItemCount = linearLayoutManager.getChildCount();
                    //pierwszy widoczny element
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                    //jeśli zmieniła się długość listy to znów przygotuj listenera do odkrycia
                    if (loading) {
                        if (totalItemCount > previousTotal + 1) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    // jeśli odkryto koniec listy
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                        // jeśli dotarliśmy do końca
                        addItem(null);
                        if (onLoadMore != null) {
                            onLoadMore.onLoadMore();
                        }
                        loading = true;
                    }
                }
            };
        }
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public void addItem(T item) {
        if (!dataSet.contains(item)) {
            dataSet.add(item);
            notifyItemInserted(dataSet.size() - 1);
        }
    }

    public void removeItem(T item) {
        int index = dataSet.indexOf(item);
        if (index != -1) {
            dataSet.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void resetItems() {
        loading = true;
        firstVisibleItem = 0;
        visibleItemCount = 0;
        totalItemCount = 0;
        previousTotal = VISIBLE_THRESHOLD;
    }

    public void clearItems() {
        resetItems();
        dataSet.clear();
    }


    public void addEmptyItem() {
        if (!dataSet.contains(null)) {
            dataSet.add(null);
            notifyItemInserted(dataSet.indexOf(null));
        }
    }

    public List<T> getDataSet() {
        return dataSet;
    }

    @Override
    public abstract int getItemViewType(int position);

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FULL_SCREEN_EMPTY_VIEW_HOLDER) { // stworzenie prostego widoku (podstawowego)
            return onCreateEmptyViewHolder(parent, viewType);
        } else if (viewType == FULL_SCREEN_PROGRESS_BAR_VIEW_HOLDER) { //stworzenie progress na caly ekran
            return onCreateProgressBarFullScreen(parent, viewType);
        } else if (viewType == PROGRESS_BAR_VIEW_HOLDER) { // stworzenie progressBar na dole
            return onCreateProgressBarViewHolder(parent, viewType);
        } else if (viewType == NORMAL_VIEW_HOLDER) { // stworzenie pustego widoku - info
            return onCreateBasicItemViewHolder(parent, viewType);
        } else if (viewType == REMOVED_ITEM_VIEW_HOLDER) {
            return onCreateRemovedViewHolder(parent, viewType);
        } else if (viewType == ERROR_INFO) {
            return onCreateErrorInfoItemViewHolder(parent, viewType);
        } else {
            throw new IllegalStateException("Invalid type, this type ot items " + viewType + " can't be handled");
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        if (getItemViewType(position) == NORMAL_VIEW_HOLDER) { //połączenie podstawowego widoku
            onBindBasicItemView(genericHolder, position);
        } else if (getItemViewType(position) == PROGRESS_BAR_VIEW_HOLDER) { //połączenie z progress Bar na dole widoku
            onBindProgressBarViewHolder(genericHolder, position);
        } else if (getItemViewType(position) == FULL_SCREEN_PROGRESS_BAR_VIEW_HOLDER) {
            onBindFullScreenProgressBar(genericHolder, position);
        } else if (getItemViewType(position) == ERROR_INFO) {
            onBindErrorInfoItemViewHolder(genericHolder, position);
        } else if (getItemViewType(position) == REMOVED_ITEM_VIEW_HOLDER) {
            onBindRemovedViewHolder(genericHolder, position);
        } else { //połączenie pustego widoku
            onBindEmptyViewHolder(genericHolder, position);
        }
    }

    public RecyclerView.ViewHolder onCreateRemovedViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public void onBindRemovedViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        //DO NOTHING
    }

    public RecyclerView.ViewHolder onCreateErrorInfoItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
          SomethingWrongInfoBinding binding = SomethingWrongInfoBinding.inflate(inflater, parent, false);
        return new ErrorInfo(binding);
    }


    public void onBindErrorInfoItemViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
            ErrorInfo errorInfo = (ErrorInfo) genericHolder;
            errorInfo.bind(getViewModel());
    }

    public abstract RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindBasicItemView(RecyclerView.ViewHolder genericHolder, int position);

    public void onBindProgressBarViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
    }

    public void onBindEmptyViewHolder(RecyclerView.ViewHolder genericHolder, int position) {

    }

    public void onBindFullScreenProgressBar(RecyclerView.ViewHolder genericHolder, int position) {
    }


    public RecyclerView.ViewHolder onCreateProgressBarViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ProgressBarBinding progressBarBinding = ProgressBarBinding.inflate(inflater, parent, false);
        return new ProgressBarViewHolder(progressBarBinding);
    }

    public RecyclerView.ViewHolder onCreateEmptyViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        EmptyInfoBinding emptyInfoBinding = EmptyInfoBinding.inflate(inflater, parent, false);
        return new EmptyViewHolder(emptyInfoBinding);
    }

    public RecyclerView.ViewHolder onCreateProgressBarFullScreen(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FullScreenProgressBarBinding fullScreenProgressBarBinding = FullScreenProgressBarBinding
                .inflate(inflater, parent, false);
        return new FullScreenProgressBarViewHolder(fullScreenProgressBarBinding);
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(EmptyInfoBinding dataBinding) {
            super(dataBinding.getRoot());
        }

    }

    public class FullScreenProgressBarViewHolder extends RecyclerView.ViewHolder {

        public FullScreenProgressBarViewHolder(FullScreenProgressBarBinding dataBinding) {
            super(dataBinding.getRoot());
        }

    }

    public class ProgressBarViewHolder extends RecyclerView.ViewHolder {

        public ProgressBarViewHolder(ProgressBarBinding dataBinding) {
            super(dataBinding.getRoot());
        }
    }

    public class ErrorInfo extends RecyclerView.ViewHolder {
        public final SomethingWrongInfoBinding dataBinding;

        public ErrorInfo(SomethingWrongInfoBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
        }

        public void bind(IErrorViewModel viewModel) {
            dataBinding.setViewModel(viewModel);
            dataBinding.executePendingBindings();
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface IErrorViewModel {
        void refreshAfterDownloadError();
    }

}