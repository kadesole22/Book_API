package com.Kade.database.repositories;

import com.Kade.database.TestDataUtil;
import com.Kade.database.domain.entities.AuthorEntity;
import com.Kade.database.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        BookEntity book = TestDataUtil.createTestBookEntityA(author);

        underTest.save(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();
        BookEntity actualBook = result.get();

        book.getAuthor().setId(actualBook.getAuthor().getId());

        assertThat(actualBook).isEqualTo(book);
    }

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);

        BookEntity bookA = TestDataUtil.createTestBookEntityA(author);
        BookEntity bookB = TestDataUtil.createTestBookB(author);
        BookEntity bookC = TestDataUtil.createTestBookC(author);

        underTest.save(bookA);
        underTest.save(bookB);
        underTest.save(bookC);

        Iterable<BookEntity> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactlyInAnyOrder(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        author = authorRepository.save(author);

        BookEntity bookA = TestDataUtil.createTestBookEntityA(author);
        underTest.save(bookA);

        bookA.setTitle("UPDATED");
        underTest.save(bookA);

        Optional<BookEntity> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();

        BookEntity bookA = TestDataUtil.createTestBookEntityA(author);
        underTest.save(bookA);

        underTest.deleteById(bookA.getIsbn());

        Optional<BookEntity> result = underTest.findById(bookA.getIsbn());
        assertThat(result).isEmpty();
    }
}
