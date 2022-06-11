package com.repositories;

import com.models.Book;
import com.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Component
public class BookRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public BookRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveBook(Book book) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Book> getAllBooks() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Book> bookList = entityManager.createQuery("select b from Book b", Book.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return bookList;
    }

    public Book findBookById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return book;
    }

    public void updateBook(Long id, Book newBook) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Book book = findBookById(id);
        book.setName(newBook.getName());
        book.setAuthor(newBook.getAuthor());
        book.setYear(newBook.getYear());
        entityManager.merge(book);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateBooksPerson(Book book) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteBook(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Book.class, id));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void releaseTheBook(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, id);
        book.setPerson(null);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
