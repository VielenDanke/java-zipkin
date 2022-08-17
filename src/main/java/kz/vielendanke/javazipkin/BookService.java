package kz.vielendanke.javazipkin;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final Tracer tracer;

    @Transactional(readOnly = true)
    public List<Book> findAll(String customerName) {
        Span nextSpan = tracer.nextSpan()
                .name("books.service")
                .tag("customer.name", customerName)
                .tag("service.class", this.getClass().getSimpleName());
        List<Book> books = bookRepository.findAll();
        nextSpan.end();
        return books;
    }

    @Transactional
    public void save(Book book) {
        Span nextSpan = tracer.nextSpan()
                .name("books.service")
                .tag("book.name", book.getName())
                .tag("service.class", this.getClass().getSimpleName());
        bookRepository.save(book);
        nextSpan.end();
    }
}
