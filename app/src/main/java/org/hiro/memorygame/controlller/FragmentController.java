package org.hiro.memorygame.controlller;


import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentController {
    private static FragmentController controller;
    private FragmentManager manager;
    private int resId;

    private FragmentController(FragmentManager manager, int resId) {
        this.manager = manager;
        this.resId = resId;
    }

    public static void init(FragmentManager fragmentManager, @IdRes int resId) {
        controller = new FragmentController(fragmentManager, resId);
    }

    public static FragmentController getController() {
        return controller;
    }

    public void open(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(fragment.toString());
        transaction.replace(resId, fragment);
        transaction.commit();
    }

    public int countFragment() {
        return manager.getBackStackEntryCount();

    }

    public void close() {
        manager.popBackStack();
    }
}
