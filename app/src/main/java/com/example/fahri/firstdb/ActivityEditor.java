package com.example.fahri.firstdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fahri.firstdb.Data.DataBaseHelper;

public class    ActivityEditor extends AppCompatActivity {

    DataBaseHelper myDb;

    EditText mETDay;
    EditText mETHomework;
    EditText mETLecture;
    EditText mETDeadline;

    String mDay, mHomework, mLecture, mDeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mETDay = findViewById(R.id.et_day);
        mETHomework = findViewById(R.id.et_homework);
        mETLecture = findViewById(R.id.et_lecture);
        mETDeadline = findViewById(R.id.et_deadline);

        myDb = new DataBaseHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mDay = mETDay.getText().toString();
        mHomework = mETHomework.getText().toString();
        mLecture = mETLecture.getText().toString();
        mDeadline = mETDeadline.getText().toString();

        switch (item.getItemId()) {
            case R.id.action_save:

                if (mDay.equals("")) {
                    Toast.makeText(ActivityEditor.this, "There is an empty field !", Toast.LENGTH_SHORT).show();
                } else if (mHomework.equals("")) {
                    Toast.makeText(ActivityEditor.this, "There is an empty field !", Toast.LENGTH_SHORT).show();
                } else if (mLecture.equals("")) {
                    Toast.makeText(ActivityEditor.this, "There is an empty field !", Toast.LENGTH_SHORT).show();
                } else if (mDeadline.equals("")) {
                    Toast.makeText(ActivityEditor.this, "There is an empty field !", Toast.LENGTH_SHORT).show();
                } else {

                    boolean isInserted = myDb.insertData(mETDay.getText().toString(), mETHomework.getText().toString(),
                            mETLecture.getText().toString(), mETDeadline.getText().toString());

                    if (isInserted) {

                        mETHomework.setText(null);
                        mETLecture.setText(null);
                        mETDeadline.setText(null);
                        mETDay.setText(null);

                        Toast.makeText(ActivityEditor.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        return true;
                    } else {
                        Toast.makeText(ActivityEditor.this, "Failed To Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            // case R.id.action_delete:
            //
            //  return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
