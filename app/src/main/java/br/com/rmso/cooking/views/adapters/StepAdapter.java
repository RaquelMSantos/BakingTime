package br.com.rmso.cooking.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.rmso.cooking.R;
import br.com.rmso.cooking.models.Ingredient;
import br.com.rmso.cooking.models.Step;

/**
 * Created by Raquel on 14/08/2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<Step> stepList;
    private Context context;
    private final StepAdapterOnClickHandler clickHandler;

    public StepAdapter(Context mContext, StepAdapterOnClickHandler mClickHandler, List<Step> mStepList) {
        clickHandler = mClickHandler;
        context = mContext;
        stepList = mStepList;
    }

    public interface StepAdapterOnClickHandler {
        void onClick(int itemClicked, Step stepClicked);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_step, parent, false);
        return new StepAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        final Step step = stepList.get(position);
        holder.mNumberTextView.setText(step.getId() + ".");
        holder.mTitleTextView.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepList !=null) return stepList.size(); else return 0;
    }

    public void setStep(ArrayList<Step> stepList) {
        if (stepList != null){
            this.stepList = stepList;
            notifyDataSetChanged();
        }
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mNumberTextView;
        public final TextView mTitleTextView;

        public StepViewHolder (View view){
            super(view);
            mNumberTextView = itemView.findViewById(R.id.tv_number_step);
            mTitleTextView = itemView.findViewById(R.id.tv_title_step);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            Step stepClicked = stepList.get(itemPosition);
            clickHandler.onClick(itemPosition, stepClicked);
        }
    }
}