package fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.michaelt.databasetesting.CreateRecipeActivity;
import com.michaelt.databasetesting.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FermentingTimeFragment extends Fragment {

    private static final int ANIM_DURATION = 300;

    private View mView;
    private FragmentTransaction ft;
    @InjectView(R.id.button_ferment_time) Button mButton;
    @InjectView(R.id.text_primary_ferment_time) TextView mPrimaryText;
    @InjectView(R.id.text_secondary_ferment_time) TextView mSecondaryText;
    @InjectView(R.id.edit_primary_ferment_time) EditText mPrimaryEdit;
    @InjectView(R.id.edit_secondary_ferment_time) EditText mSecondaryEdit;

    @Override
    public void onResume() {
        super.onResume();
        runEnterAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_ferment_time, container, false);
        ButterKnife.inject(this, mView);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String primaryFerment = mPrimaryEdit.getText().toString();
                String secondaryFerment = mSecondaryEdit.getText().toString();

                int secondaryCheck = 0;
                int primaryTime = Integer.parseInt(primaryFerment);
                int secondaryTime = Integer.parseInt(secondaryFerment);
                if (secondaryTime > 0) { secondaryCheck = 1; }

                if (primaryTime < 4) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Primary fermentation lasts longer than at least 4 days.").setTitle("Improper Time");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    ((CreateRecipeActivity)getActivity()).mPrimaryTime = primaryTime;
                    ((CreateRecipeActivity)getActivity()).mSecondaryTime = secondaryTime;
                    ((CreateRecipeActivity)getActivity()).mSecondaryCheck = secondaryCheck;
                    runExitAnimation();
                }
            }
        });

        return mView;
    }

    /**
     * Runs an enter animation
     * Button, TextViews and EditTexts come in from right.
     */
    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int h = getResources().getDisplayMetrics().heightPixels;
        int w = getResources().getDisplayMetrics().widthPixels;

        mButton.animate().setDuration(200);
        mPrimaryEdit.animate().setDuration(200);
        mSecondaryEdit.animate().setDuration(200);
        mPrimaryText.animate().setDuration(200);
        mSecondaryText.animate().setDuration(200);

        mButton.getLocationOnScreen(screenLocation);
        mButton.setTranslationX(w);
        mButton.animate().setDuration(duration).translationX(mButton.getLeft());

        mPrimaryEdit.getLocationOnScreen(screenLocation);
        mPrimaryEdit.setTranslationX(w);
        mPrimaryEdit.animate().setDuration(duration).translationX(mPrimaryEdit.getLeft());

        mSecondaryEdit.getLocationOnScreen(screenLocation);
        mSecondaryEdit.setTranslationX(w);
        mSecondaryEdit.animate().setDuration(duration).translationX(mSecondaryEdit.getLeft());

        mPrimaryText.getLocationOnScreen(screenLocation);
        mPrimaryText.setTranslationX(w);
        mPrimaryText.animate().setDuration(duration).translationX(mPrimaryText.getLeft());

        mSecondaryText.getLocationOnScreen(screenLocation);
        mSecondaryText.setTranslationX(w);
        mSecondaryText.animate().setDuration(duration).translationX(mSecondaryText.getLeft());
    }

    /**
     * Runs exit animation.
     * Button, TextViews and EditTexts run out left.
     */
    private void runExitAnimation() {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int mLeftDelta = 0;

        mButton.animate().setDuration(200);
        mPrimaryEdit.animate().setDuration(200);
        mSecondaryEdit.animate().setDuration(200);
        mPrimaryText.animate().setDuration(200);
        mSecondaryText.animate().setDuration(200);

        mButton.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mButton.getWidth();
        mButton.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);

        mPrimaryEdit.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mPrimaryEdit.getWidth();
        mPrimaryEdit.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);

        mSecondaryEdit.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mSecondaryEdit.getWidth();
        mSecondaryEdit.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);

        mPrimaryText.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mPrimaryText.getWidth();
        mPrimaryText.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);

        mSecondaryText.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mSecondaryText.getWidth();
        mSecondaryText.animate()
                .setDuration(duration)
                .translationX(mLeftDelta)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ft =     getFragmentManager()
                                .beginTransaction();
                        MaltSelectionFragment maltSelectionFragment = new MaltSelectionFragment();
                        ft.replace(R.id.frame_container, maltSelectionFragment);
                        ft.commit();
                    }
                });
    }
}
