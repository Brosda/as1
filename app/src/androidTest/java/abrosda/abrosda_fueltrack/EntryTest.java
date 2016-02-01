package abrosda.abrosda_fueltrack;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by abrosda on 1/31/16.
 */
public class EntryTest extends ActivityInstrumentationTestCase2  {

    public EntryTest() {
        super(MainActivity.class);
    }

    public void testsetTotal() {
        Entry entry = new Entry();

        entry.setTotal();
        assertEquals(entry.getTotal(), 25.0);


    }
}
