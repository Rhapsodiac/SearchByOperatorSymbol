package co.bhcc.googlelikeapro;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IconFragment.OnDataPass,IconHelpFragment.OnDataPass {

    private SearchFragment frag;
    private PageAdapter mPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPageAdapter = new PageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container2);
        mViewPager.setAdapter(mPageAdapter);

        //get database
        OperatorList db = new OperatorList(getApplicationContext());
        final ArrayList<Operator> opList = db.getOperators();

        //get data from table containing operator buttons when clicked
        TableLayout table =(TableLayout) findViewById(R.id.operTable);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                EditText bar = (EditText)findViewById(R.id.etSearch);
                String searchText = bar.getText().toString();
                String query = "http://www.google.com/search?q=" + searchText;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(query));
                startActivity(browserIntent);

            }

            //public void onClick(View view) {
            //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //            .setAction("Action", null).show();
           // }
        });

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
    public void onDataPass(String data) {
        Log.i("DATAPASS", data);
        EditText bar = (EditText)findViewById(R.id.etSearch);
        String search = bar.getText().toString();
        search += " " + data;
        bar.setText(search);
        bar.setSelection(search.length());
    }
}
