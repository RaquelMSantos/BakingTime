package br.com.rmso.cooking.ui.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

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
import br.com.rmso.cooking.models.Step;
import br.com.rmso.cooking.utils.Utility;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailStepActivity extends AppCompatActivity {

    public static final String EXTRA_STEP = "extraStep";
    private Step step;
    private SimpleExoPlayer mExoplayer;
    private int idStep;
    private Uri uri;
    private static final String TAG = DetailStepActivity.class.getSimpleName();

    @BindView(R.id.tv_name_step)
    TextView mNameStepTextView;

    @BindView(R.id.tv_description_step)
    TextView mDescriptionTextView;

    @BindView(R.id.exo_play)
    PlayerView mPlayerView;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);
        ButterKnife.bind(this);

        step = (Step) getIntent().getExtras().getSerializable(EXTRA_STEP);
        if (step != null){
            idStep = step.getId();
            mNameStepTextView.setText(step.getShortDescription());
            mDescriptionTextView.setText(step.getDescription());
            uri = Uri.parse(step.getVideoUrl());
        }

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_previous:
                        idStep--;
                        loadData();
                        initializePlayer();
                        break;
                    case R.id.action_next:
                        idStep++;
                        loadData();
                        initializePlayer();
                        break;
                }

                return true;
            }
        });

    }

    private void initializePlayer( ) {
        if (mExoplayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoplayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        }
            mPlayerView.setPlayer(mExoplayer);

            String userAgent = Util.getUserAgent(this, "Cooking");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(this, userAgent), new DefaultExtractorsFactory(), null, null);

            mExoplayer.prepare(mediaSource);
            mExoplayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        if (mExoplayer != null) {
            mExoplayer.stop();
            mExoplayer.release();
            mExoplayer = null;
        }
    }

    private void loadData() {
        step = Utility.recipeList.get(Utility.currentRecipe).getSteps().get(idStep);

        mNameStepTextView.setText(step.getShortDescription());
        mDescriptionTextView.setText(step.getDescription());
        uri = Uri.parse(step.getVideoUrl());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoplayer == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

}
