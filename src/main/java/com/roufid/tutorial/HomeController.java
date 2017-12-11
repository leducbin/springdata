package com.roufid.tutorial;

import com.roufid.tutorial.dao.mysql.AuthorRepository;
import com.roufid.tutorial.dao.postgresql.BookRepository;
import com.roufid.tutorial.entity.cassandrasql.Customer;
import com.roufid.tutorial.entity.mysql.Author;
import com.roufid.tutorial.entity.postgresql.Book;
import com.roufid.tutorial.repository.CustomerRepository;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private Long bookId = 1L;

    @RequestMapping(value = "/getbook")
    public String findById(@RequestParam("id") long id){
        Book book = bookRepository.findOne(bookId);

        Assert.assertNotNull(book);

        Author author = authorRepository.findOne(book.getId());

        Assert.assertNotNull(author);

        String a = "The book " + book.getLastname() + " was written by " + author.getFirstname() + " " + author.getLastname();
        return a;
    }

    @RequestMapping("/save")
    public String savedata()
    {
        Author author = new Author();
        author.setId(1L);
        author.setFirstname("Radouane");
        author.setLastname("Roufid");

        authorRepository.save(author);

        Book book = new Book();
        book.setId(bookId);
        book.setFisrtname("Spring Boot Book");
        book.setLastname("aaaaaaaaaaaaaaaaaaaaaa");

        bookRepository.save(book);
        return "Done";
    }
    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/show")
    public void lookup(){
        System.out.println("===================Lookup Customers from Cassandra by Firstname===================");
        List<Customer> peters = customerRepository.findByFirstname("Peter");
        peters.forEach(System.out::println);

        System.out.println("===================Lookup Customers from Cassandra by Age===================");
        List<Customer> custsAgeGreaterThan25 = customerRepository.findCustomerHasAgeGreaterThan(25);
        custsAgeGreaterThan25.forEach(System.out::println);
    }
}
