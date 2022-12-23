package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Category;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() {


        //given
        Category category = createCategory();
        Member member = createMember();
        Book book = createBook(category, "객체지향적 생각", 10, 12000);


        int orderCount = 4;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());

        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);

        assertThat(6).isEqualTo(book.getStockQuantity());


    }


    @Test
    void 상품주문_재고수량초과() {
        //given
        Category category = createCategory();
        Member member = createMember();
        Item item = createBook(category, "객체지향적 생각", 9, 12000);

        int orderCount = 11;


        //when //then
        assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount))
                .as("테스트 Exception 필요  : %s", NotEnoughStockException.class)
                .isInstanceOf(NotEnoughStockException.class);

    }

    @Test
    void 주문취소() {
        //given
        Category category = createCategory();
        Member member = createMember();
        Item item = createBook(category, "문뜩 드는 생각", 10, 13000);

        int orderCount = 3;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        assertThat(7).isEqualTo(item.getStockQuantity());

        //when
        orderService.cancelOrder(orderId);

        //then
        Order findOrder = orderRepository.findOne(orderId);
        assertThat(OrderStatus.CANCEL).as("주문상태가 CANCEL 이어야 한다").isEqualTo(findOrder.getStatus());
        assertThat(10).as("주문 갯수가 원상태여야 한다").isEqualTo(findOrder.getOrderItems().get(0).getItem().getStockQuantity());

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("멤버1");
        member.setAddress(new Address("부산시", "해운대로", "253-1"));
        em.persist(member);
        return member;
    }

    private Book createBook(Category category, String name, int quantity, int price) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(quantity);
        book.setPrice(price);

        book.addCategory(category);
        em.persist(book);
        return book;
    }

    private Category createCategory() {
        Category categorySub = new Category();
        categorySub.setName("Java");
        em.persist(categorySub);

        Category category = new Category();
        category.setName("IT");
        category.addChildCategory(categorySub);
        em.persist(category);
        em.flush();
        return categorySub;
    }
}