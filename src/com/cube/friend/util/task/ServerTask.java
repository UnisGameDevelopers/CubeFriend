package com.cube.friend.util.task;

import com.cube.friend.CubeFriendPlugin;
import org.bukkit.Bukkit;

public class ServerTask {

    private ExecuteRepeatable executeRepeatable;
    private long term;
    private boolean isFinished = true, taskFailed = false;

    public ServerTask(ExecuteRepeatable executeRepeatable, long term) {
        this.executeRepeatable = executeRepeatable;
        this.term = term;
    }

    public boolean isFinished() { return isFinished; }
    public boolean isTaskFailed() { return taskFailed; }
    public ExecuteRepeatable getExecuteRepeatable() { return executeRepeatable; }

    public void setFinished(boolean finished) { isFinished = finished; }

    public void run() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(CubeFriendPlugin.instance, new Runnable() {
            @Override
            public void run() {
                taskFailed = !executeRepeatable.execute(executeRepeatable.getElement());
                isFinished = true;
            }
        }, term);
    }

}
