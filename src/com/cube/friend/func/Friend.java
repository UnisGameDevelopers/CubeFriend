package com.cube.friend.func;

import org.bukkit.Bukkit;

import java.util.List;

public class Friend {

    private List<String> friends, invites;
    private String owner;
    private int openedSlot;

    public Friend(String owner, List<String> friends) {
        this.owner = owner;
        this.friends = friends;
    }

    public Friend(String owner, List<String> friends, List<String> invites, int openedSlot) {
        this.owner = owner;
        this.friends = friends;
        this.invites = invites;
        this.openedSlot = openedSlot;
    }

    /**
     * 초대목록 n번째 name을 친구로 등록합니다.
     * */
    public void accept(int n) {
        String name = invites.get(n);
        Friend friend = FriendManager.getManager().get(name);
        friend.addFriend(getOwner());
        addFriend(name);
        invites.remove(n);
        FriendManager.getManager().save();

        if(isOnline(name)) {
            Bukkit.getPlayer(name).sendMessage("§e" + owner + " §a님이 친구요청을 수락하셨습니다.");
        }
    }

    public void accept(String name) {
        int n = invites.indexOf(name);
        accept(n);
    }

    public void deny(String name) {
        invites.remove(name);
        FriendManager.getManager().save();

        if(isOnline(name)) {
            Bukkit.getPlayer(name).sendMessage(owner + " §c님이 친구요청을 거절하셨습니다.");
        }
    }

    public void remove(int n) {
        String name = friends.get(n);
        Friend friend = FriendManager.getManager().get(name);
        friend.removeFriend(owner);
        this.friends.remove(n);
        FriendManager.getManager().save();
        Bukkit.getPlayer(this.owner).sendMessage(name + "님을 친구목록에서 삭제하였습니다.");
    }

    public void remove(String name) {
        if(friends.contains(name)) {
            int n = friends.indexOf(name);
            remove(n);
        }
    }

    public boolean requestFriend(String requester) {
        if(this.invites.contains(requester) || this.friends.contains(requester) || isFullSlot()) {
            return  false;
        }
        this.invites.add(requester);
        FriendManager.getManager().save();
        if(Bukkit.getPlayer(owner) != null)
            Bukkit.getPlayer(owner).sendMessage(requester  + "님이 당신에게 친구요청을 보냈습니다.");
        return true;
    }

    public void sendMessageFromFriends(String message) {
        for(String friendName : this.friends){
            if(isOnline(friendName)) {
                Bukkit.getPlayer(friendName).sendMessage(message);
            }
        }
    }

    public boolean isFullSlot() {
        return this.friends.size() >= this.openedSlot;
    }

    public void removeFriend(String name) { friends.remove(name); }
    public void addFriend(String name) { friends.add(name); }
    public List<String> getFriends() { return friends; }
    public List<String> getInvites() { return invites; }
    public String getOwner() { return owner; }
    public int getOpenedSlot() { return openedSlot; }
    public void setOpenedSlot(int openedSlot) { this.openedSlot = openedSlot; }

    public static boolean isOnline(String name) {
        return Bukkit.getPlayer(name) != null;
    }
}
