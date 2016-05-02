package com.wimso.android_sqlite;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wimso.android_sqlite.adapter.ContactListAdapter;
import com.wimso.android_sqlite.db.SQLiteDB;
import com.wimso.android_sqlite.listener.RecyclerItemClickListener;
import com.wimso.android_sqlite.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView lvContact;
    private FloatingActionButton btnAdd;

    private ContactListAdapter contactListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = (RecyclerView) findViewById(R.id.lvContact);
        btnAdd = (FloatingActionButton) findViewById(R.id.add);

        linearLayoutManager = new LinearLayoutManager(this);
        contactListAdapter = new ContactListAdapter(this);
        contactListAdapter.setOnItemClickListener(this);

        lvContact.setLayoutManager(linearLayoutManager);
        lvContact.setAdapter(contactListAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActActivity.start(MainActivity.this);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    void loadData(){
        sqLiteDB = new SQLiteDB(this);

        List<Contact> contactList = new ArrayList<>();

        Cursor cursor = sqLiteDB.retrieve();
        Contact contact;

        if (cursor.moveToFirst()) {
            do {

                contact = new Contact();

                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));

                contactList.add(contact);
            }while (cursor.moveToNext());
        }

        contactListAdapter.clear();
        contactListAdapter.addAll(contactList);
    }

    @Override
    public void onItemClick(int position, View view) {
        ActActivity.start(this, contactListAdapter.getItem(position));
    }
}
