package com.github.spirylics.xgwt.hello;


public enum Path {
    
   me("me"), me_friends("me/friends"), me_contacts("me/contacts"), me_followers("me/followers"), me_share("me/share"), me_photos("me/photos"), me_album("me/album"), me_following("me/following"), me_like("me/like");

    private String path;

    Path(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
