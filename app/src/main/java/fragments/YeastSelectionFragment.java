package fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.michaelt.databasetesting.CreateRecipeActivity;
import com.michaelt.databasetesting.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Michael on 4/18/2015.
 */
public class YeastSelectionFragment extends Fragment {

    private static final int ANIM_DURATION = 300;

    private View mView;
    private List<String> mYeastList;
    private FragmentTransaction ft;
    @InjectView(R.id.button_yeast_selection) Button mButton;
    @InjectView(R.id.text_yeast_selection) TextView mTextView;
    @InjectView(R.id.spinner_yeast_selection) Spinner mSpinner;

    @Override
    public void onResume() {
        super.onResume();
        runEnterAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_yeast_selection, container, false);
        ButterKnife.inject(this, mView);

        mYeastList = ((CreateRecipeActivity)getActivity()).mYeastList;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mYeastList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yeastName = mSpinner.getSelectedItem().toString();
                ((CreateRecipeActivity)getActivity()).mYeastName = yeastName;
                runExitAnimation(true);
            }

        });

        return mView;
    }

    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int h = getResources().getDisplayMetrics().heightPixels;
        int w = getResources().getDisplayMetrics().widthPixels;

        mButton.animate().setDuration(200);
        mSpinner.animate().setDuration(200);
        mTextView.animate().setDuration(200);

        mButton.getLocationOnScreen(screenLocation);
        mButton.setTranslationX(w);
        mButton.animate().setDuration(duration).translationX(mButton.getLeft());

        mSpinner.getLocationOnScreen(screenLocation);
        mSpinner.setTranslationX(w);
        mSpinner.animate().setDuration(duration).translationX(mSpinner.getLeft());

        mTextView.getLocationOnScreen(screenLocation);
        mTextView.setTranslationX(w);
        mTextView.animate().setDuration(duration).translationX(mTextView.getLeft());
    }

    private void runExitAnimation(final boolean test) {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int mLeftDelta = 0;

        mButton.animate().setDuration(200);
        mSpinner.animate().setDuration(200);
        mTextView.animate().setDuration(200);

        mButton.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mButton.getWidth();
        mButton.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);

        mSpinner.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mSpinner.getWidth();
        mSpinner.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);

        mTextView.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mTextView.getWidth();
        mTextView.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);
        ((CreateRecipeActivity)getActivity()).recipeCreated(this);
    }
}
