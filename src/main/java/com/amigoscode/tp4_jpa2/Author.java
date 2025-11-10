package com.amigoscode.tp4_jpa2;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("AUTHOR")
public class Author extends Person {

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    // Getters & Setters
    public List<Book> getBooks() { return books; }
}
