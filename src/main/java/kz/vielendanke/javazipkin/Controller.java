package kz.vielendanke.javazipkin;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
class Controller {

    private final BookService bookService;
    private final Tracer tracer;

    @GetMapping
    public ResponseEntity<List<Book>> findAll(@RequestHeader(name = "customer", required = false) String customerName) {
        List<Book> books = bookService.findAll(customerName);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Book book) {
        bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
