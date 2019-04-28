package com.test.viewpagedemo.Retrofit.gson;

import android.support.annotation.NonNull;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.test.viewpagedemo.LoggerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookTypeAdapter extends TypeAdapter<Book> {

    @Override
    public void write(@NonNull JsonWriter out, @NonNull Book value) throws IOException {
        out.beginObject();
        out.name("title").value(value.getTitle());
        out.name("isbn-10").value(value.getIsbn10());
        out.name("isbn-13").value(value.getIsbn13());
        out.name("authors");
        out.beginArray();
        for (int i = 0; i < value.getAuthors().length; ++i) {
            out.value(value.getAuthors()[i]);
        }
        out.endArray();
        out.endObject();
    }

    @NonNull
    @Override
    public Book read(@NonNull JsonReader in) throws IOException {
        LoggerUtils.LOGD("read by BookTypeAdapter");
        Book book = new Book();
        List as = new ArrayList();
        in.beginObject();
        while (in.hasNext()) {
            String tag = in.nextName();
            switch (tag) {
                case "title":
                    book.setTitle(in.nextString());
                    break;
                case "isbn-10":
                    book.setIsbn10(in.nextString());
                    break;
                case "isbn-13":
                    book.setIsbn13(in.nextString());
                    break;
                case "authors":
                    as.clear();
                    in.beginArray();
                    for (int i = 0; in.hasNext(); ++i) {
                        as.add(in.nextString());
                    }
                    in.endArray();
                    String[] authors = new String[as.size()];
                    for (int i = 0; i < as.size(); ++i) {
                        authors[i] = (String) as.get(i);
                    }
                    book.setAuthors(authors);
                    break;
                default:
                    LoggerUtils.LOGD("unknow tag " + tag);
            }
        }
        in.endObject();
        return book;
    }
}
