package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    public Member() {
    }

    public Member( String name) {
        this.name = name;
    }

    @Id @Column(name = "member_id")
    @GeneratedValue(generator = "AUTO")
    private Long id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();




}
