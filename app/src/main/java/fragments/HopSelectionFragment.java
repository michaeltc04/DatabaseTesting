package fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
public class HopSelectionFragment extends Fragment {

    private static final int ANIM_DURATION = 300;

    private View mView;
    private FragmentTransaction ft;
    private List<String> mHopList;
    @InjectView(R.id.button_select_hops) Button mButton;
    @InjectView(R.id.text_select_hops) TextView mTextView;
    @InjectView(R.id.spinner_select_hops_1) Spinner mSpinner1;
    @InjectView(R.id.spinner_select_hops_2) Spinner mSpinner2;
    @InjectView(R.id.spinner_select_hops_3) Spinner mSpinner3;
    @InjectView(R.id.spinner_select_hops_4) Spinner mSpinner4;
    @InjectView(R.id.spinner_select_hops_5) Spinner mSpinner5;
    @InjectView(R.id.spinner_select_hops_6) Spinner mSpinner6;
    @InjectView(R.id.edit_select_hops_minutes_1) EditText mEditText1;
    @InjectView(R.id.edit_select_hops_minutes_2) EditText mEditText2;
    @InjectView(R.id.edit_select_hops_minutes_3) EditText mEditText3;
    @InjectView(R.id.edit_select_hops_minutes_4) EditText mEditText4;
    @InjectView(R.id.edit_select_hops_minutes_5) EditText mEditText5;
    @InjectView(R.id.edit_select_hops_minutes_6) EditText mEditText6;

    @Override
    public void onResume() {
        super.onResume();
        runEnterAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_hop_selections, container, false);
        ButterKnife.inject(this, mView);
        mHopList = ((CreateRecipeActivity)getActivity()).mHopList;
        setSpinners();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hopType1 = mSpinner1.getSelectedItem().toString();
                String hopType2 = mSpinner2.getSelectedItem().toString();
                String hopType3 = mSpinner3.getSelectedItem().toString();
                String hopType4 = mSpinner4.getSelectedItem().toString();
                String hopType5 = mSpinner5.getSelectedItem().toString();
                String hopType6 = mSpinner5.getSelectedItem().toString();

                int hopTime1 = getHopTime(hopType1, mEditText1);
                int hopTime2 = getHopTime(hopType2, mEditText2);
                int hopTime3 = getHopTime(hopType3, mEditText3);
                int hopTime4 = getHopTime(hopType4, mEditText4);
                int hopTime5 = getHopTime(hopType5, mEditText5);
                int hopTime6 = getHopTime(hopType6, mEditText6);

                ((CreateRecipeActivity)getActivity()).hopName1 = hopType1;
                ((CreateRecipeActivity)getActivity()).hopName2 = hopType2;
                ((CreateRecipeActivity)getActivity()).hopName3 = hopType3;
                ((CreateRecipeActivity)getActivity()).hopName4 = hopType4;
                ((CreateRecipeActivity)getActivity()).hopName5 = hopType5;
                ((CreateRecipeActivity)getActivity()).hopName6 = hopType6;

                ((CreateRecipeActivity)getActivity()).timeHop1 = hopTime1;
                ((CreateRecipeActivity)getActivity()).timeHop2 = hopTime2;
                ((CreateRecipeActivity)getActivity()).timeHop3 = hopTime3;
                ((CreateRecipeActivity)getActivity()).timeHop4 = hopTime4;
                ((CreateRecipeActivity)getActivity()).timeHop5 = hopTime5;
                ((CreateRecipeActivity)getActivity()).timeHop6 = hopTime6;

                runExitAnimation(true);
            }

        });

        return mView;
    }

    /**
     * If no hop time is input in theEditText param then hopTime is defaulted to 60min, otherwise
     * -1 for a "None" option from theType param or the user input amount.
     *
     * @param theType the Hop type selected by the user
     * @param theEditText the input field for minute of hop to be added
     * @return
     */
    private int getHopTime(String theType, EditText theEditText) {
        int hopTime;
        if(theType.equals("None")) {
            hopTime = -1;
        } else {
            String theString = theEditText.getText().toString();
            if (theString.equals("")) {
                hopTime = 60;
            } else {
                hopTime = Integer.parseInt(theString);
            }
        }
        return hopTime;
    }

    /**
     * Sets all spinners with Hop data from the database.
     */
    private void setSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mHopList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(adapter);
        mSpinner2.setAdapter(adapter);
        mSpinner3.setAdapter(adapter);
        mSpinner4.setAdapter(adapter);
        mSpinner5.setAdapter(adapter);
        mSpinner6.setAdapter(adapter);
    }

    //Inject all the fade views for animation reasons here not to clutter code
    @InjectView(R.id.text_fade_1) TextView mFadeView1;
    @InjectView(R.id.text_fade_2) TextView mFadeView2;
    @InjectView(R.id.text_fade_3) TextView mFadeView3;
    @InjectView(R.id.text_fade_4) TextView mFadeView4;
    @InjectView(R.id.text_fade_5) TextView mFadeView5;
    @InjectView(R.id.text_fade_6) TextView mFadeView6;
    @InjectView(R.id.text_fade_7) TextView mFadeView7;
    @InjectView(R.id.text_fade_8) TextView mFadeView8;
    @InjectView(R.id.text_fade_9) TextView mFadeView9;
    @InjectView(R.id.text_fade_10) TextView mFadeView10;
    @InjectView(R.id.text_fade_11) TextView mFadeView11;
    @InjectView(R.id.text_fade_12) TextView mFadeView12;
    @InjectView(R.id.text_hops_notice) TextView mHopsNotice;

    /**
     * Runs an enter animation.
     * Spinners, Button, and Main text come in from right, the rest is already visible.
     */
    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int h = getResources().getDisplayMetrics().heightPixels;
        int w = getResources().getDisplayMetrics().widthPixels;

        mHopsNotice.setAlpha(0);

        mButton.animate().setDuration(200);
        mSpinner1.animate().setDuration(200);
        mSpinner2.animate().setDuration(200);
        mSpinner3.animate().setDuration(200);
        mSpinner4.animate().setDuration(200);
        mSpinner5.animate().setDuration(200);
        mSpinner6.animate().setDuration(200);
        mTextView.animate().setDuration(200);
        mHopsNotice.animate().setDuration(200);

        mHopsNotice.animate().alpha(1);

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
        mSpinner6.getLocationOnScreen(screenLocation);
        mSpinner6.setTranslationX(w);
        mSpinner6.animate().setDuration(duration).translationX(mSpinner6.getLeft());

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
        mSpinner6.animate().setDuration(200);
        mTextView.animate().setDuration(200);

        mEditText1.animate().setDuration(200);
        mEditText2.animate().setDuration(200);
        mEditText3.animate().setDuration(200);
        mEditText4.animate().setDuration(200);
        mEditText5.animate().setDuration(200);
        mEditText6.animate().setDuration(200);
        mFadeView1.animate().setDuration(200);
        mFadeView2.animate().setDuration(200);
        mFadeView3.animate().setDuration(200);
        mFadeView4.animate().setDuration(200);
        mFadeView5.animate().setDuration(200);
        mFadeView6.animate().setDuration(200);
        mFadeView7.animate().setDuration(200);
        mFadeView8.animate().setDuration(200);
        mFadeView9.animate().setDuration(200);
        mFadeView10.animate().setDuration(200);
        mFadeView11.animate().setDuration(200);
        mFadeView12.animate().setDuration(200);
        mHopsNotice.animate().setDuration(200);

        mEditText1.animate().alpha(0);
        mEditText2.animate().alpha(0);
        mEditText3.animate().alpha(0);
        mEditText4.animate().alpha(0);
        mEditText5.animate().alpha(0);
        mEditText6.animate().alpha(0);
        mFadeView1.animate().alpha(0);
        mFadeView2.animate().alpha(0);
        mFadeView3.animate().alpha(0);
        mFadeView4.animate().alpha(0);
        mFadeView5.animate().alpha(0);
        mFadeView6.animate().alpha(0);
        mFadeView7.animate().alpha(0);
        mFadeView8.animate().alpha(0);
        mFadeView9.animate().alpha(0);
        mFadeView10.animate().alpha(0);
        mFadeView11.animate().alpha(0);
        mFadeView12.animate().alpha(0);
        mHopsNotice.animate().alpha(0);

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
        mSpinner6.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mSpinner6.getWidth();
        mSpinner6.animate()
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
                        YeastSelectionFragment yeastSelectionFragment = new YeastSelectionFragment();
                        ft.replace(R.id.frame_container, yeastSelectionFragment);
                        ft.commit();
                    }
                });
    }
}



