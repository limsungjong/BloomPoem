'use strict'

const pickUpOrderHsBtn = document.querySelector('.pickUpOrderHistory');
const pickUpOrderRvBtn = document.querySelector('.pickUpReviewManagement');
pickUpOrderHsBtn.addEventListener('click', () => {
    const contentBox = new pickUpOrderHS();
    contentBox.orderReviewView();
    contentBox.pickUpContentHandler();
})

pickUpOrderRvBtn.addEventListener('click', () => {

})

class pickUpOrderHS {
    now = new Date();
    date = new Date()
    periodEndDate = new Intl.DateTimeFormat("ko-KR").format(this.now)
    threeMonth = new Date(this.date.setMonth(this.date.getMonth() - 3));
    periodStartDate = new Intl.DateTimeFormat("ko-KR").format(this.threeMonth);
    pickUpOrder = null;

    constructor() {
    }

    // 리스트 생성 함수 // 주문 내역 픽업 클릭시
    orderReviewView() {
        $("#page").val(0)
        $(".mainBox").empty();
        $(".pagingButton").empty();
        $(".pageName").empty();
        $(".pageName").append("픽업 구매 내역");

        console.log(userContext)
        $.ajax({
            url : "/myPage/get/reviewList",
            method : "post",
            data : {"userEmail" : userContext.userEmail , "startDate" : this.now , "endDate" : this.threeMonth}
        }).then(r=>{
            this.pickUpOrder = r;
            console.log(r)
            $(".mainBox").empty();
            $(".mainBox").append(`<div class="period">기간 : <span>${this.periodStartDate}</span> ~ <span>${this.periodEndDate}</span> </div>`);

            $.each(r.content , (i,p)=>{
                let status ;
                if(p.pickUpOrderStatus == 3){
                    status = '결제 완료';
                }else if(p.pickUpOrderStatus == 4) {
                    status = "픽업 전";
                }else if(p.pickUpOrderStatus == 5){
                    status="픽업 후";
                }
                const msg = `
              <div class="orderBox">
                <div class="productArea p${p.pickUpOrderNumber}">
                </div>
                <div class="orderInfoBox">
                  <div>주문날짜 : <span>${p.pickUpOrderDate}</span></div>
                  <div>주문번호 : <span>${p.pickUpOrderNumber}</span></div>
                  
                  <div>픽업날짜 : <span>${p.pickUpOrderReservationDate}</span></div>
                  <div>픽업시간 : <span>${p.pickUpOrderReservationTime}</span></div>
                </div>
                <div class="orderStatusBox">
                  <div>상태 : <span>${status}</span></div>
                  <div class="question">상품을 받으셨나요?<button class="btn btn-outline-dark" id="orderStatus" data-no="${p.pickUpOrderNumber}">완료체크하기</button></div>
                </div>
                <div class="orderTotalBox">
                  <div>총 물품 : <span class="t${p.pickUpOrderNumber}"></span>개</div>
                  <div>총 가격 : <span>${comma(p.pickUpOrderTotalPrice)}</span>원</div>
                </div>
              </div>
            `
                $(".mainBox").append(msg);

                $.ajax({
                    url : "/api/v1/pick_up_order/success/orderDetail",
                    method : "post",
                    data : {"orderNumber" : p.pickUpOrderNumber}
                }).then(r=>{
                    console.log(r)
                    if(i == 1){
                        shoppingOrderDetail1 = r
                    }else{
                        shoppingOrderDetail0 = r
                    }
                    $(`.t${p.pickUpOrderNumber}`).empty();
                    $(`.t${p.pickUpOrderNumber}`).append(r.length);
                    $.each(r, (i,t)=>{
                        console.log(t)
                        const products =
                            `<div class="oneProductArea ${t.pickUpOrderNumber}">
                  <img src ="/image/florist_product/${t.floristMainImage}" alt="productImage" class="oneProductImage" />
                  <div class="oneProductName">${t.flowerName}</div>
                  <div>수량 : <span class="oneProductQuantity">${t.pickUpOrderDetailCount}</span> 송이</div>
                  <div class="oneProductPrice">가격 : <span >${comma(t.pickUpOrderDetailCount * t.pickUpOrderRealPrice)}</span> 원</div>
                  <button class="btn btn-outline-primary reviewButton" data-number="${p.pickUpOrderNumber}" data-no="${t.flowerNumber}">리뷰<br/>쓰기</button>
                </div>`
                        $(`.p${t.pickUpOrderNumber}`).append(products);
                    })
                })
            })
            if(r.totalPages>1){
                const page = $("#page").val();
                $(".pagingButton").empty();
                for(let i=1;r.totalPages>=i; i++){
                    const msg = (page==i) ?`<button class="btn btn-outline-dark shoppingOrder" style="background-color: #dcf8e4; cursor: none">${i}</button>` : `<button class="btn btn-outline-dark shoppingOrder">${i}</button>`
                    $(".pagingButton").append(msg);
                }
            }
        })


    }

    pickUpContentHandler() {

    }
}

class pickUpOrderRv {
    constructor() {
    }
}