package com.repositories;

import com.models.Book;
import com.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Component
public class PersonRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public PersonRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Person> getAllPeople() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Person> personList = entityManager.createQuery("select p from Person p", Person.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return personList;
    }

    public Person findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Person person = entityManager.find(Person.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return person;
    }

    public void savePerson(Person person) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updatePerson(Long id, Person newPerson) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Person person = findById(id);
        person.setName(newPerson.getName());
        person.setAge(newPerson.getAge());
        entityManager.merge(person);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    public void deletePerson(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Person.class, id));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Book> findBookByPersonId(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Book> bookList = entityManager.createQuery("select b from Book b where b.person.id=?1", Book.class).setParameter(1, id).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return bookList;
    }
}
