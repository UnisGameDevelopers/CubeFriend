package com.cube.friend.gui;

import com.cube.friend.util.GuiManager;
import com.cube.friend.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FriendAcceptGUI {

    public static void open(Player player, String name) {
        GuiManager manager = new GuiManager(27, name + " 수락");
        manager.setItem(11, new ItemBuilder(Material.WOOL).setData((short) 5).setDisplayName("§a수락").build());
        manager.setItem(15, new ItemBuilder(Material.WOOL).setData((short) 14).setDisplayName("§c거절").build());
        player.openInventory(manager.getInventory());
    }

}
