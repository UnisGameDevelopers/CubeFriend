package com.cube.friend.command;

import com.cube.friend.func.Friend;
import com.cube.friend.func.FriendManager;
import com.cube.friend.gui.FriendMenuGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equals("친구")) {
            if(args.length == 0) {
                FriendMenuGUI.open((Player) sender);
                return false;
            }
            if(args.length == 2 && args[0].equals("추가")) {
                String reciver = args[1];
                Friend friend = FriendManager.getManager().get(reciver);
                Friend myFriend = FriendManager.getManager().get(sender.getName());
                if(reciver.equals(sender.getName())) {
                    sender.sendMessage("§c자신한테 초대를 보낼 수 없습니다.");
                    return false;
                }
                if(myFriend.isFullSlot()) {
                    sender.sendMessage("§c친구목록이 부족합니다, 친구목록을 구입해주세요.");
                    return false;
                }
                boolean success = friend.requestFriend(sender.getName());
                if(success) sender.sendMessage("§a친구요청을 보냈습니다.");
                else sender.sendMessage("§c이미 요청을 보냈거니, 당신과 친구이거나, 상대방의 친구창이 꽉 찼습니다.");
            }
        }

        return false;
    }
}
