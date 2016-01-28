package abrosda.abrosda_fueltrack;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class NewEntryActivity extends ActionBarActivity {

    protected EditText station, odometer, grade, amount, cost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_entry);

        station = (EditText) findViewById(R.id.Station);
        odometer = (EditText) findViewById(R.id.Odometer);
        grade = (EditText) findViewById(R.id.FuelGrade);
        amount = (EditText) findViewById(R.id.FuelAmount);
        cost = (EditText) findViewById(R.id.FuelUnitCost);

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



    //For when the user saves a new entry, call the Entry class constructor
    public void save(View view) {

        setResult(RESULT_OK);
        String station = station.getText().toString;


    }

    public void cancel(View view) {

    }
}
