package com.udacity.jwdnd.c1.review.model;

import java.util.Arrays;

public enum MessageType {
    SAY     ("Say"),
    SHOUT   ("Shout"),
    WHISPER ("Whisper");

    public final String label;

    private MessageType(String label) {
        this.label = label;
    }

    public String[] getMessageTypeLabel() {
        return Arrays.stream(MessageType.values())
                .map(MessageType::getLabel)
                .toArray(String[]::new);
    }

    public String getLabel() {
        return label;
    }
}
