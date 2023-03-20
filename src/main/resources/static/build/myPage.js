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
    pickUpOrderDetail0 = null;
    pickUpOrderDetail1 = null;
    modalContainer = null;
    mainContainer = null;

    constructor() {
    }

    // 리스트 생성 함수 // 주문 내역 픽업 클릭시
    orderReviewView() {
        $("#page").val(0)
        $(".mainBox").empty();
        $(".pagingButton").empty();
        $(".pageName").empty();
        $(".pageName").append("픽업 구매 내역");

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
                    status="픽업 완료";
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
                  <div class="question">상품을 받으셨나요?<button class="btn btn-outline-dark" id="pickUpOrderStatus" data-no="${p.pickUpOrderNumber}">완료체크하기</button></div>
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
                    if(i == 1){
                        this.pickUpOrderDetail1 = r
                    }else{
                        this.pickUpOrderDetail0 = r
                    }
                    $(`.t${p.pickUpOrderNumber}`).empty();
                    $(`.t${p.pickUpOrderNumber}`).append(r.length);
                    $.each(r, (i,t)=>{
                        const products =
                            `<div class="oneProductArea ${t.pickUpOrderNumber}">
                  <img src ="/image/florist_product/${t.floristMainImage}" alt="productImage" class="oneProductImage" />
                  <div class="oneProductName">${t.flowerName}</div>
                  <div>수량 : <span class="oneProductQuantity">${t.pickUpOrderDetailCount}</span> 송이</div>
                  <div class="oneProductPrice">가격 : <span >${comma(t.pickUpOrderDetailCount * t.pickUpOrderRealPrice)}</span> 원</div>
                  <button id="pickUpReviewButton" class="btn btn-outline-primary pickUpReviewButton" data-number="${p.pickUpOrderNumber}" data-no="${t.floristNumber}">리뷰<br/>쓰기</button>
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
        //page 넘기는 스크립트
        $(".pagingButton").on("click",".shoppingOrder", (e) => {
            const page = parseInt(e.target.textContent)-1;
            $("#page").val(e.target.textContent);
            $.ajax({
                url : "/myPage/get/reviewList",
                method : "post",
                data : {"userEmail" : userContext.userEmail , "startDate" : this.now , "endDate" : this.threeMonth, page},
            }).then(r => {
                // 위의 상품 띄어주는 함수 다시 시작
                this.pickUpOrder = r;
                $(".mainBox").empty();
                $(".mainBox").append(`      <div class="period">기간 : <span>${this.periodStartDate}</span> ~ <span>${this.periodEndDate}</span> </div>`);

                $.each(r.content , (i,p)=>{
                    let status ;
                    if(p.pickUpOrderStatus == 3){
                        status = '결제 완료';
                    }else if(p.pickUpOrderStatus == 4) {
                        status = "픽업 전";
                    }else if(p.pickUpOrderStatus == 5){
                        status="픽업 완료";
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
                  <div class="question">상품을 받으셨나요?<button class="btn btn-outline-dark" id="pickUpOrderStatus" data-no="${p.pickUpOrderNumber}">완료체크하기</button></div>
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

                        if(i == 1){
                            this.pickUpOrderDetail1 = r
                        }else{
                            this.pickUpOrderDetail0 = r
                        }
                        $(`.p${p.pickUpOrderNumber}`).empty();
                        $(`.t${p.pickUpOrderNumber}`).empty();
                        $(`.t${p.pickUpOrderNumber}`).append(r.length);
                        $.each(r, (i,t)=>{
                            const products =
                                `<div class="oneProductArea ${t.pickUpOrderNumber}">
                  <img src ="/image/florist_product/${t.floristMainImage}" alt="productImage" class="oneProductImage" />
                  <div class="oneProductName">${t.flowerName}</div>
                  <div>수량 : <span class="oneProductQuantity">${t.pickUpOrderDetailCount}</span> 송이</div>
                  <div class="oneProductPrice">가격 : <span >${comma(t.pickUpOrderDetailCount * t.pickUpOrderRealPrice)}</span> 원</div>
                  <button id="pickUpReviewButton" class="btn btn-outline-primary pickUpReviewButton" data-number="${p.pickUpOrderNumber}" data-no="${t.floristNumber}">리뷰<br/>쓰기</button>
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
                // 위의 상품 띄어주는 함수 끝
            })
        })
        //page넘기는 스크립트 끝
        this.mainContainer = document.querySelector('.mainBox');
    }

    pickUpContentHandler() {
        // 상품을 받았는지 체크하기 버튼 처리 시작
        $(".mainArea").on("click", "#pickUpOrderStatus" , (e)=>{
            const pickUpOrderNumber = e.target.dataset.no;
            console.log(pickUpOrderNumber);
            $.ajax({
                url : "/myPage/pick_up/order_status_update",
                method : "post",
                data : {pickUpOrderNumber}
            })
        })
        // 상품을 받았는지 체크하기 버튼 처리 끝
        // modal 시작

        $(".mainArea").on("click","#pickUpReviewButton", (e)=>{
            console.log(e)
            // 꽃집 seq
            const targetNo = e.target.dataset.no
            // 이벤트 타겟
            const target = e.target
            // 오더 seq
            const pickUpOrderNumber = e.target.dataset.number;

            // 이건 왜?
            const targetLeft = parseInt(target.getBoundingClientRect().left +60);
            const targetTop = parseInt(target.getBoundingClientRect().top -10);
            let productName;

            $.ajax({
                url : "/review/check_review",
                method : "post",
                data : {"pickUpOrderNumber" : pickUpOrderNumber}
            }).then(r=>{
                if(r){
                    alert("이미 작성된 리뷰가 있습니다.")
                    return;
                }else{
                    $(".modal-content").css("left",`${targetLeft}px` )
                    $(".modal-content").css("top",`${targetTop}px` )
                    this.createModal();

                    console.log(this.pickUpOrderDetail0)
                    console.log(this.pickUpOrderDetail1)
                    console.log(targetNo)
                    for(let i= 0;this.pickUpOrderDetail0.length>i; i++){
                        if(targetNo == this.pickUpOrderDetail0[i].floristNumber){
                            console.log("asd")
                            productName = this.pickUpOrderDetail0[i].flowerName
                            break;
                        }
                        for(let i= 0;this.pickUpOrderDetail1.length>i; i++){
                            if(targetNo == this.pickUpOrderDetail1[i].floristNumber){
                                console.log("qwe")
                                productName = this.pickUpOrderDetail1[i].flowerName
                                break;
                            }
                        }
                    }
                    $("#pickUpOrderNumber").val(pickUpOrderNumber);
                    $(".pickUpInputText").val("");
                    $(".shoppingReviewScore").val(5).prop("selected", true);
                    $("#productNumber").val(targetNo);
                    $("#productName").text(productName);
                }
            })

        })

        //modal 끝
        // review 등록

    }

    createModal() {
        if(document.querySelector('#pickUpModal')) {
            document.querySelector('body').removeChild(this.modalContainer);
        }
        const modalBox = document.createElement('div');
        modalBox.setAttribute('class','modalArea');
        modalBox.setAttribute('id','pickUpModal');
        modalBox.classList.add('modal');
        const modalHtml =
            `
            <div class="modalBox modal-content" id="reviewWriteModal">
                <div class="modalHeader">
                  <div><span style="font-size: 1.5rem;" id="productName"></span> </div>
                  <div><select class="shoppingReviewScore" name="shoppingReviewScore"><option value="5">5</option><option value="4">4</option><option value="3">3</option><option value="2">2</option><option value="1">1</option></select>점</div>
                  <div><label class="inputLabel" for="inputFile">사진첨부<span class="material-symbols-outlined">image</span></label><form id="form" method="post"enctype="multipart/form-data"><input type="file" value=""  id="inputFile" name="reviewImage" accept=".jpg, .jpeg, .png" style="display: none;"><input type="hidden" name="shoppingReviewNumber" id="shoppingReviewNumber"></form></div>
                  <div><span class="material-symbols-outlined close">close</span></div>
                </div>
                <div class="modalBody">
                  <div><input type="text" class="inputText form-control pickUpInputText" name="shoppingReviewContent" placeholder="리뷰를 입력하세요."></div>
                  <input type="hidden" name="shoppingOrderNumber" id="pickUpOrderNumber" value="">
                  <input type="hidden" name="productNumber" id="productNumber" value="">
                  <div><button class="btn btn-outline-success pickUpWrite">작성하기</button></div>
                </div>
            </div>
            `;
        modalBox.innerHTML = modalHtml;
        document.querySelector('body').append(modalBox);
        this.modalContainer = modalBox;
        this.modalHandler();
    }

    removeModal() {
        if(this.modalContainer) {
          document.querySelector('body').removeChild(this.modalContainer);
        }
    }

    modalHandler() {
        this.modalContainer.querySelector(".close")
            .addEventListener('click', () => this.removeModal());

        $(document).keydown((e)=>{
            if(e.keyCode == 27){
                this.removeModal();
            }
        })

        this.modalContainer.querySelector(".pickUpWrite")
            .addEventListener('click', () => {
                console.log('클릭')
                const floristNumber = parseInt(this.modalContainer.querySelector("#productNumber").val());
                const pickUpOrderNumber = parseInt(this.modalContainer.querySelector("#pickUpOrderNumber").val());
                console.log(floristNumber)
                console.log(pickUpOrderNumber)

                if($.trim(this.modalContainer.$(".pickUpInputText").val())<= 0){
                    alert("리뷰 내용이 비어있습니다.")
                    return;
                }
                $.ajax({
                    url : "/review/pick_up/write",
                    method : "post",
                    data : {
                        "floristNumber" : floristNumber,
                        "userEmail" : userContext.userEmail ,
                        "pickUpOrderNumber" : pickUpOrderNumber,
                        "pickUpOrderContent" : $(".pickUpInputText").val(),
                        "pickUpOrderScore" : $(".shoppingReviewScore").val(),
                    }
                }).then(r =>{
                    $("#shoppingReviewNumber").val(r);
                    console.log($("#inputFile").val())
                    if($("#inputFile").val().length>1){
                        const formData = new FormData($("#form")[0])

                        // $.ajax({
                        //     url : "/review/pick_up/save_photo",
                        //     method : "post",
                        //     data : formData,
                        //     processData : false,
                        //     contentType : false,
                        //     cache : false
                        // })
                    }
                    alert("리뷰가 작성되었습니다.")
                    close_pop();
                }).catch(err => console.log(err))
            })
    }
}

class pickUpOrderRv {
    constructor() {
    }
}