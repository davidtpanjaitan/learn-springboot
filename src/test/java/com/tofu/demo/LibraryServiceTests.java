package com.tofu.demo;

import com.tofu.demo.model.Book;
import com.tofu.demo.repository.BookLabelRepository;
import com.tofu.demo.repository.BookRepository;
import com.tofu.demo.service.dto.BookDetailResponse;
import com.tofu.demo.service.dto.BookRequest;
import com.tofu.demo.service.dto.BookResponse;
import com.tofu.demo.service.library.LibraryService;
import com.tofu.demo.service.library.LibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class LibraryServiceTests {

	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private BookLabelRepository bookLabelRepo;
	private  LibraryService libraryService;

	@BeforeEach
	void setUp() {
		Book newbook = Book.builder()
				.id(UUID.randomUUID().toString())
				.title("testbook")
				.isbn("123")
				.author("testguy")
				.publisher("testcompany")
				.publicationYear(1984)
				.build();

		bookRepo.save(newbook);
		libraryService = new LibraryServiceImpl(bookRepo, bookLabelRepo);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void testGetBooks() {
		List<BookResponse> results = libraryService.getBooks();
		assertThat(results).isNotEmpty();
	}

	@Test
	void testGetBookDetail() {
		List<BookResponse> results = libraryService.getBooks();
		BookDetailResponse book = libraryService.getBookDetail("123");
		assertThat(book).isNotNull();
		assertThat(book.getTitle()).isEqualTo("testbook");
	}

	@Test
	void testBookCreate() {
		List<BookResponse> results = libraryService.getBooks();
		int bookscount = results.size();
		BookRequest newbook = BookRequest.builder()
						.title("newbook")
						.year(2023)
						.author("david")
						.publisher("elex media komputindo")
						.isbn("456")
						.build();
		libraryService.bookCreate(newbook);
		results = libraryService.getBooks();

		assertThat(results.size()).isEqualTo(bookscount + 1);
		assertThat(results.stream().anyMatch(obj -> obj.getIsbn().equals("456"))).isTrue();
	}
}
