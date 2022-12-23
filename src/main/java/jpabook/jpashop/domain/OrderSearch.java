package jpabook.jpashop.domain;

import jpabook.jpashop.repository.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName; //회원명
    private OrderStatus orderStatus; //주문 상태 ORDER, CANCEL

}
