package com.controllers;

import com.models.Book;
import com.models.Person;
import com.repositories.BookRepository;
import com.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/library/books")
public class BookController {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookController(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/save")
    public String saveBook(@ModelAttribute("book") Book book) {
        return "/library/books/saveBook";
    }

    @PostMapping("/save")
    public String saveBookPost(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "/library/books/saveBook";
        bookRepository.saveBook(book);
        return "redirect:/library/books";
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.getAllBooks());
        return "/library/books/getAllBooks";
    }

    @GetMapping("/{id}")
    public String showBookById(@PathVariable("id") Long id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personRepository.getAllPeople());
        model.addAttribute("book", bookRepository.findBookById(id));
        return "/library/books/getBookById";
    }

    @PatchMapping("/{id}")
    public String setUserToBookPatch(@PathVariable("id") Long id, @ModelAttribute("person") Person person) {
        Person person1 = personRepository.findById(person.getId());
        Book book = bookRepository.findBookById(id);
        book.setPerson(person1);
        person1.setBook(book);
        bookRepository.updateBooksPerson(book);
        return "redirect:/library/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteBook(id);
        return "redirect:/library/books";
    }

    @GetMapping("/{id}/update")
    public String updateBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookRepository.findBookById(id));
        return "/library/books/updateBook";
    }

    @PatchMapping("/{id}/update")
    public String updateBookPatch(@PathVariable("id") Long id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/library/books/updateBook";
        bookRepository.updateBook(id, book);
        return "redirect:/library/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseTheBook(@PathVariable("id") Long id) {
        bookRepository.releaseTheBook(id);
        return "redirect:/library/books";
    }

}
