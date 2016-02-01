package com.cml.frame.rx_volley;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cml.lib.rxvolley.rxvolley.RxRequest;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    String url = "https://raw.githubusercontent.com/cml8655/note/master/data/test.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RxRequest.<MyModel>post(url, MyModel.class).subscribe(new Subscriber<MyModel>() {
                    @Override
                    public void onCompleted() {
                        Log.i("RxRequest", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RxRequest", "onError", e);
                    }

                    @Override
                    public void onNext(MyModel myModel) {
                        Log.i("RxRequest", "onNext==>" + myModel);
                    }
                });
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void cancelRequest(View v) {
        Toast.makeText(this,"cancel",Toast.LENGTH_LONG).show();
        RxRequest.cancel(url);
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
}
