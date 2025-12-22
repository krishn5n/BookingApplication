package com.example.demo.Controller;

import com.example.demo.Service.BookSeatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookSeatController {
    private final BookSeatService bookSeatService;

    public BookSeatController(BookSeatService bookSeatService){
        this.bookSeatService = bookSeatService;
    }


}
