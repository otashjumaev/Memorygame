package org.hiro.memorygame.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import org.hiro.memorygame.R;

public class SimpleDialog_Game extends AlertDialog {

    private ImageView staricon1;
    private ImageView staricon2;
    private ImageView staricon3;

    public SimpleDialog_Game(@NonNull Context context) {
        super(context);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.simple_dialog, null, false);

        staricon1 = view.findViewById(R.id.staricon1);
        staricon2 = view.findViewById(R.id.staricon2);
        staricon3 = view.findViewById(R.id.staricon3);
        staricon1.animate().scaleX(3).scaleY(3).setDuration(3000).start();
        staricon1.animate().scaleX(-1).scaleY(-1).rotationX(180).setDuration(3000).start();

        staricon2.animate().scaleX(3).scaleY(3).setDuration(3000).start();
        staricon2.animate().scaleX(-1).scaleY(-1).rotationX(180).setDuration(3000).start();

        staricon3.animate().scaleX(3).scaleY(3).setDuration(3000).start();
        staricon3.animate().scaleX(-1).scaleY(-1).rotationX(180).setDuration(3000).start();

        setView(view);
    }

}
