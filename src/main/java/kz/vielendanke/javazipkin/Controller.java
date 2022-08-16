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

    private final UserRepository userRepository;
    private final Tracer tracer;

    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        Span span = tracer.nextSpan().name("findAllBooks");
        List<Book> books = userRepository.findAll();
        span.end();
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Book book) {
        Span span = tracer.nextSpan().name("saveBook");
        userRepository.save(book);
        span.end();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
