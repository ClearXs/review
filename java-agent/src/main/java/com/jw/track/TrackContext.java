package com.jw.track;

public class TrackContext {

    private static final ThreadLocal<String> TRACK_LOCAL = new ThreadLocal<>();

    public static void clear() {
        TRACK_LOCAL.remove();
    }

    public static String getLinkId() {
        return TRACK_LOCAL.get();
    }

    public static void setLinkId(String linkId) {
        TRACK_LOCAL.set(linkId);
    }
}
