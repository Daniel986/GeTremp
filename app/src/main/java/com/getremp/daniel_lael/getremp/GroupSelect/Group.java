package com.getremp.daniel_lael.getremp.GroupSelect;

import android.net.Uri;

public class Group {

    private String name;
    private long id;
    private Uri imageUri;

    public Group(String name, long id, Uri imageUri) {
        this.name = name;
        this.id = id;
        this.imageUri = imageUri;
    }

    public Group(String name, long id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

}
