package Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

    public static void main(String[] args) {
        String data = "        {\n" +
                "            \"title\": \"Java Puzzlers: Traps, Pitfalls, and Corner Cases\",\n" +
                "                \"isbn-10\": \"032133678X\",\n" +
                "                \"isbn-13\": \"978-0321336781\",\n" +
                "                \"authors\": [\n" +
                "            \"Joshua Bloch\",\n" +
                "                    \"Neal Gafter\"\n" +
                "            ]\n" +
                "        }";

        System.out.println("---------------------------------------api");
        Gson gson = new Gson();
        Book book = gson.fromJson(data, Book.class);
        System.out.println("book = " + book.toString());
        System.out.println("book to json = " + gson.toJson(book));


        System.out.println("---------------------------------------serializer");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Book.class, new BookSerializer());
        Gson myGson = gsonBuilder.create();
        Book b = myGson.fromJson(data, Book.class);
        String result = myGson.toJson(b);
        System.out.println("b = " + b.toString());
        System.out.println("serialize result = " + result);
        System.out.println("serialize result = " + myGson.fromJson(result, Book.class));


        System.out.println("---------------------------------------TypeAdapter");
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Book.class, new BookTypeAdapter());
        Gson gsAdapter = gb.create();
        Book bAdapter = gsAdapter.fromJson(data, Book.class);
        result = gsAdapter.toJson(bAdapter);
        System.out.println("bAdapter = " + bAdapter.toString());
        System.out.println("adapter result = " + result);
        System.out.println("serialize result = " + gsAdapter.fromJson(result, Book.class));
    }
}
