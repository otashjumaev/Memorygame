package org.hiro.memorygame.models;

import android.widget.ImageView;


public class PhotosData {
    private ImageView imageView;
    private boolean check;
    private long imgUrl;

    public PhotosData(ImageView imageView, long imgUrl) {
        this.imageView = imageView;
        this.imgUrl = imgUrl;
    }

    public PhotosData(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public long getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(long imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
