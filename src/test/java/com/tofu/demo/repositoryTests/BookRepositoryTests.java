package com.tofu.demo.repositoryTests;

import com.tofu.demo.model.Book;
import com.tofu.demo.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        Book newbook = Book.builder()
                .id(UUID.randomUUID().toString())
                .title("testbook")
                .isbn("123")
                .author("testguy")
                .publisher("testcompany")
                .publicationYear(1984)
                .build();

        bookRepository.save(newbook);
    }
    @Test
    public void testFindIsbnPositive() {
        Optional<Book> foundbook = bookRepository.findByIsbn("123");
        assertThat(foundbook.isPresent()).isTrue();
        assertThat(foundbook.get().getTitle()).isEqualTo("testbook");
    }

    @Test void testFindIsbnNegative() {
        Optional<Book> foundbook = bookRepository.findByIsbn("321");
        assertThat(foundbook.isPresent()).isFalse();
    }
}
