package com.tangotkk.segdialog.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tangotkk.segdialog.builder.DialogBuilder;
import com.tangotkk.segdialog.holder.LayoutItemHolder;
import com.tangotkk.segdialog.holder.ViewItemHolder;

public class MainActivity extends AppCompatActivity {

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
                View textView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_text, null, false);
                new DialogBuilder(MainActivity.this)
                        .addItem(new LayoutItemHolder(R.layout.item_title) {
                            @Override
                            public void onViewClick(Dialog dialog, View view) {
                                showShortToast("Title clicked");
                            }

                            @Override
                            public View createView(LayoutInflater inflater, ViewGroup parent) {
                                View view = super.createView(inflater, parent);
                                TextView tv = (TextView) view.findViewById(R.id.title_tv);
                                tv.setText("SegDialog sample");
                                return view;
                            }



                        })
                        .addItem(new ViewItemHolder(textView) {
                            @Override
                            public void onViewClick(Dialog dialog, View view) {
                                showShortToast("Text clicked");
                            }

                            @Override
                            public View createView(LayoutInflater inflater, ViewGroup parent) {
                                View view =  super.createView(inflater, parent);
                                TextView tv = (TextView) view.findViewById(R.id.content_tv);
                                tv.setText("SegDialog text");
                                return view;
                            }
                        })
                        .addItem(new LayoutItemHolder(R.layout.item_footer) {
                            @Override
                            public void onViewClick(Dialog dialog, View view) {
                                showShortToast("footer clicked");
                            }

                            @Override
                            public void bindEvent(final Dialog dialog, View view) {
                                super.bindEvent(dialog, view);
                                View  left = view.findViewById(R.id.left_btn);
                                View right = view.findViewById(R.id.right_btn);
                                left.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showShortToast("Cancel");
                                        dialog.dismiss();
                                    }
                                });
                                right.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showShortToast("Confirm");
                                        dialog.dismiss();
                                    }
                                });
                            }
                        })
                        .setBackgroundRes(R.drawable.dialog_back)
                        .setHorizontalMargin(0, 0)
                        .buildAndShow();

            }
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

    private void showShortToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
