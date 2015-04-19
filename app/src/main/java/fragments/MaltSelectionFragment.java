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
 * Created by Michael on 4/17/2015.
 */
public class MaltSelectionFragment extends Fragment {

    private static final int ANIM_DURATION = 300;

    private View mView;
    private FragmentTransaction ft;
    private List<String> mMaltList;
    @InjectView(R.id.spinner_select_malts_1) Spinner mSpinner1;
    @InjectView(R.id.spinner_select_malts_2) Spinner mSpinner2;
    @InjectView(R.id.spinner_select_malts_3) Spinner mSpinner3;
    @InjectView(R.id.spinner_select_malts_4) Spinner mSpinner4;
    @InjectView(R.id.spinner_select_malts_5) Spinner mSpinner5;
    @InjectView(R.id.button_select_malts) Button mButton;
    @InjectView(R.id.text_select_malts) TextView mTextView;

    @Override
    public void onResume() {
        super.onResume();
        runEnterAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_malt_selections, container, false);
        ButterKnife.inject(this, mView);
        mMaltList = ((CreateRecipeActivity)getActivity()).mGrainList;
        setSpinners();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maltType1 = mSpinner1.getSelectedItem().toString();
                String maltType2 = mSpinner2.getSelectedItem().toString();
                String maltType3 = mSpinner3.getSelectedItem().toString();
                String maltType4 = mSpinner4.getSelectedItem().toString();
                String maltType5 = mSpinner5.getSelectedItem().toString();


                ((CreateRecipeActivity)getActivity()).maltName1 = maltType1;
                ((CreateRecipeActivity)getActivity()).maltName2 = maltType2;
                ((CreateRecipeActivity)getActivity()).maltName3 = maltType3;
                ((CreateRecipeActivity)getActivity()).maltName4 = maltType4;
                ((CreateRecipeActivity)getActivity()).maltName5 = maltType5;
                runExitAnimation(true);
            }

        });

        return mView;
    }

    /**
     * Sets all the Malt data from the Database into the spinners.
     */
    private void setSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mMaltList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(adapter);
        mSpinner2.setAdapter(adapter);
        mSpinner3.setAdapter(adapter);
        mSpinner4.setAdapter(adapter);
        mSpinner5.setAdapter(adapter);
    }

    /**
     * Runs an enter animation.
     * Spinners, Button, and Main text come in from right, the rest is already visible.
     */
    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int h = getResources().getDisplayMetrics().heightPixels;
        int w = getResources().getDisplayMetrics().widthPixels;

        mButton.animate().setDuration(200);
        mSpinner1.animate().setDuration(200);
        mSpinner2.animate().setDuration(200);
        mSpinner3.animate().setDuration(200);
        mSpinner4.animate().setDuration(200);
        mSpinner5.animate().setDuration(200);
        mTextView.animate().setDuration(200);

        mButton.getLocationOnScreen(screenLocation);
        mButton.setTranslationX(w);
        mButton.animate().setDuration(duration).translationX(mButton.getLeft());

        mSpinner1.getLocationOnScreen(screenLocation);
        mSpinner1.setTranslationX(w);
        mSpinner1.animate().setDuration(duration).translationX(mSpinner1.getLeft());
        mSpinner2.getLocationOnScreen(screenLocation);
        mSpinner2.setTranslationX(w);
        mSpinner2.animate().setDuration(duration).translationX(mSpinner2.getLeft());
        mSpinner3.getLocationOnScreen(screenLocation);
        mSpinner3.setTranslationX(w);
        mSpinner3.animate().setDuration(duration).translationX(mSpinner3.getLeft());
        mSpinner4.getLocationOnScreen(screenLocation);
        mSpinner4.setTranslationX(w);
        mSpinner4.animate().setDuration(duration).translationX(mSpinner4.getLeft());
        mSpinner5.getLocationOnScreen(screenLocation);
        mSpinner5.setTranslationX(w);
        mSpinner5.animate().setDuration(duration).translationX(mSpinner5.getLeft());

        mTextView.getLocationOnScreen(screenLocation);
        mTextView.setTranslationX(w);
        mTextView.animate().setDuration(duration).translationX(mTextView.getLeft());
    }

    /**
     * Runs exit animation.
     * Spinners, Button, and Main text run out left, everything else fades out.
     *
     * @param test
     */
    private void runExitAnimation(final boolean test) {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int mLeftDelta = 0;

        mButton.animate().setDuration(200);
        mSpinner1.animate().setDuration(200);
        mSpinner2.animate().setDuration(200);
        mSpinner3.animate().setDuration(200);
        mSpinner4.animate().setDuration(200);
        mSpinner5.animate().setDuration(200);
        mTextView.animate().setDuration(200);

        mButton.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mButton.getWidth();
        mButton.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);

        mSpinner1.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mSpinner1.getWidth();
        mSpinner1.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);
        mSpinner2.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mSpinner2.getWidth();
        mSpinner2.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);
        mSpinner3.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mSpinner3.getWidth();
        mSpinner3.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);
        mSpinner4.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mSpinner4.getWidth();
        mSpinner4.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);
        mSpinner5.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mSpinner5.getWidth();
        mSpinner5.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);

        mTextView.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mTextView.getWidth();
        mTextView.animate()
                .setDuration(duration)
                .translationX(mLeftDelta)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ft = getFragmentManager().beginTransaction();
                        HopSelectionFragment hopSelectionFragment = new HopSelectionFragment();
                        ft.replace(R.id.frame_container, hopSelectionFragment);
                        ft.commit();
                    }
                });
    }
}



