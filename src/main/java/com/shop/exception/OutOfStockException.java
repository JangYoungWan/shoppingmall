package com.shop.exception;

public class OutOfStockException extends RuntimeException{
    //상품의 주문 수량보다 재고 수가 적은 경우 발생시킬 exception을 정의
    public OutOfStockException(String message){
        super(message);
    }
}
