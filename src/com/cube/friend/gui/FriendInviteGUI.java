package com.cube.friend.gui;

import com.cube.friend.func.FriendManager;
import com.cube.friend.util.ItemBuilder;
import com.shop.gui.PageGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FriendInviteGUI extends PageGUI {

    public FriendInviteGUI(String owner, String title) {
        super(owner, title, 54, Object.class);
        super.setCurrentPage(1);
        this.updateGUI();
    }

    @Override
    public void updateGUI() {
        List<String> invites = FriendManager.getManager().get(owner).getInvites();
        List<ItemStack> items = new ArrayList<>();
        super.setPageAmount(invites.size());
        for(int i = 45*(super.getCurrentPage()-1); i < 45*super.getCurrentPage(); i++) {
            if(i >= invites.size()) {
                items.add(new ItemStack(Material.AIR));
                continue;
            }
            items.add(new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayName("§a" + invites.get(i)).build());
        }
        for(int i = 0; i < super.inventory.getSize()-9; i++) super.inventory.setItem(i, items.get(i));
        super.initializePage();
        super.inventory.setItem(49, new ItemBuilder(Material.BLAZE_ROD).setDisplayName("§c뒤로가기").build());
    }
}
