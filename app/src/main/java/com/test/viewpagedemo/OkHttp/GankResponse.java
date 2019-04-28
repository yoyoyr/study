package com.test.viewpagedemo.OkHttp;

import android.support.annotation.NonNull;

import java.util.List;

public class GankResponse {
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
        StringBuffer buffer = new StringBuffer("GankResponse{" +
                "error=" + error + '\n');
        for (Result r : results) {
            buffer.append(r.toString());
        }
        return buffer.toString();
    }

    class Result {
        String _id;
        String createdAt;
        String desc;
        List<String> images;
        String publishedAt;
        String source;
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

        public List<String> getImages() {
            return images;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getSource() {
            return source;
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
            return "Result{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", images=" + images +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    "}\n";
        }
    }
}
