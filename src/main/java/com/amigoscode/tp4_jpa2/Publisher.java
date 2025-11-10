package com.amigoscode.tp4_jpa2;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

@DiscriminatorValue("PUBLISHER")
public class Publisher extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String country;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)//signifie que toutes les opérations effectuées sur un Publisher seront propagées vers ses Books :
    private List<Book> books = new ArrayList<>();

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public List<Book> getBooks() { return books; }
}
