package com.jw.track;

import java.util.Stack;

public class TrackManager {

    private static final ThreadLocal<Stack<Span>> TRACK = new ThreadLocal<>();

    private static Span createSpan() {
        Stack<Span> spans = TRACK.get();
        if (spans == null) {
            spans = new Stack<>();
            TRACK.set(spans);
        }
        Span span;
        if (spans.isEmpty()) {
            span = new Span();
            String linkId = TrackContext.getLinkId();
            if (linkId == null) {
                linkId = "nvl";
            }
            span.setName(linkId);
        } else {
            span = spans.peek();
            TrackContext.setLinkId(span.getName());
        }
        return span;
    }

    public static Span createCurrentSpan() {
        Span span = createSpan();
        Stack<Span> spans = TRACK.get();
        spans.push(span);
        return span;
    }

    public static Span getExitSpan() {
        Stack<Span> spans = TRACK.get();
        if (spans == null || spans.isEmpty()) {
            TrackContext.clear();
            return null;
        }
        return spans.pop();
    }

    public static Span getCurrentSpan() {
        Stack<Span> spans = TRACK.get();
        if (spans == null || spans.isEmpty()) {
            return null;
        }
        return spans.peek();
    }
}
