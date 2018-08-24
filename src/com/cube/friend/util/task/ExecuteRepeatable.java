package com.cube.friend.util.task;

public interface ExecuteRepeatable {
    public boolean execute(Object... objects);
    public Object[] getElement();
}
