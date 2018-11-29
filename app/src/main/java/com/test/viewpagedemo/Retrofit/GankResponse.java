package com.test.viewpagedemo.Retrofit;

import java.util.List;

public class GankResponse {

    String error;
    List<Result> results;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("GankResponse{" + "error='" + error + "\n");
        for (Result r : results) {
            s.append(r + "\n");
        }
        s.append("}\n");
        return s.toString();
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
        String used;
        String who;

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
                    ", used='" + used + '\'' +
                    ", who='" + who + '\'' +
                    '}';
        }
    }
}
