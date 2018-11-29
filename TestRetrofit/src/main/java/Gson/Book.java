package Gson;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Book {
    private String[] authors;
    @SerializedName("isbn-10")
    private String isbn10;
    @SerializedName("isbn-13")
    private String isbn13;
    private String title;
    //为了代码简洁，这里移除getter和setter方法等


    @Override
    public String toString() {
        return "Book{" +
                "authors=" + Arrays.toString(authors) +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    //            "authors": [
//        "Joshua Bloch",
//                "Neal Gafter"
//            ]
    public String[] getAuthors() {
        return authors;
    }

    public String getAuthorsString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (String author : authors) {
            stringBuilder.append(author).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}