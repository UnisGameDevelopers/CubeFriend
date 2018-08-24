package com.cube.friend.gui;

import com.cube.friend.CubeFriendPlugin;
import com.cube.friend.func.Friend;
import com.cube.friend.func.FriendManager;
import com.cube.friend.util.DateUtility;
import com.cube.friend.util.ItemBuilder;
import com.shop.gui.PageGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FriendListGUI extends PageGUI {

    public FriendListGUI(String owner, String title) {
        super(owner, title, 54, Object.class);
        super.setCurrentPage(1);
        this.updateGUI();
    }

    @Override
    public void updateGUI() {
        Friend friend = FriendManager.getManager().get(owner);
        List<String> friends = friend.getFriends();
        List<ItemStack> items = new ArrayList<>();
        super.setPageAmount(friends.size());
        for(int i = 45*(super.getCurrentPage()-1); i < 45*super.getCurrentPage(); i++) {
            if(i >= friends.size()) {
                if(i >= friend.getOpenedSlot()) {
                    items.add(new ItemBuilder(Material.IRON_FENCE)
                            .setDisplayName("§c잠겨있는 칸")
                            .addLore("§e가격: §f" + CubeFriendPlugin.BUY_FRIEND_PRICE)
                            .build());
                    continue;
                }
                items.add(new ItemStack(Material.AIR));
                continue;
            }
            String name = friends.get(i);
            ItemBuilder builder = new ItemBuilder(Material.SKULL_ITEM).setData((short) (Friend.isOnline(name) ? 3 : 2))
                    .setDisplayName(Friend.isOnline(name) ? "§f" + name : "§7" + name)
                    .addLore(Friend.isOnline(name) ? "§a[ 온라인 ]" : "§c[ 오프라인 ]");
            if(!Friend.isOnline(name)) {
                try {
                    Date lastJoinDate = DateUtility.sdf.parse(FriendManager.getManager().getLastJoined(name));
                    builder.addLore("§6마지막접속: " + DateUtility.getRemainingDate(lastJoinDate, new Date()));
                }catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            items.add(builder.build());
        }
        for(int i = 0; i < super.inventory.getSize()-9; i++) super.inventory.setItem(i, items.get(i));
        super.initializePage();
        super.inventory.setItem(49, new ItemBuilder(Material.BLAZE_ROD).setDisplayName("§c뒤로가기").build());
    }

}
