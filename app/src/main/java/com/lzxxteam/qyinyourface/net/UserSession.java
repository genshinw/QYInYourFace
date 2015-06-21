package com.lzxxteam.qyinyourface.net;

import java.util.HashMap;

/**
 * Created by Elvis on 2015/6/18.
 */
public class UserSession {

    private static int userId = -1;
    private static int userDistruct= 440305;
    public static int userProvice;
    public static HashMap<String,Integer> distictToIdMap = new HashMap<String,Integer>();

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        UserSession.userId = userId;
    }

    public static int getUserDistruct() {
        return userDistruct;
    }

    public static void setUserDistruct(int userDistruct) {
        UserSession.userDistruct = userDistruct;
    }
}
