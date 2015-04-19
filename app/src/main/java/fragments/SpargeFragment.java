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
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Michael on 4/17/2015.
 */
public class SpargeFragment extends Fragment {

    private static final int ANIM_DURATION = 300;

    private View mView;
    private FragmentTransaction ft;
    @InjectView(R.id.button_sparge_type) Button mButton;
    @InjectView(R.id.spinner_sparge_type) Spinner mSpinner;
    @InjectView(R.id.text_sparge_type) TextView mTextView;
    @InjectView(R.id.text_sparge_notice) TextView mTextNotice;

    @Override
    public void onResume() {
        super.onResume();
        runEnterAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_sparge, container, false);
        ButterKnife.inject(this, mView);

        String[] items = new String[] { "None" , "Fly" , "Batch" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spargeType = mSpinner.getSelectedItem().toString();
                ((CreateRecipeActivity)getActivity()).mSpargeType = spargeType;
                runExitAnimation(true);
            }

        });

        return mView;
    }

    /**
     * Runs an enter animation.
     * Spinner, Button, and Main text come in from right, TextNotice fades in.
     */
    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int h = getResources().getDisplayMetrics().heightPixels;
        int w = getResources().getDisplayMetrics().widthPixels;

        mTextNotice.setAlpha(0);

        mButton.animate().setDuration(200);
        mSpinner.animate().setDuration(200);
        mTextView.animate().setDuration(200);
        mTextNotice.animate().setDuration(200);

        mTextNotice.animate().alpha(1);

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

    /**
     * Runs an exit animation.
     * Spinner, Button, and Main text exit left, TextNotice fades out.
     */
    private void runExitAnimation(final boolean test) {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int mLeftDelta = 0;

        mButton.animate().setDuration(200);
        mSpinner.animate().setDuration(200);
        mTextView.animate().setDuration(200);
        mTextNotice.animate().setDuration(200);

        mTextNotice.animate().alpha(0);

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
                .translationX(mLeftDelta)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ft = getFragmentManager().beginTransaction();
                        FermentingTimeFragment fermentingTimeFragment = new FermentingTimeFragment();
                        ft.replace(R.id.frame_container, fermentingTimeFragment);
                        ft.commit();
                    }
                });
    }
}



