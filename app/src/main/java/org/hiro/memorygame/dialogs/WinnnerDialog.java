package org.hiro.memorygame.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import org.hiro.memorygame.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class WinnnerDialog extends AlertDialog {

    public CircleImageView levels;
    public CircleImageView tryAgain;
    public CircleImageView nextLevel;
    private ImageView staricon1;
    private ImageView staricon2;
    private ImageView staricon3;

    public WinnnerDialog(@NonNull Context context, int starCount) {
        super(context);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.simple_dialog, null, false);
        levels = view.findViewById(R.id.all_level);
        tryAgain = view.findViewById(R.id.try_again);
        nextLevel = view.findViewById(R.id.next_level);
        staricon1 = view.findViewById(R.id.staricon1);
        staricon2 = view.findViewById(R.id.staricon2);
        staricon3 = view.findViewById(R.id.staricon3);
        staricon3.setVisibility(starCount > 2 ? View.VISIBLE : View.INVISIBLE);
        staricon2.setVisibility(starCount > 1 ? View.VISIBLE : View.INVISIBLE);
        staricon1.setVisibility(starCount > 0 ? View.VISIBLE : View.INVISIBLE);
        staricon1.animate().scaleX(3).scaleY(3).setDuration(3000).start();
        staricon1.animate().scaleX(-1).scaleY(-1).rotationX(180).setDuration(3000).start();
        staricon2.animate().scaleX(3).scaleY(3).setDuration(3000).start();
        staricon2.animate().scaleX(-1).scaleY(-1).rotationX(180).setDuration(3000).start();
        staricon3.animate().scaleX(3).scaleY(3).setDuration(3000).start();
        staricon3.animate().scaleX(-1).scaleY(-1).rotationX(180).setDuration(3000).start();

        setView(view);
    }

}
