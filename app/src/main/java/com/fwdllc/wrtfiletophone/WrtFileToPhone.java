package com.fwdllc.wrtfiletophone;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.view.View.GONE;

public class WrtFileToPhone extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button wrtbutton;
    Button readbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrt_file_to_phone);

        editText = (EditText) findViewById(R.id.vimText);
        textView = (TextView) findViewById(R.id.viewText);
        textView.setVisibility(GONE);
    }


    public void write_file(View view) throws FileNotFoundException {
        String msg = editText.getText().toString();



//        File file = new File(getFilesDir(), "FTC_Battery");
        File file = new File(getFilesDir(), "FTC_TeleData");
        File fdir = getFilesDir();

        try {
            FileOutputStream  fileOutputStream = openFileOutput("FTC_TeleData", MODE_WORLD_READABLE);
//            FileOutputStream  fileOutputStream = openFileOutput(String.valueOf(file), MODE_APPEND);
            fileOutputStream.write(msg.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "message saved!", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), fdir.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), getFileStreamPath("FTC_TeleData").toString(), Toast.LENGTH_LONG).show();

            editText.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void read_file(View view) {
        try {
            String message;
            FileInputStream fileInputStream = openFileInput("FTC_TeleData");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            while ((message = bufferedReader.readLine()) != null) {
                stringBuffer.append(String.format("%s\n", message));
            }
            textView.setText(stringBuffer.toString());
            textView.setVisibility(View.VISIBLE);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wrt_file_to_phone, menu);
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
