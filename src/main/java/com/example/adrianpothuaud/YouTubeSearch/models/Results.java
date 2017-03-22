package com.example.adrianpothuaud.YouTubeSearch.models;

import java.util.ArrayList;

public class Results {
    private String regionCode;
    private class PageInfo {
        int totalResults;
        int resultsPerPage;
    };
    private PageInfo pageInfo;
    public class Item {
        public class ID {
            public String videoId;

            public String getVideoId() {
                return videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }
        }
        ID id;
        public class Snippet {
            String title;
            String description;
            public class Thumbnails {
                public class MediumThumb {
                    String url;
                }
                MediumThumb high;
            }
            Thumbnails thumbnails;
            String channelTitle;

            public String getChannelTitle() {
                return channelTitle;
            }

            public void setChannelTitle(String channelTitle) {
                this.channelTitle = channelTitle;
            }

            public String getTitle() {
                return title;
            }
            public void setTitle(String title) {
                this.title = title;
            }
            public String getDescription() {
                return description;
            }
            public void setDescription(String description) {
                this.description = description;
            }
            public String getMediumThumbUrl() {
                return thumbnails.high.url;
            }
        };
        Snippet snippet;
        public Snippet getSnippet() {
            return snippet;
        }
        public void setSnippet(Snippet snippet) {
            this.snippet = snippet;
        }
        public String getVideoId(){return id.getVideoId();}
    };
    private ArrayList<Item> items;
    public String getRegionCode() {
        return regionCode;
    }
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    public PageInfo getPageInfo() {
        return pageInfo;
    }
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
