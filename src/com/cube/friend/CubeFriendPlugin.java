package com.cube.friend;

import com.cube.friend.command.FriendCommand;
import com.cube.friend.event.FriendEvent;
import com.cube.friend.func.FriendManager;
import com.cube.friend.util.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CubeFriendPlugin extends JavaPlugin {

    public static CubeFriendPlugin instance;
    public static DataManager dataManager;

    public static int BUY_FRIEND_PRICE = 10000;

    @Override
    public void onEnable() {
        instance = this;

        initialize();
    }

    private void initialize() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        dataManager = new DataManager("config.yml");

        if(dataManager.get("CONST_BUY_FRIEND_AMOUNT") == null) {
            dataManager.set("CONST_BUY_FRIEND_AMOUNT", 10000);
            DataManager.save(dataManager);
        }else{
            BUY_FRIEND_PRICE = dataManager.getInt("CONST_BUY_FRIEND_AMOUNT");
        }

        Bukkit.getPluginManager().registerEvents(new FriendEvent(), this);
        getCommand("친구").setExecutor(new FriendCommand());
        FriendManager.getManager().load();
    }

}
