package com.cube.friend.util.task;

import com.cube.friend.CubeFriendPlugin;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class ServerTaskManager {

    private static ServerTaskManager instance;
    private List<ServerTask> executeRepeatables = new ArrayList<>();

    private ServerTaskManager() { runTask(); }

    public static ServerTaskManager getManager() {
        if(instance == null) instance = new ServerTaskManager();
        return instance;
    }

    public List<ServerTask> getExecuteRepeatables() {
        List<ServerTask> list = new ArrayList<>();
        for(ServerTask executeRepeatable : executeRepeatables) {
            list.add(executeRepeatable);
        }
        return list;
    }

    public void addTask(ExecuteRepeatable repeatable, long term) {
        ServerTask task = new ServerTask(repeatable, term);
        this.executeRepeatables.add(task);
    }

    public void removeTask(ExecuteRepeatable executeRepeatable) {
        for(ServerTask serverTask : getExecuteRepeatables()) {
            if(serverTask.getExecuteRepeatable().equals(executeRepeatable))
                executeRepeatables.remove(serverTask);
        }
    }

    private void runTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(CubeFriendPlugin.instance, new Runnable() {
            @Override
            public void run() {
                List<ServerTask> tasks = getExecuteRepeatables();
                for(ServerTask task : tasks) {
                    if(task.isTaskFailed()) {
                        executeRepeatables.remove(task);
                        System.out.println("The operation failed, Delete from the task list.");
                        continue;
                    }
                    if(task.isFinished()) {
                        task.run();
                        task.setFinished(false);
                        continue;
                    }
                    if(!task.isFinished()) {
                        task.run();
                    }
                }
            }
        }, 0L, 1L);
    }

}
