package org.hiro.memorygame.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.hiro.memorygame.R;
import org.hiro.memorygame.controlller.FragmentController;
import org.hiro.memorygame.database.DataBase;
import org.hiro.memorygame.dialogs.WinnnerDialog;
import org.hiro.memorygame.models.PhotosData;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LevelOneFragment extends Fragment {
    @BindView(R.id.parents)
    ViewGroup viewGroup;
    private Unbinder unbinder;
    private ArrayList<Integer> imagesDrawableData;
    private ArrayList<PhotosData> images;
    private int count = 0;
    private ImageView[] clickViews = new ImageView[2];
    private View view;
    private int level;
    private int timeCount = 0;
    private TextView levelView;
    private TextView timeView;
    private int showViewsTime;
    private int lastStarCount;
    private TextView winTime;
    private LinearLayout winStarsShow;
    private CountDownTimer timer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        orientation :: 1 portrait, 2 landspace
        level = getArguments().getInt("level");
        lastStarCount = getArguments().getInt("star");
        //birinchi if/else da orientatsiyani aniqlash
        //ikkinchi if/else da levelga qarab layout tanlash
        if (getResources().getConfiguration().orientation == 2) {
            if (level >= 1 && level <= 3)
                view = inflater.inflate(R.layout.fragment_level_1_land, container, false);
            else if (level >= 4 && level <= 6)
                view = inflater.inflate(R.layout.fragment_level_2_landspace, container, false);
            else if (level >= 7 && level <= 9)
                view = inflater.inflate(R.layout.fragment_level_3_landspace, container, false);
        } else {
            if (level >= 1 && level <= 3)
                view = inflater.inflate(R.layout.fragment_level_1_portrait, container, false);
            else if (level >= 4 && level <= 6)
                view = inflater.inflate(R.layout.fragment_level_2_portrait, container, false);
            else if (level >= 7 && level <= 9)
                view = inflater.inflate(R.layout.fragment_level_3_portrait, container, false);

        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        ButterKnife.bind(this, view);
        loadImagesData();
        shuffleImagesData();
        loadViews();
        loadDataToViews();
        levelView.setText("Level : " + level);
        setShowViewsTime(level);
        hideOriginPhotos();
        setTime();

    }

    private void setTime() {
        new CountDownTimer(getShowViewsTime(), 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                showPrize(timeCount);
            }

            @Override
            public void onFinish() {
                timer = new CountDownTimer(999999999, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timeView.setText(timeCount + "");
                        showPrize(timeCount);
                        timeCount++;
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        }.start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public int getShowViewsTime() {
        return showViewsTime;
    }

    public void setShowViewsTime(int level) {
        if (level <= 3) showViewsTime = 5000;
        else if (level <= 6) showViewsTime = 3000;
        else showViewsTime = 2000;
    }

    private void hideOriginPhotos() {
        new CountDownTimer(getShowViewsTime(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                for (int i = 0; i < images.size(); i++) {
                    hideOriginalPhoto(images.get(i).getImageView());
                }
                setClickViews();
            }
        }.start();
    }

    private void hideOriginalPhoto(ImageView imageView) {
        imageView.animate()
                .rotationYBy(90)
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (imageView.getRotationY() < 180) {
                            imageView.setImageResource(R.drawable.market);
                            imageView.animate().rotationYBy(90);
                        } else imageView.setRotationY(0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    private void setClickViews() {
        for (int i = 0; i < images.size(); i++) {
            images.get(i).getImageView().setOnClickListener(this::clickView);
        }
    }


    private void clickView(View view) {
        for (int i = 0; i < images.size(); i++) {
            if (!images.get(i).isCheck()) images.get(i).getImageView().setEnabled(false);
        }
        if (count == 0) clickViews[0] = (ImageView) view;
        if (count == 1) clickViews[1] = (ImageView) view;
        if (count == 1 && !clickViews[0].toString().equals(clickViews[1].toString())) checkViews();
        count++;
        if (count == 2 && clickViews[0].toString().equals(clickViews[1].toString())) {
            hideOriginalPhoto(clickViews[0]);
            count = 0;
            for (int i = 0; i < images.size(); i++) {
                if (!images.get(i).isCheck()) images.get(i).getImageView().setEnabled(true);
            }
        } else {
            view.setRotationY(-180);
            ImageView imageView = (ImageView) view;
            view.animate().rotationYBy(90).setDuration(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (view.getRotationY() < 0) {
                        imageView.setImageResource(imagesDrawableData.get((Integer) view.getTag() / 100));
                        imageView.animate().rotationYBy(90);
                        if (count != 2) {
                            for (int i = 0; i < images.size(); i++) {
                                if (!images.get(i).isCheck())
                                    images.get(i).getImageView().setEnabled(true);
                                else if (images.get(i).isCheck())
                                    images.get(i).getImageView().setEnabled(false);
                            }
                        }
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

    private void checkViews() {
        new CountDownTimer(600, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if ((int) clickViews[0].getTag() / 100 == (int) clickViews[1].getTag() / 100) {
                    images.get((Integer) clickViews[0].getTag() % 100).setCheck(true);
                    images.get((Integer) clickViews[0].getTag() % 100).getImageView().setVisibility(View.INVISIBLE);
                    images.get((Integer) clickViews[1].getTag() % 100).setCheck(true);
                    images.get((Integer) clickViews[1].getTag() % 100).getImageView().setVisibility(View.INVISIBLE);
                    checkWin();
                } else {
                    hideOriginalPhoto(clickViews[0]);
                    hideOriginalPhoto(clickViews[1]);
                }
                for (int i = 0; i < images.size(); i++) {
                    if (images.get(i).isCheck()) images.get(i).getImageView().setEnabled(false);
                    else if (!images.get(i).isCheck()) {
                        images.get(i).getImageView().setEnabled(true);
                    }
                }
                count = 0;
            }
        }.start();
    }

    private void showPrize(int timeCount) {
        for (int i = 0; i < winStarsShow.getChildCount(); i++) {
            winStarsShow.getChildAt(i).setVisibility(getStars(level) > i ? View.VISIBLE : View.INVISIBLE);
        }
        if (getWinTime(level) != -1) winTime.setText(getWinTime(level) + "");
        else winTime.setVisibility(View.INVISIBLE);
    }

    private void checkWin() {
        int k = 0;
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).isCheck()) k++;
            else break;
        }
        if (k == images.size()) {

            timer.cancel();
            if (getStars(level) > lastStarCount)
                DataBase.getDataBase().setStars(level, getStars(level));
            WinnnerDialog dialog = new WinnnerDialog(getContext(), getStars(level));
            if (level > 8) dialog.nextLevel.setVisibility(View.INVISIBLE);
            dialog.levels.setOnClickListener(v -> {
                FragmentController.getController().close();
                FragmentController.getController().close();
                FragmentController.getController().open(new LevelsFragment());
                dialog.cancel();
            });
            dialog.nextLevel.setOnClickListener(v -> {
                FragmentController.getController().close();
                LevelOneFragment fragment = new LevelOneFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("level", level + 1);
                fragment.setArguments(bundle);
                FragmentController.getController().open(fragment);
                dialog.cancel();
            });
            dialog.tryAgain.setOnClickListener(v -> {
                FragmentController.getController().close();
                LevelOneFragment fragment = new LevelOneFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("level", level);
                fragment.setArguments(bundle);
                FragmentController.getController().open(fragment);
                dialog.cancel();

                dialog.cancel();
            });
            dialog.show();
        }
    }

    private int getStars(int level) {
        if (level == 1) {
            if (timeCount < 25) return 3;
            else if (timeCount < 30) return 2;
            else return 1;
        } else if (level == 2) {
            if (timeCount < 20) return 3;
            else if (timeCount < 25) return 2;
            else return 1;
        } else if (level == 3) {
            if (timeCount < 15) return 3;
            else if (timeCount < 20) return 2;
            else return 1;
        } else if (level == 4) {
            if (timeCount < 80) return 3;
            else if (timeCount < 125) return 2;
            else return 1;
        } else if (level == 5) {
            if (timeCount < 70) return 3;
            else if (timeCount < 105) return 2;
            else return 1;
        } else if (level == 6) {
            if (timeCount < 60) return 3;
            else if (timeCount < 90) return 2;
            else return 1;
        } else if (level == 7) {
            if (timeCount < 180) return 3;
            else if (timeCount < 240) return 2;
            else return 1;
        } else if (level == 8) {
            if (timeCount < 160) return 3;
            else if (timeCount < 220) return 2;
            else return 1;
        } else {
            if (timeCount < 60) return 3;
            else if (timeCount < 80) return 2;
            else return 1;
        }
    }

    private int getWinTime(int level) {
        if (level == 1) {
            if (timeCount <= 25) return 25;
            else if (timeCount <= 30) return 30;
            else return -1;
        } else if (level == 2) {
            if (timeCount <= 20) return 20;
            else if (timeCount <= 25) return 25;
            else return -1;
        } else if (level == 3) {
            if (timeCount <= 15) return 15;
            else if (timeCount <= 20) return 20;
            else return 1;
        } else if (level == 4) {
            if (timeCount <= 80) return 80;
            else if (timeCount <= 125) return 125;
            else return -1;
        } else if (level == 5) {
            if (timeCount <= 70) return 70;
            else if (timeCount <= 105) return 105;
            else return -1;
        } else if (level == 6) {
            if (timeCount <= 60) return 60;
            else if (timeCount <= 90) return 90;
            else return -1;
        } else if (level == 7) {
            if (timeCount <= 180) return 180;
            else if (timeCount <= 240) return 240;
            else return -1;
        } else if (level == 8) {
            if (timeCount <= 160) return 160;
            else if (timeCount <= 220) return 220;
            else return -1;
        } else {
            if (timeCount <= 60) return 60;
            else if (timeCount <= 80) return 80;
            else return -1;
        }
    }

    private void loadDataToViews() {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            integers.add(i);
        }
        Collections.shuffle(integers);
        int j = 0;
        for (int i = 0; i < images.size(); i++) {
            int pos = (int) images.get(integers.get(i)).getImageView().getTag();
            images.get(integers.get(i)).getImageView().setTag(j * 100 + pos);
            images.get(integers.get(i)).setImgUrl(imagesDrawableData.get(j));
            images.get(integers.get(i)).getImageView().setImageResource(imagesDrawableData.get(j));
            if (i % 2 == 1) j++;
        }
    }

    private void shuffleImagesData() {
        Collections.shuffle(imagesDrawableData);
    }

    private void loadViews() {
        images = new ArrayList<>();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            viewGroup.getChildAt(i).setTag(i);
            images.add(new PhotosData((ImageView) viewGroup.getChildAt(i)));
        }
        timeView = view.findViewById(R.id.times);
        levelView = view.findViewById(R.id.level);
        winStarsShow = view.findViewById(R.id.starsParent);
        winTime = view.findViewById(R.id.win_time);
    }

    private void loadImagesData() {
        imagesDrawableData = new ArrayList<>();
        imagesDrawableData.add(R.drawable.ikon1);
        imagesDrawableData.add(R.drawable.ikon2);
        imagesDrawableData.add(R.drawable.ikon3);
        imagesDrawableData.add(R.drawable.ikon4);
        imagesDrawableData.add(R.drawable.ikon5);
        imagesDrawableData.add(R.drawable.ikon6);
        imagesDrawableData.add(R.drawable.ikon7);
        imagesDrawableData.add(R.drawable.ikon8);
        imagesDrawableData.add(R.drawable.ikon9);
        imagesDrawableData.add(R.drawable.ikon10);
        imagesDrawableData.add(R.drawable.ikon11);
        imagesDrawableData.add(R.drawable.ikon12);
        imagesDrawableData.add(R.drawable.ikon13);
        imagesDrawableData.add(R.drawable.ikon14);
        imagesDrawableData.add(R.drawable.ikon15);
        imagesDrawableData.add(R.drawable.ikon16);
        imagesDrawableData.add(R.drawable.ikon17);
        imagesDrawableData.add(R.drawable.ikon18);
        imagesDrawableData.add(R.drawable.ikon19);
        imagesDrawableData.add(R.drawable.ikon20);
    }
}


