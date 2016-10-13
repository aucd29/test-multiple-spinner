package com.example.multiple_spinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.textView)
    TextView mText;

    @BindView(R.id.sp1)
    Spinner mSp1;

    @BindView(R.id.sp2)
    Spinner mSp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initSpinner();
    }

    private void initSpinner() {
        mSp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                Log.d(TAG, "SP1 SELECTED ITEM : " + selected);
                if (selected == null) {
                    return ;
                }

                int sp2Id = getResources().getIdentifier("sp2_" + selected + "_data", "array", getPackageName());
                if (sp2Id == 0) {
                    Log.e(TAG, "ERROR: sp2Id == null");
                    return;
                }

                mSp2.setAdapter(instanceAdapter(sp2Id));
                mSp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mText.setText(parent.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ArrayAdapter<String> instanceAdapter(int spId) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(spId));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }
}
