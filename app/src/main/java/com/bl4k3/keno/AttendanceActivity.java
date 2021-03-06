package com.bl4k3.keno;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AttendanceActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.SUBJECTNAME, DatabaseHelper.TOTALCONDUCTED, DatabaseHelper.TOTALATTENDED, DatabaseHelper.PERCENTAGE};

    final int[] to = new int[] { R.id.id, R.id.SubjectName, R.id.TotalConducted, R.id.TotalAttended, R.id.percentDisplay};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mAuth = FirebaseAuth.getInstance();

        DBManager dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        // OnCLickListener For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView name = (TextView) view.findViewById(R.id.SubjectName);
                TextView email = (TextView) view.findViewById(R.id.TotalConducted);
                TextView password = (TextView) view.findViewById(R.id.TotalAttended);
                TextView percent = (TextView) view.findViewById(R.id.percentDisplay);

                String id = idTextView.getText().toString();
                String SubjectN = name.getText().toString();
                String TotalC = email.getText().toString();
                String TotalA = password.getText().toString();
                String attendPercent = percent.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifySubject.class);
                modify_intent.putExtra("SubjectName", SubjectN);
                modify_intent.putExtra("TotalC", TotalC);
                modify_intent.putExtra("TotalA", TotalA);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("percent",attendPercent);

                startActivity(modify_intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        /*FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            finish();
            Intent intent = new Intent(AttendanceActivity.this, Welcome.class);
            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_LONG).show();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {

        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_subject) {

            Intent add_mem = new Intent(this, AddSubject.class);
            startActivity(add_mem);

        }
        return super.onOptionsItemSelected(item);
    }
}
