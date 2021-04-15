package org.hiro.memorygame.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.hiro.memorygame.R;
import org.hiro.memorygame.controlller.FragmentController;
import org.hiro.memorygame.database.DataBase;
import org.hiro.memorygame.models.LevelData;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LevelsFragment extends Fragment {
    private FrameLayout[] button = new FrameLayout[9];
    private TextView[] textView = new TextView[9];
    private LinearLayout[] linearLayout = new LinearLayout[9];
    private Unbinder unbinder;
    private LevelOneFragment fragment;
    private ArrayList<LevelData> data = DataBase.getDataBase().getLevels();
    private ImageView star;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_level, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        fragment = new LevelOneFragment();
        loadView(view);
        setLevels();
        setClickButtons();
    }

    @SuppressLint("SetTextI18n")
    private void setLevels() {
        for (int i = 0; i < 9; i++) {
            if (data.get(i).isExist()) {
                textView[i].setText("" + (i + 1));
                for (int j = 0; j < linearLayout[i].getChildCount(); j++) {
                    star = (ImageView) linearLayout[i].getChildAt(j);
                    if (data.get(i).getStarCount() > j)
                        star.setImageResource(R.drawable.ic_star_black_24dp);
                }
            } else {
                linearLayout[i].setVisibility(View.INVISIBLE);
                textView[i].setText("");
                textView[i].setBackgroundResource(R.drawable.ic_lock_outline_black_24dp);
            }
        }
    }

    private void setClickButtons() {
        for (int i = 0; i < 9; i++) {
            button[i].setOnClickListener(this::click);
        }
    }

    private void loadView(View view) {
        for (int i = 0; i < 9; i++) {
            button[i] = view.findViewById(getResources().getIdentifier("button" + (i / 3 + 1) + "_" + (i % 3 + 1), "id", getContext().getPackageName()));
            button[i].setTag(i + 1);
            textView[i] = view.findViewById(getResources().getIdentifier("text" + (i / 3 + 1) + "_" + (i % 3 + 1), "id", getContext().getPackageName()));
            linearLayout[i] = view.findViewById(getResources().getIdentifier("stars_level_" + (i / 3 + 1) + "_" + (i % 3 + 1), "id", getContext().getPackageName()));
        }
    }

    private void click(View view) {
        Bundle bundle = new Bundle();
        int selectLevel = (Integer) view.getTag();
        bundle.putInt("level", selectLevel);
        bundle.putInt("star", data.get(selectLevel).getStarCount());
        fragment.setArguments(bundle);
        FragmentController.getController().open(fragment);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
