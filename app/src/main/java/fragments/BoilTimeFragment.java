package fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.michaelt.databasetesting.CreateRecipeActivity;
import com.michaelt.databasetesting.R;

/**
 * Created by Michael on 4/17/2015.
 */
public class BoilTimeFragment extends Fragment {

    private static final int ANIM_DURATION = 300;

    private View mView;
    private FragmentTransaction ft;
    private Button mButton;
    private TextView mTextView;
    private EditText mEditText;

    @Override
    public void onResume() {
        super.onResume();
        runEnterAnimation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_boil_time, container, false);

        mButton = (Button) mView.findViewById(R.id.button_boil_time);
        mEditText = (EditText) mView.findViewById(R.id.edit_boil_time);
        mTextView = (TextView) mView.findViewById(R.id.text_boil_time);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boilTime = mEditText.getText().toString();
                if (boilTime.length() < 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Under 10 minutes isn't much of a boil!").setTitle("Improper Boil Time");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    ((CreateRecipeActivity)getActivity()).mBoilTime = boilTime;
                    runExitAnimation(true);
                }
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

    private void runExitAnimation(final boolean test) {
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
                        ft = getFragmentManager()
                                .beginTransaction();
                        SpargeFragment spargeFragment = new SpargeFragment();
                        ft.replace(R.id.frame_container, spargeFragment);
                        ft.commit();
                    }
                });
    }
}



