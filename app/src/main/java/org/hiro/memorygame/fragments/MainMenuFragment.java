package org.hiro.memorygame.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import org.hiro.memorygame.R;
import org.hiro.memorygame.controlller.FragmentController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainMenuFragment extends Fragment {

    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.settings)
    ImageView settings;
    @BindView(R.id.about)
    ImageView about;
    @BindView(R.id.music)
    ImageView music;
    @BindView(R.id.mode)
    ImageView mode;
    @BindView(R.id.music_text)
    TextView musicText;
    @BindView(R.id.mode_text)
    TextView modeText;

    private Unbinder unbinder;
    private int count = 0;
    private OnMusicListener onMusicListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        play.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("level", 0);
            LevelsFragment menuFragment = new LevelsFragment();
            menuFragment.setArguments(bundle);
            FragmentController.getController().open(menuFragment);
        });
        settings.setOnClickListener(this::onClicked);
        music.setVisibility(View.GONE);
        mode.setVisibility(View.GONE);
        musicText.setVisibility(View.GONE);
        modeText.setVisibility(View.GONE);

        music.setOnClickListener(v -> {
            if (onMusicListener != null)
                onMusicListener.onMusic(v, musicText);
        });
        about.setOnClickListener(v -> {
            AboutFragment aboutFragment = new AboutFragment();
            FragmentController.getController().open(aboutFragment);
        });

    }

    private void onClicked(View view) {

        if (count == 0) {
            music.animate().translationY(-30).setDuration(200).start();
            musicText.animate().translationY(-30).setDuration(200).start();
            music.setVisibility(View.VISIBLE);
            musicText.setVisibility(View.VISIBLE);
            musicText.animate().alpha(1).setDuration(200).start();

            mode.animate().translationY(-30).setDuration(200).start();
            modeText.animate().translationY(-30).setDuration(200).start();
            mode.setVisibility(View.VISIBLE);
            modeText.setVisibility(View.VISIBLE);
            modeText.animate().alpha(1).setDuration(200).start();
        } else if (count == 1) {
            music.animate().alpha(0).setDuration(200).start();
            music.animate().translationY(20).setDuration(200).start();
            musicText.animate().translationY(20).setDuration(200).start();
            musicText.animate().alpha(0).setDuration(200).start();

            mode.animate().alpha(0).setDuration(200).start();
            mode.animate().translationY(20).setDuration(200).start();
            modeText.animate().translationY(20).setDuration(200).start();
            modeText.animate().alpha(0).setDuration(200).start();
        } else if (count == 2) {

            musicText.animate().alpha(1).setDuration(200).start();
            musicText.animate().translationY(-30).setDuration(200).start();
            music.animate().translationY(-30).setDuration(200).start();
            music.animate().alpha(1).setDuration(200).start();

            modeText.animate().alpha(1).setDuration(200).start();
            modeText.animate().translationY(-30).setDuration(200).start();
            mode.animate().translationY(-30).setDuration(200).start();
            mode.animate().alpha(1).setDuration(200).start();

            count = 0;
        }
        count++;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.UP, enter, 300);
        } else return CubeAnimation.create(CubeAnimation.UP, enter, 300);
    }

    public void setOnMusicListener(OnMusicListener onMusicListener) {
        this.onMusicListener = onMusicListener;
    }


    public interface OnMusicListener {
        void onMusic(View view, TextView musicText);
    }
}
