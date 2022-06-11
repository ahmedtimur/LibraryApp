package com.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static javax.persistence.CascadeType.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 4, max = 80, message = "Name should be more than 4 and less than 40")
    private String name;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 4, max = 40, message = "Name should be more than 4 and less than 40")
    private String author;

    private int year;

    @ManyToOne(cascade = {DETACH, PERSIST, MERGE, REMOVE}, fetch = FetchType.LAZY)
    private Person person;

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

}
