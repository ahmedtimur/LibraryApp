package com.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 4, max = 80, message = "Name should be more than 4 and less than 40")
    private String name;

    @Max(value = 120, message = "Wrong age")
    @Min(value = 1, message = "Wrong age")
    private int age;

    @OneToMany(mappedBy = "person", cascade = {DETACH, PERSIST, MERGE, REMOVE}, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setBook(Book book) {
        books.add(book);
    }

}
