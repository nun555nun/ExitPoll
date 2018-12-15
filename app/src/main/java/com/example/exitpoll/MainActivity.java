package com.example.exitpoll;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.exitpoll.db.DatabaseHelper;
import com.example.exitpoll.model.VoteItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.exitpoll.db.DatabaseHelper.COL_ID;
import static com.example.exitpoll.db.DatabaseHelper.COL_IMAGE;
import static com.example.exitpoll.db.DatabaseHelper.COL_NUMBER;
import static com.example.exitpoll.db.DatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private List<VoteItem> mVoteItemList;
    private long mId;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new DatabaseHelper(MainActivity.this);
        mDb = mHelper.getWritableDatabase();
        loadPhoneData();
        Button resultButton = findViewById(R.id.result_button);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });
        Button no_vote = findViewById(R.id.no_vote);
        no_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteItem noVote = mVoteItemList.get(0);
                count = noVote.number;
                count+=1;
                mId=1;
                updateVote();
                Toast.makeText(MainActivity.this,"งดออกเสียง ",Toast.LENGTH_SHORT).show();
            }
        });
        Button no1 = findViewById(R.id.no1);
        no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteItem voteNo1 = mVoteItemList.get(1);
                count = voteNo1.number;
                count+=1;
                mId=2;
                updateVote();
                Toast.makeText(MainActivity.this,"เลือกผู้สมัครหมายเลข 1",Toast.LENGTH_SHORT).show();
            }
        });
        Button no2 = findViewById(R.id.no2);
        no2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteItem voteNo2 = mVoteItemList.get(2);
                count = voteNo2.number;
                count+=1;
                mId=3;
                updateVote();
                Toast.makeText(MainActivity.this,"เลือกผู้สมัครหมายเลข 2",Toast.LENGTH_SHORT).show();
            }
        });
        Button no3 = findViewById(R.id.no3);
        no3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteItem voteNo3 = mVoteItemList.get(3);
                count = voteNo3.number;
                count+=1;
                mId=4;
                updateVote();
                Toast.makeText(MainActivity.this,"เลือกผู้สมัครหมายเลข 3",Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void updateVote(){
        ContentValues cv = new ContentValues();

        cv.put(COL_NUMBER, count);

        mDb.update(
                TABLE_NAME,
                cv,
                COL_ID + " = ?",
                new String[]{String.valueOf(mId)}
        );
        loadPhoneData();
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
        loadPhoneData();
    }
}
