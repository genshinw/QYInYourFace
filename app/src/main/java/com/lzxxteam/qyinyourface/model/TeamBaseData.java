package com.lzxxteam.qyinyourface.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Elvis on 2015/6/13.
 */
public class TeamBaseData {

    private  int id;
    private int teamSex;
    private String teamName;
    private int teamLevel;
    private int teamWinLevel;

    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public int getTeamSex() {
        return teamSex;
    }

    @JsonProperty("sex")
    public void setTeamSex(int teamSex) {
        this.teamSex = teamSex;
    }

    public String getTeamName() {
        return teamName;
    }

    @JsonProperty("name")
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamLevel() {
        return teamLevel;
    }

    @JsonProperty("level")
    public void setTeamLevel(int teamLevel) {
        this.teamLevel = teamLevel;
    }

    public int getTeamWinLevel() {
        return teamWinLevel;
    }

    @JsonProperty("wintimes")
    public void setTeamWinLevel(int teamWinLevel) {
        this.teamWinLevel = teamWinLevel;
    }
}
