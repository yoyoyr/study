package com.test.viewpagedemo.Retrofit.gson;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.test.viewpagedemo.LoggerUtils;

import java.lang.reflect.Type;

//    {
//        "title":"Java Puzzlers: Traps, Pitfalls, and Corner Cases",
//            "isbn-10":"032133678X",
//            "isbn-13":"978-0321336781",
//            "authors": [
//        "Joshua Bloch",
//                "Neal Gafter"
//            ]
//    }
public class BookSerializer implements JsonSerializer<Book>, JsonDeserializer<Book> {
    @NonNull
    @Override
    public JsonElement serialize(@NonNull Book src, Type typeOfSrc, JsonSerializationContext context) {
        LoggerUtils.LOGD("serialize by BookSerializer");
        JsonObject book = new JsonObject();
        book.addProperty("title", src.getTitle());
        book.addProperty("isbn-10", src.getIsbn10());
        book.addProperty("isbn-13", src.getIsbn13());
        JsonArray jsonArray = new JsonArray();
        for (String author : src.getAuthors()) {
//            JsonPrimitive jsonPrimitive = new JsonPrimitive(author);
            jsonArray.add(author);
        }
        book.add("authors", jsonArray);
        return book;
    }

    //    {} jsonObject  [] jsonArray
    @NonNull
    @Override
    public Book deserialize(@NonNull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        LoggerUtils.LOGD("deserialize by BookSerializer");
        JsonObject book = json.getAsJsonObject();
        String title = book.get("title").getAsString();
        String isbn10 = book.get("isbn-10").getAsString();
        String isbn13 = book.get("isbn-13").getAsString();

        JsonArray authors = book.getAsJsonArray("authors");
        String[] as = new String[authors.size()];
        for (int i = 0; i < authors.size(); ++i) {
            as[i] = authors.get(i).getAsString();
        }

        Book b = new Book();
        b.setTitle(title);
        b.setIsbn10(isbn10);
        b.setIsbn13(isbn13);
        b.setAuthors(as);
        return b;
    }
}
