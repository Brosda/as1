package abrosda.abrosda_fueltrack;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class NewEntryActivity extends ActionBarActivity {

    protected EditText date, station, odometer, grade, amount, cost;
    private static final String FILENAME = "file.sav";


    private ArrayList<Entry> entries = new ArrayList<Entry>();  //list of entries

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_entry);

        Button saveButton = (Button) findViewById(R.id.save);


        station = (EditText) findViewById(R.id.Station);
        odometer = (EditText) findViewById(R.id.Odometer);
        grade = (EditText) findViewById(R.id.FuelGrade);
        amount = (EditText) findViewById(R.id.FuelAmount);
        cost = (EditText) findViewById(R.id.FuelUnitCost);
        date = (EditText) findViewById(R.id.Date);


        //For when the user saves a new entry, call the Entry class constructor
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                setResult(RESULT_OK);
                String newdate = date.getText().toString();
                String stat = station.getText().toString();
                String odo = odometer.getText().toString();
                String fgrade = grade.getText().toString();
                String famount = amount.getText().toString();
                String fcost = amount.getText().toString();


               // from http://stackoverflow.com/questions/8705017/convert-string-to-float for float conversion
                Entry entry = new Entry(newdate, stat, Float.valueOf(odo), fgrade, Float.valueOf(famount),Float.valueOf(fcost));
                entries.add(entry);
                saveInFile();
                //saveInFile(text, new Date(System.currentTimeMillis()));
                //finish();

            }
        });

    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(entries, out);
            out.flush();
            fos.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

        Intent intent = new Intent(NewEntryActivity.this, ViewLogActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);


    }

}
