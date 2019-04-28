package com.test.viewpagedemo.Glide;

import android.support.annotation.NonNull;

import java.util.List;

public class GankBean {

    boolean error;
    List<Result> results;

    public boolean isError() {
        return error;
    }

    public List<Result> getResults() {
        return results;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("GankBean{\n" +
                "error=" + error +
                "\n, results=");

        for (Result r : results) {
            stringBuffer.append(r.toString());
        }

        return stringBuffer.toString();
    }

    class Result {
        String _id;
        String createdAt;
        String desc;
        String publishedAt;
        String resource;
        String type;
        String url;
        boolean used;
        String who;

        public String get_id() {
            return _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getResource() {
            return resource;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public boolean isUsed() {
            return used;
        }

        public String getWho() {
            return who;
        }

        @NonNull
        @Override
        public String toString() {
            return "\nResult{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", resource='" + resource + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    '}';
        }
    }
}
