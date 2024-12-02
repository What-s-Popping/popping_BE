package com.popping.data.pop.emotion;

public enum ActionState {
    HEART("emotion"),
    LIKE("emotion"),
    FIRE("emotion"),
    SMILE("emotion"),
    FINGER("emotion"),
    HAND("emotion"),
    IMG_SAVED("saved"),
    SHARED("shared"),
    RE_POP("rePop");

    private final String type;

    ActionState(String type) {
        this.type = type;
    }

    public boolean isNotEmotionState() {
        return this.equals(ActionState.RE_POP) || this.equals(ActionState.SHARED) || this.equals(ActionState.IMG_SAVED);
    }
}
