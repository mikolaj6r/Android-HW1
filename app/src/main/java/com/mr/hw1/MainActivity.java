package com.mr.hw1;

import android.content.Intent;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String SOUND_ID = "soundId";
    public static final String CONTACT_ID = "contactName";
    public static final int SOUND_REQUEST = 1;
    public static final int CONTACT_REQUEST = 2;
    private int current_sound = 0;
    private int current_contact = 0;

    private MediaPlayer buttonPlayer;
    static public Uri[] sounds;
    boolean isPlayed=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonsSetListeners();

        sounds = new Uri[4];
        sounds[0] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring01);
        sounds[1] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring02);
        sounds[2] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring03);
        sounds[3] = Uri.parse("android.resource://" + getPackageName() + "/" +
                R.raw.ring04);

        buttonPlayer = new MediaPlayer();
        buttonPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            buttonPlayer.setDataSource(getApplicationContext(),sounds[current_sound]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.start();
                isPlayed = true;
            }
        });

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPlayed) {
                    buttonPlayer.prepareAsync();
                    fab.setImageResource(android.R.drawable.ic_media_pause);
                    Snackbar.make(view, "Music played", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    buttonPlayer.stop();
                    fab.setImageResource(android.R.drawable.ic_media_play);
                    Snackbar.make(view, "Music stopped", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    isPlayed=false;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(requestCode == SOUND_REQUEST){
                current_sound = data.getIntExtra(SOUND_ID,0);
                buttonPlayer.reset();
                try {
                    View parentLayout = findViewById(android.R.id.content);
                    buttonPlayer.setDataSource(getApplicationContext(),sounds[current_sound]);
                    Snackbar.make(parentLayout, "Music changed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
            else if(requestCode == CONTACT_REQUEST){
                TextView name = (TextView) findViewById(R.id.textView);
                if(name != null){
                    current_contact = data.getIntExtra(CONTACT_ID, 0);
                    TypedArray names = getResources().obtainTypedArray(R.array.contacts);
                    name.setText(names.getString(current_contact));
                }

                ImageView contactImg = (ImageView) findViewById(R.id.contactImg);
                if(contactImg != null){
                    final int n = new Random().nextInt(16);
                    TypedArray avatars = getResources().obtainTypedArray(R.array.avatars);
                    contactImg.setImageDrawable(avatars.getDrawable(n));
                }
            }
        }
        else if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(),getText(R.string.back_message),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void buttonsClickHandler(View view){
        if(isPlayed) {
            final FloatingActionButton fab = findViewById(R.id.fab);
            buttonPlayer.stop();
            fab.setImageResource(android.R.drawable.ic_media_play);
            isPlayed = false;
            Snackbar.make(view, "Music stopped", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        switch(view.getId()){
            case R.id.btn_changeContact:
                Intent contactPick = new Intent(getApplicationContext(), ChangeContact.class);
                contactPick.putExtra(CONTACT_ID, current_contact);
                startActivityForResult(contactPick, CONTACT_REQUEST);
                break;
            case R.id.btn_changeSound:
                Intent soundPick = new Intent(getApplicationContext(), ChangeSound.class);
                soundPick.putExtra(SOUND_ID, current_sound);
                startActivityForResult(soundPick, SOUND_REQUEST);
                break;
            default:

        }
    }

    public void buttonsSetListeners(){
        Button changeContact = (Button) findViewById(R.id.btn_changeContact);
        Button changeSound = (Button) findViewById(R.id.btn_changeSound);

        View.OnClickListener listener  = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                buttonsClickHandler(view);
            }
        };
        if(changeContact != null)
            changeContact.setOnClickListener(listener);
        if(changeSound != null)
            changeSound.setOnClickListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        buttonPlayer.stop();
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(android.R.drawable.ic_media_play);
        isPlayed = false;
    }



}
