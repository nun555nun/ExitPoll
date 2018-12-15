package com.example.exitpoll;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.exitpoll.adapter.VoteListAdapter;
import com.example.exitpoll.db.DatabaseHelper;
import com.example.exitpoll.model.VoteItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.exitpoll.db.DatabaseHelper.COL_ID;
import static com.example.exitpoll.db.DatabaseHelper.COL_IMAGE;
import static com.example.exitpoll.db.DatabaseHelper.COL_NUMBER;
import static com.example.exitpoll.db.DatabaseHelper.TABLE_NAME;

public class ResultActivity extends AppCompatActivity {
    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private List<VoteItem> mVoteItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mHelper = new DatabaseHelper(ResultActivity.this);
        mDb = mHelper.getWritableDatabase();
        loadPhoneData();
        setupListView();
        Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();

                cv.put(COL_NUMBER,0);

                mDb.update(
                        TABLE_NAME,
                        cv,
                        COL_ID ,null
                );
                Intent i =getIntent();
                finish();
                startActivity(i);
            }
        });
    }


    private void loadPhoneData() {
        Cursor c = mDb.query(TABLE_NAME, null, null, null, null, null, null);

        mVoteItemList = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(COL_ID));

            int number = c.getInt(c.getColumnIndex(COL_NUMBER));
            String image = c.getString(c.getColumnIndex(COL_IMAGE));

            VoteItem item = new VoteItem(id, number, image);
            mVoteItemList.add(item);
        }
        c.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setupListView() {
        VoteListAdapter adapter = new VoteListAdapter(
                ResultActivity.this,
                R.layout.item_vote,
                mVoteItemList
        );
        ListView lv = findViewById(R.id.vote_list);
        lv.setAdapter(adapter);

    }
}
