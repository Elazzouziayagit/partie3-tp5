package com.amigoscode.tp4_jpa2;

import jakarta.persistence.*;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TP4PU");

        // ================================
        // 1️⃣ Livres par catégorie
        // ================================
        EntityManager em1 = emf.createEntityManager();
        em1.getTransaction().begin();//démarre une transaction JPA — c’est obligatoire si tu veux exécuter des opérations sur la base (lecture, écriture, etc.).

        String categoryName = "Science";

        List<Book> booksInCategory = em1.createQuery(
                        "SELECT b FROM Book b JOIN b.categories c WHERE c.name = :name", Book.class)
                .setParameter("name", categoryName)
                .getResultList();

        System.out.println("Livres dans la catégorie '" + categoryName + "' :");
        for (Book b : booksInCategory) {
            System.out.println("- " + b.getTitle());
        }

        em1.getTransaction().commit();
        em1.close();

        // ================================
        // 2️⃣ Livres par éditeur
        // ================================
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();

        String publisherName = "Penguin";

        List<Book> booksByPublisher = em2.createQuery(
                        "SELECT b FROM Book b WHERE b.publisher.name = :name", Book.class)
                .setParameter("name", publisherName)
                .getResultList();

        System.out.println("\nLivres publiés par '" + publisherName + "' :");
        for (Book b : booksByPublisher) {
            System.out.println("- " + b.getTitle());
        }

        em2.getTransaction().commit();
        em2.close();

        // ================================
        // ================================
// 3️⃣ Supprimer un livre et observer le CascadeType
// ================================
        EntityManager em3 = emf.createEntityManager();
        em3.getTransaction().begin();

// Récupérer tous les livres à supprimer (titre "Learning Jakarta EE")
        List<Book> booksToDelete = em3.createQuery(
                        "SELECT b FROM Book b WHERE b.title = :title", Book.class)
                .setParameter("title", "Learning Jakarta EE")
                .getResultList();

        for (Book book : booksToDelete) {
            em3.remove(book);
            System.out.println("Livre '" + book.getTitle() + "' supprimé !");
        }

        em3.getTransaction().commit();
        em3.close();

// ================================
// Vérifier les entités restantes
// ================================
        EntityManager em4 = emf.createEntityManager();

// Livres restants
        List<Book> remainingBooks = em4.createQuery("SELECT b FROM Book b", Book.class).getResultList();//remain... contient tous les livres qui se trouve encore dans la base de donné
        System.out.println("\nLivres restants : " + remainingBooks.size());
        for (Book b : remainingBooks) {
            System.out.println("- " + b.getTitle());
        }

// Reviews restantes
        List<Review> remainingReviews = em4.createQuery("SELECT r FROM Review r", Review.class).getResultList();
        System.out.println("Reviews restantes : " + remainingReviews.size());
        for (Review r : remainingReviews) {
            System.out.println("- " + r.getComment());
        }
        em4.close();
// ================================
// 4️⃣ Mettre à jour le prix d’un livre
// ================================
        EntityManager em5 = emf.createEntityManager();
        em5.getTransaction().begin();

// Récupérer un livre pour mise à jour (exemple : "Advanced Java")
        Book bookToUpdate = em5.createQuery(
                        "SELECT b FROM Book b WHERE b.title = :title", Book.class)
                .setParameter("title", "Advanced Java")
                .getSingleResult();

// Afficher le prix actuel
        System.out.println("\nPrix actuel de '" + bookToUpdate.getTitle() + "' : " + bookToUpdate.getPrice());

// Mettre à jour le prix
        bookToUpdate.setPrice(65);
        bookToUpdate.setDiscountedPrice(60);

// Commit pour appliquer la mise à jour
        em5.getTransaction().commit();

        System.out.println("Prix mis à jour de '" + bookToUpdate.getTitle() + "' : " + bookToUpdate.getPrice());

        em5.close();

// ================================
// 5️⃣ Tester le chargement des relations (EAGER vs LAZY) eager Dès que tu charges l’objet principal, toutes les données liées sont aussi chargées tout de suite.
// ================================
        EntityManager em6 = emf.createEntityManager();
        em6.getTransaction().begin();

// Récupérer un livre pour tester les relations
        Book testBook = em6.createQuery(
                        "SELECT b FROM Book b WHERE b.title = :title", Book.class)
                .setParameter("title", "Advanced Java")
                .getSingleResult();

// Afficher le titre du livre
        System.out.println("\nLivre sélectionné : " + testBook.getTitle());

// Tester la relation Publisher (souvent EAGER) many to one
        System.out.println("Editeur (EAGER ou LAZY selon l'annotation) : " + testBook.getPublisher().getName());

// Tester la relation Categories (souvent LAZY)
        System.out.println("Categories : ");
        for (Category c : testBook.getCategories()) {
            System.out.println("- " + c.getName());
        }

// Tester la relation Reviews (souvent LAZY) many to many
        System.out.println("Reviews : ");
        for (Review r : testBook.getReviews()) {
            System.out.println("- " + r.getComment());
        }

        em6.getTransaction().commit();
        em6.close();




        emf.close();
    }
}
