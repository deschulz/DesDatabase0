package net.deschulz.desdatabase0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "desDebug";

    private DesDBManager mydatabase = new DesDBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openDatabase(View view) {
        Log.i(TAG,"openDatabase");
        mydatabase.open();
    }

    public void insertRow(View v) {
        long ret = mydatabase.newEntry("Bart","password");
        Log.i(TAG,"insertRow " + ret);
    }

    public void closeDatabase(View v) {
        Log.i(TAG,"closeDatabase");
        mydatabase.close();
    }

    public void deleteRow(View v) {
        Log.i(TAG,"deleteRow");

    }

    public ArrayList<DesDbRecord> getAllRecords(View v) {
        Log.i(TAG,"getAllRecords");
        ArrayList<DesDbRecord> results = mydatabase.getAll();
        for (DesDbRecord rec : results) {
            Log.i(TAG, "Record: " + rec.getName() + " " + rec.getPassword());
        }
        return results;
    }

    public DesDbRecord getOneRecord(View v) {
        String name = "Joe";
        Log.i(TAG,"getOneRecord: " + name);
        DesDbRecord dbrec = mydatabase.getOneRec(name);
        if (dbrec != null) {
            Log.i(TAG, "Return = " + dbrec.dump());
        }
        else {
            Log.i(TAG,String.format("%s -- no record found",name));
        }
        return dbrec;
    }
}