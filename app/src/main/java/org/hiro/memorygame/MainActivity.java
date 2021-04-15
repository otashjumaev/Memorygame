package org.hiro.memorygame;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.hiro.memorygame.controlller.FragmentController;
import org.hiro.memorygame.fragments.MainMenuFragment;

public class MainActivity extends AppCompatActivity implements MainMenuFragment.OnMusicListener {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentController.init(getSupportFragmentManager(), R.id.container);
        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        FragmentController.getController().open(mainMenuFragment);
        playMusic();
        mainMenuFragment.setOnMusicListener(this);
    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.grasswalk);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void pauseMusic() {
        mediaPlayer.pause();
    }

    private void stopMusic() {
        mediaPlayer.stop();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMusic(View view, TextView musicText) {
        if (mediaPlayer.isPlaying()) {
            pauseMusic();
            musicText.setText("Off");
        } else {
            playMusic();
            musicText.setText("On");
        }
    }

    @Override
    protected void onDestroy() {
        stopMusic();
        super.onDestroy();
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        if (FragmentController.getController().countFragment() > 1)
            FragmentController.getController().close();
        else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setPositiveButton("Exit", (dialog1, which) -> finishAffinity());
            dialog.setMessage("Do you want exit?");
            dialog.setTitle("Exit");
            dialog.setNegativeButton("Cancel", (dialog1, which) -> dialog.setCancelable(true));
            dialog.show();
        }

    }

}
