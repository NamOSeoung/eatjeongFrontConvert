package com.dev.eatjeong.main.home;

public class User {
    String name;
    String email;
    String content;

    public User(String name, String email, String content){
        this.name = name;
        this.email = email;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
