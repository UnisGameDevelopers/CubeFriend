package com.cube.friend.func;

import com.cube.friend.util.DataManager;
import com.cube.friend.util.DateUtility;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class FriendManager {

    private static FriendManager instance;
    private Map<String, Friend> friendMap = new HashMap<>();
    private DataManager config;

    private FriendManager() {
        config = new DataManager("friend.yml");
    }

    public static FriendManager getManager(){
        if(instance == null) instance = new FriendManager();
        return instance;
    }

    public void load(Player player) {
        List<String> friends = config.getStringList(player.getName() + ".list");
        List<String> invites = config.getStringList(player.getName() + ".invites");
        int openedSlot = config.getInt(player.getName() + ".openedSlot");
        friendMap.put(player.getName(), new Friend(player.getName(), friends ,invites, openedSlot));
    }

    public void load(String player) {
        List<String> friends = config.getStringList(player + ".list");
        List<String> invites = config.getStringList(player + ".invites");
        int openedSlot = config.getInt(player + ".openedSlot");
        friendMap.put(player, new Friend(player, friends ,invites, openedSlot));
    }

    public void newUser(Player player) {
        if(config.get(player.getName() + ".list") == null) {
            config.set(player.getName() + ".list", new ArrayList<>());
            config.set(player.getName() + ".invites", new ArrayList<>());
            config.set(player.getName() + ".openedSlot", 36);
            DataManager.save(config);
        }
    }

    public void load() {
        for(String key : config.getKeys(false)) {
            List<String> friends = config.getStringList(key + ".list");
            List<String> invites = config.getStringList(key + ".invites");
            int openedSlot = config.getInt(key + ".openedSlot");
            friendMap.put(key, new Friend(key, friends ,invites, openedSlot));
        }
    }

    public void setLastJoined(Player player) {
        config.set(player.getName() + ".lastJoined", DateUtility.sdf.format(new Date()));
        DataManager.save(config);
    }

    /**
     * @param playerName Quited Player Name
     * @return lastJoined Date Format
     * */
    public String getLastJoined(String playerName) {
        String format = config.getString(playerName + ".lastJoined");
        return format;
    }

    public Friend get(String player) {
        return friendMap.get(player);
    }

    public void save() {
        for(Map.Entry<String, Friend> entry : friendMap.entrySet()) {
            config.set(entry.getKey() + ".list", entry.getValue().getFriends());
            config.set(entry.getKey() + ".invites", entry.getValue().getInvites());
            config.set(entry.getKey() + ".openedSlot", entry.getValue().getOpenedSlot());
        }
        DataManager.save(config);
    }

    public boolean isLoaded(String player) {
        return this.friendMap.containsKey(player);
    }

}
