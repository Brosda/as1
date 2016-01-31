/*Written by Alex Brosda
* Assignment 1 cmpt 301
* 1428272
* Fuel Log*/

package abrosda.abrosda_fueltrack;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    protected EditText date, station, odometer, grade, amount, cost; //All editable fields
    private ListView oldEntryList; //List for listView
    private TextView totaltext;  //total cost of all entries
    private static final String FILENAME = "file.sav";  //savefile for GSON
    private ArrayList<Entry> entries = new ArrayList<Entry>();  //list of entries
    private ArrayAdapter<Entry> adapter;
    private float total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up a;; buttons listviews and textviews
        oldEntryList = (ListView) findViewById(R.id.oldEntriesList);
        totaltext = (TextView) findViewById(R.id.textView);

        final Button saveButton = (Button) findViewById(R.id.save);
        Button cancelButton = (Button) findViewById(R.id.cancel);
        final Button editButton = (Button) findViewById(R.id.edit);

        //sets the variables to the corresponding user text
        station = (EditText) findViewById(R.id.Station);
        odometer = (EditText) findViewById(R.id.Odometer);
        grade = (EditText) findViewById(R.id.FuelGrade);
        amount = (EditText) findViewById(R.id.FuelAmount);
        cost = (EditText) findViewById(R.id.FuelUnitCost);
        date = (EditText) findViewById(R.id.Date);

        //prints the total
        total = getTotalCost();
        totaltext.setText("Total cost = $" + Float.toString(total));

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //try to handle inconpatible typings or blanks
                try {
                    setResult(RESULT_OK);
                    String newdate = date.getText().toString();
                    String stat = station.getText().toString();
                    String odo = odometer.getText().toString();
                    String fgrade = grade.getText().toString();
                    String famount = amount.getText().toString();
                    String fcost = cost.getText().toString();


                    // from http://stackoverflow.com/questions/8705017/convert-string-to-float for float conversion
                    Entry entry = new Entry(newdate, stat, Float.valueOf(odo),
                            fgrade, Float.valueOf(famount), Float.valueOf(fcost));
                    entries.add(entry);
                    total = getTotalCost();
                    totaltext.setText("Total cost = $" + String.format("%.2f", total));
                    adapter.notifyDataSetChanged();

                    saveInFile();
                }catch(Exception err){
                    //This code was learned and modified from
                    // http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Error invalid input(s)");
                    builder1.setCancelable(true);

                    AlertDialog alert1 = builder1.create();
                    alert1.show();

                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                //sets the text fields back to nothing, so help text is displayed
                station.setText("");
                date.setText("");
                odometer.setText("");
                grade.setText("");
                amount.setText("");
                cost.setText("");
                //clear choice from list
                oldEntryList.clearChoices();
                adapter.notifyDataSetChanged();


            }
        });

        //Do nothing while no entry is selected
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


            }
        });

             oldEntryList.setOnItemClickListener(new AdapterView.OnItemClickListener()

             {

                 @Override
                 public void onItemClick (AdapterView < ? > parent, View view,
                 int position, long id){
                    //tracks which item on list is selected
                 final Entry item = (Entry) parent.getItemAtPosition(position);
                 editButton.setOnClickListener(new View.OnClickListener() {
                    //only updates when the edit button is pressed
                     public void onClick(View v) {
                         try {

                             //Sets all the values in selected entry to current text in text field
                             setResult(RESULT_OK);
                             item.setDate(date.getText().toString());
                             item.setStation(station.getText().toString());
                             item.setOdo(odometer.getText().toString());
                             item.setAmount(amount.getText().toString());
                             item.setGrade(grade.getText().toString());
                             item.setCost(cost.getText().toString());
                             item.setTotal();
                             oldEntryList.clearChoices();
                             total = getTotalCost();
                             totaltext.setText("Total cost = $" + String.format("%.2f", total));
                             adapter.notifyDataSetChanged();

                         }catch(Exception err) {
                             //This code was learned and modified from
                             // http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android

                             AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                             builder1.setMessage("Error invalid input(s)");
                             builder1.setCancelable(true);

                             AlertDialog alert1 = builder1.create();
                             alert1.show();

                         }

                     }
                 });

             }
             }

             );

         }


    //gets total cost of all entries by looping through
    public float getTotalCost() {
        float total = 0;
        for (int i = 0; i < entries.size();i++ ) {
            total += entries.get(i).getTotal();
        }
        return total;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    protected  void onStart() {
        super.onStart();
        //loads from GSON then updates list and adapter
        loadFromFile();
        adapter = new ArrayAdapter<Entry>(this, R.layout.list_item, entries);
        oldEntryList.setAdapter(adapter);

    }

    //saves to GSON, Code modified from lonely twitter app from labs
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

    }

    //loads from GSON, code taken and modified from lonely twitter app in labs
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            //Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-19 2016
            Type listType = new TypeToken<ArrayList<Entry>>() {}.getType();
            entries = gson.fromJson(in, listType);



        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //entries = new ArrayList<Entry>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }
}
