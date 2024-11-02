package com.example.myapplication.ui.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class RSSRoot {
    @Element(name = "channel", required = false)
    private RSSFeed channel;

    public RSSFeed getChannel() {
        return channel;
    }

    public void setChannel(RSSFeed channel) {
        this.channel = channel;
    }
}

