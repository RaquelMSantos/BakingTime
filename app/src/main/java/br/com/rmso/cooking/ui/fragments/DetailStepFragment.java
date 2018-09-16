package br.com.rmso.cooking.ui.fragments;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.models.Step;
import br.com.rmso.cooking.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Raquel on 07/09/2018.
 */

public class DetailStepFragment extends Fragment {

    private Step mStep;
    private Recipe mRecipe;
    private int mIndex;
    private int idStep;
    private Uri uri;
    private SimpleExoPlayer mExoplayer;
    private long mPlayerStartPosition = 0;
    private boolean mPlayerPlayWhenReady = true;

    @BindView(R.id.tv_name_step)
    TextView mNameStepTextView;
    @BindView(R.id.tv_description_step)
    TextView mDescriptionTextView;
    @BindView(R.id.exo_play)
    PlayerView mPlayerView;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;

    public static DetailStepFragment newInstance(Recipe recipe, int position) {
        DetailStepFragment detailStepFragment = new DetailStepFragment();
        Bundle args = new Bundle();
        args.putParcelable(Utility.RECIPE, recipe);
        args.putInt(Utility.INDEX, position);
        detailStepFragment.setArguments(args);

        return detailStepFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        int smallestScreenWidthDp = configuration.smallestScreenWidthDp;

        if (smallestScreenWidthDp >= 600){
           Utility.isTablet = true;
        }else {
            Utility.isTablet = false;
        }

        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(Utility.RECIPE);
            mIndex = savedInstanceState.getInt(Utility.INDEX);
            mStep = mRecipe.getSteps().get(mIndex);
        }else {
            mRecipe = getArguments().getParcelable(Utility.RECIPE);
            mIndex = getArguments().getInt(Utility.INDEX);
            mStep = mRecipe.getSteps().get(mIndex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_step, container, false);
        ButterKnife.bind(this, view);

        Step step = mRecipe.getSteps().get(mIndex);
        mNameStepTextView.setText(step.getShortDescription());
        mDescriptionTextView.setText(step.getDescription());


        if (savedInstanceState != null){
            mPlayerStartPosition = savedInstanceState.getLong(Utility.KEY_PLAYER_POSITION, mPlayerStartPosition);
            mPlayerPlayWhenReady = savedInstanceState.getBoolean(Utility.KEY_PLAYER_PLAY_STATE, mPlayerPlayWhenReady);
        }

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_previous:
                    if (mIndex == 0) {
                        Toast.makeText(getActivity(), "First Step", Toast.LENGTH_SHORT).show();
                    }else {
                        mIndex--;
                        loadData();
                        initializePlayer(Uri.parse(mStep.getVideoUrl()));
                    }
                    break;
                case R.id.action_next:
                    if (mIndex == mRecipe.getSteps().size() -1){
                        Toast.makeText(getActivity(), "Last Step", Toast.LENGTH_SHORT).show();
                    }else {
                        mIndex++;
                        loadData();
                        initializePlayer(Uri.parse(mStep.getVideoUrl()));
                    }
                    break;
            }

            return true;
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mExoplayer != null) {
            outState.putLong(Utility.KEY_PLAYER_POSITION, mExoplayer.getContentPosition());
            outState.putBoolean(Utility.KEY_PLAYER_PLAY_STATE, mExoplayer.getPlayWhenReady());
        }
        outState.putParcelable(Utility.RECIPE, mRecipe);
        outState.putInt(Utility.INDEX, mIndex);
    }

    private void initializePlayer(Uri uri) {
        if (mExoplayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoplayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        }
        mPlayerView.setPlayer(mExoplayer);

        String userAgent = Util.getUserAgent(getContext(), "Cooking");
        MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

        mExoplayer.prepare(mediaSource);
        mExoplayer.seekTo(mPlayerStartPosition);
        mExoplayer.setPlayWhenReady(mPlayerPlayWhenReady);
    }

    private void releasePlayer() {
        if (mExoplayer != null) {
            mExoplayer.stop();
            mExoplayer.release();
            mExoplayer = null;
        }
    }

    private void loadData() {
        mStep = Utility.recipeList.get(Utility.currentRecipe).getSteps().get(mIndex);
        mNameStepTextView.setText(mStep.getShortDescription());
        mDescriptionTextView.setText(mStep.getDescription());
        uri = Uri.parse(mStep.getVideoUrl());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExoplayer != null){
            mExoplayer.stop();
            mExoplayer.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExoplayer != null){
            mExoplayer.stop();
            mExoplayer.release();
            mExoplayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }


    @Override
    public void onResume() {
        super.onResume();
        mExoplayer = null;
        if (!TextUtils.isEmpty(mStep.getVideoUrl())){
            initializePlayer(Uri.parse(mStep.getVideoUrl()));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        super.onStop();
        if (mExoplayer!=null) {
            mExoplayer.stop();
            mExoplayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoplayer != null){
            mPlayerPlayWhenReady = mExoplayer.getPlayWhenReady();
            mPlayerStartPosition = mExoplayer.getCurrentPosition();
        }
        releasePlayer();
    }
}
