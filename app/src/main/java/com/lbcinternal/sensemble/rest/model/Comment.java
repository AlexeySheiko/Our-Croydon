package com.lbcinternal.sensemble.rest.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {

    @SerializedName("AuthorName")
    String mAuthorName;

    @SerializedName("Avatar")
    String mAvatarUrl;

    @SerializedName("Content")
    String mMessage;

    @SerializedName("DateCreated")
    Date mCreationDate;

    public Comment(String authorName, String avatarUrl, String message, Date creationDate) {
        mAuthorName = authorName;
        mAvatarUrl = avatarUrl;
        mMessage = message;
        mCreationDate = creationDate;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public String getAvatarUrl() {
        return "https://www.shapecroydon.org"
                + mAvatarUrl;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getCreationDate() {
        DateFormat format = new SimpleDateFormat("d MMM");
        return format.format(mCreationDate);
    }
}
