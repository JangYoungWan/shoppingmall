package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩방식
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    //orderItems에는 주문 상품 정보들을 담아준다.
    //orderItem 객체를 order 객체의 orderItems에 추가한다.
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        //상품을 주문한 회원의 정보를 세팅한다.
        order.setMember(member);
        //상품페이지에서는 1개의 상품을 주문하지만, 장바구니 페이지에서는 한 번에 여러개의 상품을 주문할 수 있다.
        //따라서 여러개의 주문 상품을 담을수 있도록 리스트형태로 파라미터 값을 받으며 주문 객체에 orderItem 객체를 추가한다.
        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }
        //주문 상태를 'ORDER'로 세팅
        order.setOrderStatus(OrderStatus.ORDER);
        //현재 시간을 주문 시간으로 세팅
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //총주문 금액을 구하는 메소드
    public int getTotalPrice(){
        int totalPrice =0;
        for (OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    //Item 클래스에서 주문 취소 시 주문의 수량을 상품의 재고에 더해주는 로직과 주문상태를 취소 상태로 바꿔주는 로직 생성
    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }
}
