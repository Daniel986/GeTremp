package com.getremp.daniel_lael.getremp.GroupSelect;

import android.net.Uri;

public class Group {

    private String name;
    private Uri imageUri;

    public Group(String name, long id, Uri imageUri) {
        this.name = name;
        this.imageUri = imageUri;
    }

    public Group(String name, long id) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

}
