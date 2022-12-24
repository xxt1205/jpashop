package jpabook.jpashop.service;

import jpabook.jpashop.controller.BookForm;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateItemDTO {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    public UpdateItemDTO() {
    }

    public void UpdateBook(BookForm bookForm) {
        this.id = bookForm.getId();
        this.name = bookForm.getName();
        this.price = bookForm.getPrice();
        this.stockQuantity = bookForm.getStockQuantity();
        this.author = bookForm.getAuthor();
        this.isbn = bookForm.getIsbn();
    }
}
