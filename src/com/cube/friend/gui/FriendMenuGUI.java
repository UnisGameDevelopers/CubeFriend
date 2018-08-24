package com.cube.friend.gui;

import com.cube.friend.func.Friend;
import com.cube.friend.func.FriendManager;
import com.cube.friend.util.GuiManager;
import com.cube.friend.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FriendMenuGUI {

    public static void open(Player player) {
        Friend friend = FriendManager.getManager().get(player.getName());
        GuiManager manager = new GuiManager(9, "친구메뉴");
        manager.setItem(2, new ItemBuilder(Material.SKULL_ITEM).setData((short) 3).setDisplayName("§a친구목록").build());
        manager.setItem(6, new ItemBuilder(Material.WATCH).setDisplayName("§e소식 §f(" + friend.getInvites().size() + ")").build());
        manager.setItem(5, new ItemBuilder(34).setDisplayName(" ").build());
        manager.setItem(7, new ItemBuilder(34).setDisplayName(" ").build());
        manager.setItem(0, new ItemBuilder(34).setDisplayName(" ").build());
        manager.setItem(8, new ItemBuilder(34).setDisplayName(" ").build());
        manager.setItem(1, new ItemBuilder(34).setDisplayName(" ").build());
        manager.setItem(3, new ItemBuilder(34).setDisplayName(" ").build());
        manager.setItem(4, new ItemBuilder(34).setDisplayName(" ").build());
        player.openInventory(manager.getInventory());
    }

}
//친구