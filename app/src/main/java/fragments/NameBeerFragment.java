package fragments;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
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

public class NameBeerFragment extends Fragment {

    private static final int ANIM_DURATION = 300;

    private View mView;
    private FragmentTransaction ft;
    @InjectView(R.id.button_name_your_beer) Button mButton;
    @InjectView(R.id.edit_name_your_beer) EditText mEditText;
    @InjectView(R.id.text_name_your_beer) TextView mTextView;

    @Override
    public void onResume() {
        super.onResume();
        runEnterAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_name_beer, container, false);
        ButterKnife.inject(this, mView);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beerName = mEditText.getText().toString();
                if (beerName.length() < 3) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Name needs to be longer than 2 characters!").setTitle("Improper Name");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    ((CreateRecipeActivity)getActivity()).mBeerName = beerName;
                    runExitAnimation();
                }
            }
        });

        return mView;
    }

    /**
     * Runs an enter animation.
     * EditText, Button, and Main text come in from right.
     */
    private void runEnterAnimation() {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int h = getResources().getDisplayMetrics().heightPixels;
        int w = getResources().getDisplayMetrics().widthPixels;

        mButton.animate().setDuration(200);
        mEditText.animate().setDuration(200);
        mTextView.animate().setDuration(200);

        mButton.getLocationOnScreen(screenLocation);
        mButton.setTranslationX(w);
        mButton.animate().setDuration(duration).translationX(mButton.getLeft());

        mEditText.getLocationOnScreen(screenLocation);
        mEditText.setTranslationX(w);
        mEditText.animate().setDuration(duration).translationX(mEditText.getLeft());

        mTextView.getLocationOnScreen(screenLocation);
        mTextView.setTranslationX(w);
        mTextView.animate().setDuration(duration).translationX(mTextView.getLeft());
    }

    /**
     * Runs exit animation.
     * EditText, Button, and Main text run out left.
     */
    private void runExitAnimation() {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int mLeftDelta = 0;

        mButton.animate().setDuration(200);
        mEditText.animate().setDuration(200);
        mTextView.animate().setDuration(200);

        mButton.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mButton.getWidth();
        mButton.animate()
                .setDuration(duration)
                .translationX(mLeftDelta);

        mEditText.getLocationOnScreen(screenLocation);
        mLeftDelta = 0 - screenLocation[0] - mEditText.getWidth();
        mEditText.animate()
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
                        ft =     getFragmentManager()
                                .beginTransaction();
                        BoilTimeFragment boilTimeFragment = new BoilTimeFragment();
                        ft.replace(R.id.frame_container, boilTimeFragment);
                        ft.commit();
                    }
                });
    }
}
