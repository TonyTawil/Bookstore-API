package com.tony.bookstore;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Document(collection = "Books")
public class Book {
    @Id
    private ObjectId id;
    @NotBlank(message = "Author cannot be blank")
    private String author;
    @NotBlank(message = "Country cannot be blank")
    private String country;
    private String imageLink;
    private String language;
    private String link;
    @Positive(message = "Pages must be a positive integer")
    private int pages;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @Positive(message = "Year must be a positive integer")
    private int year;

    public Book() {
    }

    public Book(ObjectId id, String author, String country, String imageLink, String language, String link, int pages, String title, int year) {
        this.id = id;
        this.author = author;
        this.country = country;
        this.imageLink = imageLink;
        this.language = language;
        this.link = link;
        this.pages = pages;
        this.title = title;
        this.year = year;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", country='" + country + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", language='" + language + '\'' +
                ", link='" + link + '\'' +
                ", pages=" + pages +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}
