package com.example.fahri.firstdb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.NoCopySpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fahri.firstdb.Data.DataBaseHelper;
import com.example.fahri.firstdb.ListView.Words;
import com.example.fahri.firstdb.ListView.WordsAdapter;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {

    ArrayList<Words> list;

    WordsAdapter mAdapter;
    ListView listView;

    DataBaseHelper myDb;

    FloatingActionButton mFab;

    RelativeLayout mRlEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        myDb = new DataBaseHelper(this);

        mRlEmpty = findViewById(R.id.rl_empty);
        mFab = findViewById(R.id.fab);

        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        mAdapter = new WordsAdapter(this, R.layout.list_view, list);
        listView.setAdapter(mAdapter);
        list.clear();

        final Cursor res = myDb.getAllData();
        if (res.getCount() == 0){
            mRlEmpty.setVisibility(View.VISIBLE);
        }

        while (res.moveToNext()){
            int id = res.getInt(0);
            String day = res.getString(1);
            String homework = res.getString(2);
            String lecture = res.getString(3);
            String deadline = res.getString(4);

            list.add(new Words(id, day, homework, lecture, deadline));
        }

        mAdapter.notifyDataSetChanged();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this, ActivityEditor.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                final CharSequence[] items = {"Update", "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityMain.this);
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //update
                        if (which==0){
                            //
                            Cursor res = myDb.getAllData();
                            ArrayList<String > arrayList = new ArrayList<>();
                            while(res.moveToNext()){
                                arrayList.add(res.getString(0));
                            }
                            showDialogUpdate(ActivityMain.this,arrayList.get(position));
                        }

                        //delete
                        if (which==1){
                            //
                            Cursor res = myDb.getAllData();
                            ArrayList<String> arrayList = new ArrayList<>();
                            while(res.moveToNext()){
                                arrayList.add(res.getString(0));
                            }
                            showDialogDelete(arrayList.get(position));
                        }
                    }
                });

                dialog.show();
                return true;
            }

            private void getReadableData(){

                final Cursor res = myDb.getAllData();
                if (res.getCount() == 0){
                    mRlEmpty.setVisibility(View.VISIBLE);
                }

                while (res.moveToNext()){
                    int id = res.getInt(0);
                    String day = res.getString(1);
                    String homework = res.getString(2);
                    String lecture = res.getString(3);
                    String deadline = res.getString(4);

                    list.add(new Words(id, day, homework, lecture, deadline));
                }

                mAdapter.notifyDataSetChanged();
            }

            private void showDialogDelete(final String idRecord) {
                AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ActivityMain.this);
                dialogDelete.setTitle("Warning!!");
                dialogDelete.setMessage("Are you sure to delete?");
                dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            myDb.deleteData(idRecord);
                            Toast.makeText(ActivityMain.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                            list.clear();

                            getReadableData();

                        }
                        catch (Exception e){
                            Log.e("error", e.getMessage());
                        }
                    }
                });
                dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialogDelete.show();
            }

            private void showDialogUpdate(Activity activity, final String position){
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.dialog_update);
                dialog.setTitle("Update");

                final EditText etDayUpdate = dialog.findViewById(R.id.et_day);
                final EditText etHomeworkUpdate = dialog.findViewById(R.id.et_homework);
                final EditText etLecture = dialog.findViewById(R.id.et_lecture);
                final EditText etDeadline = dialog.findViewById(R.id.et_deadline);
                Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

                final Cursor res = myDb.getAllData();

                while (res.moveToNext()){
                    String id = res.getString(0);

                    if (id.equals(position)) {
                        String day = res.getString(1);
                        String homework = res.getString(2);
                        String lecture = res.getString(3);
                        String deadline = res.getString(4);

                        etDayUpdate.setText(day);
                        etHomeworkUpdate.setText(homework);
                        etLecture.setText(lecture);
                        etDeadline.setText(deadline);
                    }
                }

                mAdapter.notifyDataSetChanged();

                //
                int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*1.00);
                int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.80);
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (etDayUpdate.getText().toString().equals("")) {
                                Toast.makeText(ActivityMain.this, "There is an empty field !", Toast.LENGTH_SHORT).show();
                            } else if (etHomeworkUpdate.getText().toString().equals("")) {
                                Toast.makeText(ActivityMain.this, "There is an empty field !", Toast.LENGTH_SHORT).show();
                            } else if (etLecture.getText().toString().equals("")) {
                                Toast.makeText(ActivityMain.this, "There is an empty field !", Toast.LENGTH_SHORT).show();
                            } else if (etDeadline.getText().toString().equals("")) {
                                Toast.makeText(ActivityMain.this, "There is an empty field !", Toast.LENGTH_SHORT).show();
                            } else {

                                try {
                                    myDb.updateData(position, etDayUpdate.getText().toString(),
                                            etHomeworkUpdate.getText().toString(), etLecture.getText().toString(),
                                            etDeadline.getText().toString());

                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                                    list.clear();

                                    getReadableData();

                                } catch (Exception error) {
                                    Log.e("Update error", error.getMessage());
                                }
                            }

                        }
                    });
            }

        });
    }
}

