package fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.michaelt.databasetesting.R;

public class NameBeerFragment extends Fragment {

    private static final int ANIM_DURATION = 300;

    private View mView;
    private FragmentTransaction ft;
    private Button mButton;
    private TextView mTextView;
    private EditText mEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_name_beer, container, false);
        mButton = (Button) mView.findViewById(R.id.button_name_your_beer);
        mEditText = (EditText) mView.findViewById(R.id.edit_name_your_beer);
        mTextView = (TextView) mView.findViewById(R.id.text_name_your_beer);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runExitAnimation();
            }
        });

        return mView;
    }

    private void loadNext() {
        ft =     getFragmentManager()
                .beginTransaction();
        BoilTimeFragment boilTimeFragment = new BoilTimeFragment();
        ft.replace(R.id.frame_container, boilTimeFragment);
        ft.commit();
    }

    private void runExitAnimation() {
        final long duration = (long) (ANIM_DURATION);
        int[] screenLocation = new int[2];
        int mLeftDelta = 0;

        mButton.animate().setDuration(300);
        mEditText.animate().setDuration(300);
        mTextView.animate().setDuration(300);

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
                        loadNext();
                    }
                });
    }
}
