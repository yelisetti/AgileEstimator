package estimator.agile.com.estimator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.sephiroth.android.library.tooltip.Tooltip;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    EditText mInputEditText;
    TextView mDisplayTextView;
    TextView alertTextView;
    Button mSetValueBtn;
    Button mClearValueBtn;
    Spinner taskSpinner;

    private Integer[] agileArr = {0, 1, 2, 3, 5, 8, 13, 20, 40, 100};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputEditText = findViewById(R.id.editText);
        mDisplayTextView = findViewById(R.id.agilevalue);
        mSetValueBtn = findViewById(R.id.setValuebtn);
        mClearValueBtn = findViewById(R.id.clearbtn);
        alertTextView = findViewById(R.id.fibonacciText);
        taskSpinner = findViewById(R.id.spinner);

        displayTextOnClick();
        clearTextOnClick();
        getSpinner();

        Arrays.sort(agileArr);
    }

    private static boolean contains(Integer[] arr, Integer item) {
        int index = Arrays.binarySearch(arr, item);
        return index >= 0;
    }

    private void getSpinner() {
        // Spinner click listener
        taskSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Story");
        categories.add("Task");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        taskSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void clearTextOnClick() {
        mClearValueBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        mDisplayTextView.setText("");
                        mInputEditText.setText("");
                        alertTextView.setVisibility(View.GONE);
                        hideKeyboard();
                    }
                }
        );
    }

    private void displayTextOnClick() {
        mSetValueBtn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        if (taskSpinner.getSelectedItem().toString().equals("Story")) {
                            if (contains(agileArr, Integer.parseInt(mInputEditText.getText().toString()))) {
                                mDisplayTextView.setText(mInputEditText.getText().toString());
                                alertTextView.setVisibility(View.GONE);
                            } else {
                                mDisplayTextView.setText(mInputEditText.getText().toString());
                                alertTextView.setVisibility(View.VISIBLE);
                            }
                            hideKeyboard();
                        } else {
                            mDisplayTextView.setText(mInputEditText.getText().toString());
                            alertTextView.setVisibility(View.GONE);
                        }
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
