package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//상품 상세페이지에서 주문할 상품의 아이디와 주문수량을 전달받는 클래스(주문 수량은 최소 1개, 최대 999개)
@Getter @Setter
public class OrderDto {

    @NotNull(message = "상품 아이디는 필수 입력값입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
    @Max(value = 999, message = "최대 주문 수량은 999개입니다.")
    private int count;
}
