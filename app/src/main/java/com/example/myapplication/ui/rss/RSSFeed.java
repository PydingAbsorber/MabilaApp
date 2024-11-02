package com.example.myapplication.ui.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class RSSFeed {

    @Element(name = "channel", required = false)
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

}





