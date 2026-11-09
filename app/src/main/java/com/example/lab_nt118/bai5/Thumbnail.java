package com.example.lab_nt118.bai5;

import com.example.lab_nt118.R;

public enum Thumbnail {
    BUN_CHA("Bún Chả", R.drawable.thumb_buncha),
    COM_TAM("Cơm Tấm", R.drawable.thumb_comtam),
    PHO_BO("Phở Bò", R.drawable.thumb_phobo),
    BANH_MI("Bánh Mì", R.drawable.thumb_banhmi);

    private final String name;
    private final int imageResId;

    Thumbnail(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    @Override
    public String toString() {
        return name;
    }
}