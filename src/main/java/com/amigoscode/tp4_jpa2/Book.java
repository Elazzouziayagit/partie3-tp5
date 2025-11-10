package com.amigoscode.tp4_jpa2;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String author;
    private double price;
    private double discountedPrice;

    @ManyToOne //@ManyToOne : indique une relation plusieurs livres → un éditeur.
    //Un éditeur (Publisher) peut publier plusieurs livres, mais un livre n’a qu’un seul éditeur.

    private Publisher publisher; // variable qui stocke l’éditeur du livre.Par défaut, cette relation est EAGER, donc quand tu charges un Book, le Publisher est aussi chargé.

    @ManyToMany
    @JoinTable(name = "Book_Category",
            joinColumns = @JoinColumn(name = "books_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id"))
    private List<Category> categories = new ArrayList<>(); // liste des catégories du livre. normalement pas cascade REMOVE ici

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(double discountedPrice) { this.discountedPrice = discountedPrice; }

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }

    public List<Category> getCategories() { return categories; }
    public List<Review> getReviews() { return reviews; }
}
