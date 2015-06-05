package com.lzxxteam.qyinyourface.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Elvis on 2015/5/30.
 */
public class UserData {


    private String userId;
    private String userName;
    private String email;
    private String sex;
    private String height;
    private String weight;
    private String team;
    private String userAddr;

    @JsonProperty("id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("sex")
    public void setSex(String sex) {
        this.sex = sex;
    }

    @JsonProperty("team")
    public void setTeam(String team) {
        this.team = team;
    }

    @JsonProperty("height")
    public void setHeight(String height) {
        this.height = height;
    }

    @JsonProperty("weight")
    public void setWeight(String weight) {
        this.weight = weight;
    }

    @JsonProperty("addr")
    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getHeight() {
        return height;
    }

    public String getSex() {
        return sex;
    }

    public String getTeam() {
        return team;
    }

    public String getWeight() {
        return weight;
    }

    public String[] getUserMsgsArray(){

        String[] userMsgsArray = new String[7];
        userMsgsArray[0] = userName;
        userMsgsArray[1] = sex;
        userMsgsArray[2] = height;
        userMsgsArray[3] = weight;
        userMsgsArray[4] = userAddr;
        userMsgsArray[5] = team;
        userMsgsArray[6] = email;
        return userMsgsArray;
    }
}
