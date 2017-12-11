package estimator.agile.com.estimator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText mInputEditText;
    TextView mDisplayTextView;
    Button mSetValueBtn;
    Button mClearValueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputEditText = (EditText) findViewById(R.id.editText);
        mDisplayTextView = (TextView) findViewById(R.id.agilevalue);
        mSetValueBtn = (Button) findViewById(R.id.setValuebtn);
        mClearValueBtn = (Button) findViewById(R.id.clearbtn);

        displayTextOnClick();
        clearTextOnClick();
    }

    private void clearTextOnClick() {
        mClearValueBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mDisplayTextView.setText("");
                        mInputEditText.setText("");
                        hideKeyboard();
                    }
                }
        );
    }

    private void displayTextOnClick() {
        mSetValueBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mDisplayTextView.setText(mInputEditText.getText().toString());
                        hideKeyboard();
                    }
                }
        );

    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
