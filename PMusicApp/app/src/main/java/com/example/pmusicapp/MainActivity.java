package com.example.pmusicapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView txtName, txtCurrentTime, txtTotalTime;
    ImageButton btnShuffle, btnPrev, btnPlay, btnNext, btnRep;
    SeekBar seekBar;
    MediaPlayer mediaPlayer = new MediaPlayer();
    ArrayList<Song> arrayListSong = new ArrayList<>();
    String[] supportedExtensions = {".mp3", ".m4a"};
    int pos=0;

    private static final int MY_PERMISSION_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check for this stupid permission :)
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
        }
        findView();
        createDrawerToggle();

        arrayListSong = readSongs(Environment.getExternalStorageDirectory());
//        for (int i=0; i< arrayListSong.size(); i++){
//            Toast.makeText(this, ""+ arrayListSong.get(i), Toast.LENGTH_SHORT).show();
//        }
        try {
            mediaPlayer.setDataSource(arrayListSong.get(pos).getFile());
            mediaPlayer.prepare();
            txtName.setText(arrayListSong.get(pos).getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.stop);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    protected void findView(){
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        txtName = findViewById(R.id.textViewName);
        txtCurrentTime = findViewById(R.id.textViewCurrentTime);
        txtTotalTime = findViewById(R.id.textViewTotalTime);
        btnShuffle = findViewById(R.id.imageButtonShuffle);
        btnPrev = findViewById(R.id.imageButtonPrev);
        btnPlay = findViewById(R.id.imageButtonPlay);
        btnNext = findViewById(R.id.imageButtonNext);
        btnRep = findViewById(R.id.imageButtonRep);
        seekBar = findViewById(R.id.seekBar);
    }

//    private void loadMusic() {
//        ContentResolver contentResolver = getContentResolver();
//        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
//
//        if (songCursor != null && songCursor.moveToFirst()){
//            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
//            int songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
//
//            do{
//                arrayListSong.add(songCursor.getString(songPath));
//            }while (songCursor.moveToNext());
//        }
//    }[]jui

    private ArrayList<Song> readSongs(File root){
        ArrayList<Song> temp = new ArrayList<>();
        File files[] = root.listFiles();

        for (File f:files){
            if (f.isDirectory()){
                temp.addAll(readSongs(f));
            }else{
                for (int i=0; i < supportedExtensions.length; i++){
                    if (f.getName().endsWith(supportedExtensions[i])){
                        temp.add(new Song(f.getName(), f.getAbsolutePath()));
                    }
                }

            }
        }
        return temp;
    }

    private void createDrawerToggle() {
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission granted" , Toast.LENGTH_SHORT).show();
                        // do stuff
                    } else{
                        Toast.makeText(this, "Still no permission! WTF?", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



//    public void logout(View view){
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(), Login.class));
//        finish();
//    }
}
