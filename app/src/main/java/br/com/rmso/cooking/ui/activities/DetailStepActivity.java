package br.com.rmso.cooking.ui.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
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
import br.com.rmso.cooking.models.Recipe;
import br.com.rmso.cooking.models.Step;
import br.com.rmso.cooking.ui.fragments.DetailStepFragment;
import br.com.rmso.cooking.utils.Utility;

public class DetailStepActivity extends AppCompatActivity  {

    private int index;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_step);

        Configuration configuration = getResources().getConfiguration();
        int smallestScreenWidthDp = configuration.smallestScreenWidthDp;

        if (smallestScreenWidthDp >= 600){
            Utility.isTablet = true;
        }else {
            Utility.isTablet = false;
        }

        recipe = getIntent().getExtras().getParcelable(Utility.RECIPE);
        index = getIntent().getExtras().getInt(Utility.INDEX,0);

        showStep(recipe, index);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Utility.RECIPE, recipe);
        outState.putInt(Utility.INDEX, index);
    }

    public void showStep(Recipe recipe, int position) {
        if (Utility.isTablet) {
            this.index = position;
        }
        DetailStepFragment detailStepFragment = DetailStepFragment.newInstance(recipe, index);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.step_container, detailStepFragment);
        fragmentTransaction.commit();
    }

}
