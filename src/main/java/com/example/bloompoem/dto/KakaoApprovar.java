package com.example.bloompoem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoApprovar {
    private String tid	;				//결제 고유 번호
    private String cid	;				//가맹점 코드
    private String partner_order_id;	//가맹점 주문번호, 최대 100자
    private String partner_user_id;		//가맹점 회원 id, 최대 100자
    private Amount amount;				//결제 금액 정보
    private String item_name;			//상품 이름, 최대 100자
    private Integer quantity;			//상품 수량
    private Date approved_at;			//결제 승인 시각

}
