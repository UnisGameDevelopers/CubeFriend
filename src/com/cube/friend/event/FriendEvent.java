package com.cube.friend.event;

import com.cube.friend.CubeFriendPlugin;
import com.cube.friend.func.Friend;
import com.cube.friend.func.FriendManager;
import com.cube.friend.gui.*;
import com.shop.gui.GUISession;
import com.shop.gui.PageGUI;
import com.shop.money.type.MoneyType;
import com.shop.money.wrappers.MoneyWrapperManager;
import com.shop.money.wrappers.impl.AbstractMoneyWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class FriendEvent implements Listener {

    private static List<Player> searchableUsers = new ArrayList<>();

    @EventHandler
    public void onFriendGUIClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getInventory().getTitle().equals("친구메뉴")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            switch (event.getRawSlot()) {
                case 6:
                    new FriendInviteGUI(event.getWhoClicked().getName(), event.getWhoClicked().getName() + " 초대목록").open(player);
                    break;
                case 2:
                    new FriendListGUI(event.getWhoClicked().getName(), event.getWhoClicked().getName() + " 친구목록").open(player);
                    break;
            }
        }else if(event.getInventory().getTitle().contains("초대목록")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            if(event.getRawSlot() >= 54 || event.getRawSlot() == 45 || event.getRawSlot() == 53) return;
            if(!event.getCurrentItem().getItemMeta().hasDisplayName()) return;

            if(event.getRawSlot() == 49) {
                FriendMenuGUI.open(player);
                return;
            }
            /* 수락 GUI 로 넘어감. */
            FriendAcceptGUI.open((Player) event.getWhoClicked(), ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
        }else if(event.getInventory().getTitle().contains("수락")) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            Friend friend = FriendManager.getManager().get(event.getWhoClicked().getName());
            String name = event.getInventory().getTitle().split(" ")[0];

            switch (event.getRawSlot()) {
                case 11:
                    /* 친구목록이 꽉 찼는지 확인 */
                    if(friend.isFullSlot()){
                        player.sendMessage("§c친구목록이 꽉 찼습니다.");
                        return;
                    }
                    friend.accept(name);
                    player.sendMessage("§a친구요청을 수락하였습니다.");
                    break;
                case 15:
                    if(friend.isFullSlot()){
                        player.sendMessage("§c친구목록이 꽉 찼습니다.");
                        return;
                    }
                    friend.deny(name);
                    player.sendMessage("§c친구요청을 거절하였습니다.");
                    break;
            }
            ((Player) event.getWhoClicked()).closeInventory();
        }else if(event.getInventory().getTitle().contains("친구목록")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
            if (event.getRawSlot() >= 54) return;

            switch (event.getRawSlot()) {
                case 4:
                    FriendMenuGUI.open(player);
                    return;
            }
            PageGUI gui = GUISession.getSession((Player) event.getWhoClicked());
            Friend friend = FriendManager.getManager().get(event.getWhoClicked().getName());
            int clickSlotNumber = event.getRawSlot() + (45 * (gui.getCurrentPage()-1));
            if (clickSlotNumber >= friend.getOpenedSlot()){
                AbstractMoneyWrapper<?> money = MoneyWrapperManager.get(MoneyType.MONEY, event.getWhoClicked().getName());
                if(money.isBiggerThen(CubeFriendPlugin.BUY_FRIEND_PRICE, event.getWhoClicked().getName())) {
                    money.subtract(CubeFriendPlugin.BUY_FRIEND_PRICE, event.getWhoClicked().getName());
                    friend.setOpenedSlot(friend.getOpenedSlot() + 1);
                    gui.updateGUI();
                    FriendManager.getManager().save();
                }
                return;
            }
            if (event.getClick().isShiftClick()) {
                friend.remove(clickSlotNumber);
                gui.updateGUI();
            }
        }
    }

    @EventHandler
    public void onFriendJoin(PlayerJoinEvent event) {
        FriendManager.getManager().newUser(event.getPlayer());
        FriendManager.getManager().load(event.getPlayer());
        FriendManager.getManager().get(event.getPlayer().getName()).sendMessageFromFriends(event.getPlayer().getName() + " §6§l님이 접속하였습니다.");
    }

    @EventHandler
    public void onFriendQuit(PlayerQuitEvent event){
        FriendManager.getManager().setLastJoined(event.getPlayer());
        FriendManager.getManager().load(event.getPlayer());
        FriendManager.getManager().get(event.getPlayer().getName()).sendMessageFromFriends(event.getPlayer().getName() + " §6§l님이 접속을 종료하였습니다.");
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(GUISession.isOpened((Player) event.getPlayer())){
            GUISession.removeGUIOpeningPlayer((Player) event.getPlayer());
        }
    }

}
// /친구 요청 qlswlsh