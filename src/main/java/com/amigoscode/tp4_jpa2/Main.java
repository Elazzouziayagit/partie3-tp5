package com.amigoscode.tp4_jpa2;

import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {

        // Créer l'EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TP4PU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // ================================
        // 1️⃣ Créer des Publishers
        // ================================
        Publisher publisher1 = new Publisher();
        publisher1.setName("Penguin");
        publisher1.setCountry("USA");

        Publisher publisher2 = new Publisher();
        publisher2.setName("Hachette");
        publisher2.setCountry("France");

        // ================================
        // 2️⃣ Créer des Categories
        // ================================
        Category catScience = new Category();
        catScience.setName("Science");

        Category catFiction = new Category();
        catFiction.setName("Fiction");

        // ================================
        // 3️⃣ Créer des Books
        // ================================
        Book book1 = new Book();
        book1.setTitle("Learning Jakarta EE");
        book1.setAuthor("Aya El Azzouzi");
        book1.setPrice(50);
        book1.setDiscountedPrice(45);
        book1.setPublisher(publisher1);
        book1.getCategories().add(catScience);
        book1.getCategories().add(catFiction);

        Book book2 = new Book();
        book2.setTitle("Advanced Java");
        book2.setAuthor("Ali Ben");
        book2.setPrice(60);
        book2.setDiscountedPrice(55);
        book2.setPublisher(publisher2);
        book2.getCategories().add(catScience);

        // ================================
        // 4️⃣ Créer des Reviews
        // ================================
        Review rev1 = new Review();
        rev1.setComment("Excellent book !");
        rev1.setBook(book1);

        Review rev2 = new Review();
        rev2.setComment("Très utile !");
        rev2.setBook(book1);

        Review rev3 = new Review();
        rev3.setComment("Très avancé !");
        rev3.setBook(book2);

        // ================================
        // 5️⃣ Ajouter Books aux Publishers et Reviews aux Books
        // ================================
        publisher1.getBooks().add(book1);
        publisher2.getBooks().add(book2);

        book1.getReviews().add(rev1);
        book1.getReviews().add(rev2);
        book2.getReviews().add(rev3);

        // ================================
        // 6️⃣ Persister toutes les entités
        // ================================
        em.persist(publisher1);
        em.persist(publisher2);
        em.persist(catScience);
        em.persist(catFiction);
        em.persist(book1);
        em.persist(book2);
        em.persist(rev1);
        em.persist(rev2);
        em.persist(rev3);

        em.getTransaction().commit();

        // ================================
        // 7️⃣ Vérifier en affichant un livre
        // ================================
        Book b = em.find(Book.class, book1.getId());//pour trouver le livre dans la base de donné qu on a deja enregistré avant
        System.out.println("Livre : " + b.getTitle());
        System.out.println("Editeur : " + b.getPublisher().getName());
        System.out.println("Categories : ");
        b.getCategories().forEach(c -> System.out.println("- " + c.getName()));
        System.out.println("Reviews : ");
        b.getReviews().forEach(r -> System.out.println("- " + r.getComment()));

        em.close();
        emf.close();
    }
}
