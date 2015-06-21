package com.lzxxteam.qyinyourface.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Elvis on 2015/5/30.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {


    private int userAge;
    private int userGameTimes;
    private int userId;
    private int sex;
    private int height;
    private int weight;

    private String team="";
    private String userAddr="";
    private String userName="";
    private String email="";

    public String getUserName() {
        return userName;
    }
    @JsonProperty("name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeam() {
        return team;
    }
    @JsonProperty("team")
    public void setTeam(String team) {
        this.team = team;
    }

    public int getHeight() {
        return height;
    }
    @JsonProperty("height")
    public void setHeight(int height) {
        this.height = height;
    }

    public int getUserAge() {
        return userAge;
    }
    @JsonProperty("age")
    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getUserGameTimes() {
        return userGameTimes;
    }
    @JsonProperty("gtimes")
    public void setUserGameTimes(int userGameTimes) {
        this.userGameTimes = userGameTimes;
    }

    public int getUserId() {
        return userId;
    }
    @JsonProperty("id")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSex() {
        return sex;
    }
    @JsonProperty("sex")
    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getWeight() {
        return weight;
    }
    @JsonProperty("weight")
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getUserAddr() {
        return userAddr;
    }
    @JsonProperty("addr")
    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

}
