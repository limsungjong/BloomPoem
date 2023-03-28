'use strict'
let floristData = null;

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
            url: "/myPage/get/reviewList",
            method: "post",
            data: {"userEmail": userContext.userEmail, "startDate": this.now, "endDate": this.threeMonth}
        }).then(r => {
            this.pickUpOrder = r;
            $(".mainBox").empty();
            $(".mainBox").append(`<div class="period">기간 : <span>${this.periodStartDate}</span> ~ <span>${this.periodEndDate}</span> </div>`);

            $.each(r.content, (i, p) => {
                let status;
                if (p.pickUpOrderStatus == 3) {
                    status = '결제 완료';
                } else if (p.pickUpOrderStatus == 4) {
                    status = "픽업 전";
                } else if (p.pickUpOrderStatus == 5) {
                    status = "픽업 완료";
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
                  <div class="floristName fp${p.pickUpOrderNumber}"></div>
                  <div class="floristName f${p.pickUpOrderNumber}"></div>
                  <div>상태 : <span>${status}</span></div>
                </div>
                <div class="orderTotalBox pickTotalBox">
                  <div>총 물품 : <span class="t${p.pickUpOrderNumber}"></span>개</div>
                  <div>총 가격 : <span>${comma(p.pickUpOrderTotalPrice)}</span>원</div>
                  <div class="question">상품을 받으셨나요?<button class="btn btn-outline-dark" id="pickUpOrderStatus" data-no="${p.pickUpOrderNumber}">완료체크하기</button></div>
                </div>
              </div>
            `
                $(".mainBox").append(msg);

                $.ajax({
                    url: "/api/v1/pick_up_order/success/orderDetail",
                    method: "post",
                    data: {"orderNumber": p.pickUpOrderNumber}
                }).then(r => {
                    r = Object.values(r).flat();
                    if (i == 1) {
                        this.pickUpOrderDetail1 = r
                    } else {
                        this.pickUpOrderDetail0 = r
                    }
                    $(`.t${p.pickUpOrderNumber}`).empty();
                    $(`.t${p.pickUpOrderNumber}`).append(r.length);
                    $(`.f${p.pickUpOrderNumber}`).text("꽃 집 : " + r[0].floristName);

                    const orderReviewBtn = document.createElement('button');
                    orderReviewBtn.setAttribute('id', 'pickUpReviewButton');
                    orderReviewBtn.className = `btn btn-outline-primary pickUpReviewButton`;
                    orderReviewBtn.setAttribute('data-number', r[0].pickUpOrderNumber);
                    orderReviewBtn.textContent = "리뷰 쓰기";
                    $(`.fp${r[0].pickUpOrderNumber}`).prepend(orderReviewBtn);

                    $.each(r, (i, t) => {
                        const products =
                            `<div class="oneProductArea ${t.pickUpOrderNumber}">
                  <img src ="/image/florist_product/${t.floristMainImage}" alt="productImage" class="oneProductImage" />
                  <div class="oneProductName">${t.flowerName}</div>
                  <div>수량 : <span class="oneProductQuantity">${t.pickUpOrderDetailCount}</span> 송이</div>
                  <div class="oneProductPrice">가격 : <span >${comma(t.pickUpOrderDetailCount * t.floristProductPrice)}</span> 원</div>
                </div>`
                        $(`.p${t.pickUpOrderNumber}`).append(products);
                    })
                })
            })

            if (r.totalPages > 1) {
                const page = parseInt(($("#page").val())) + 1;
                $(".pagingButton").empty();

                let start = Math.floor((page - 1) / 5) * 5 + 1; // 시작 페이지
                let end = Math.min(start + 4, r.totalPages); // 끝 페이지
                if (start > 1) {
                    $(".pagingButton").append(`<button class="btn btn-outline-dark pickOrder" data-page="${start - 1}"><</button>`);
                }

                for (let i = start; i <= end; i++) {
                    const msg = (page == i) ? `<button class="btn btn-outline-dark pickOrder" style="background-color: #dcf8e4; pointer-events: none";>${i}</button>` : `<button class="btn btn-outline-dark pickOrder" data-page="${i}">${i}</button>`;
                    $(".pagingButton").append(msg);
                }

                if (end < r.totalPages) {
                    $(".pagingButton").append(`<button class="btn btn-outline-dark pickOrder" data-page="${end + 1}">></button>`);
                }
            }
        })


        //page 넘기는 스크립트
        $(".pagingButton").on("click", ".pickOrder", (e) => {
            const page = parseInt(e.target.dataset.page) - 1;
            $("#page").val(page);
            $.ajax({
                url: "/myPage/get/reviewList",
                method: "post",
                data: {"userEmail": userContext.userEmail, "startDate": this.now, "endDate": this.threeMonth, page},
            }).then(r => {
                // 위의 상품 띄어주는 함수 다시 시작
                this.pickUpOrder = r;
                $(".mainBox").empty();
                $(".mainBox").append(`<div class="period">기간 : <span>${this.periodStartDate}</span> ~ <span>${this.periodEndDate}</span> </div>`);

                $.each(r.content, (i, p) => {
                    let status;
                    if (p.pickUpOrderStatus == 3) {
                        status = '결제 완료';
                    } else if (p.pickUpOrderStatus == 4) {
                        status = "픽업 전";
                    } else if (p.pickUpOrderStatus == 5) {
                        status = "픽업 완료";
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
                  <div class="floristName fp${p.pickUpOrderNumber}"></div>
                  <div class="floristName f${p.pickUpOrderNumber}"></div>
                  <div>상태 : <span>${status}</span></div>
                </div>
                <div class="orderTotalBox pickTotalBox">
                  <div>총 물품 : <span class="t${p.pickUpOrderNumber}"></span>개</div>
                  <div>총 가격 : <span>${comma(p.pickUpOrderTotalPrice)}</span>원</div>
                  <div class="question">상품을 받으셨나요?<button class="btn btn-outline-dark" id="pickUpOrderStatus" data-no="${p.pickUpOrderNumber}">완료체크하기</button></div>
                </div>
              </div>
            `
                    $(".mainBox").append(msg);

                    $.ajax({
                        url: "/api/v1/pick_up_order/success/orderDetail",
                        method: "post",
                        data: {"orderNumber": p.pickUpOrderNumber}
                    }).then(r => {
                        r = Object.values(r).flat();
                        if (i == 1) {
                            this.pickUpOrderDetail1 = r
                        } else {
                            this.pickUpOrderDetail0 = r
                        }
                        $(`.p${p.pickUpOrderNumber}`).empty();
                        $(`.t${p.pickUpOrderNumber}`).empty();
                        $(`.t${p.pickUpOrderNumber}`).append(r.length);
                        $(`.f${p.pickUpOrderNumber}`).text("꽃 집 : " + r[0].floristName);

                        const orderReviewBtn = document.createElement('button');
                        orderReviewBtn.setAttribute('id', 'pickUpReviewButton');
                        orderReviewBtn.className = `btn btn-outline-primary pickUpReviewButton`;
                        orderReviewBtn.setAttribute('data-number', r[0].pickUpOrderNumber);
                        orderReviewBtn.textContent = "리뷰 쓰기";
                        $(`.fp${r[0].pickUpOrderNumber}`).prepend(orderReviewBtn);

                        $.each(r, (i, t) => {
                            $(`.f${p.pickUpOrderNumber}`).text("꽃 집 : " + t.floristName);
                            const products =
                                `<div class="oneProductArea ${t.pickUpOrderNumber}">
                  <img src ="/image/florist_product/${t.floristMainImage}" alt="productImage" class="oneProductImage" />
                  <div class="oneProductName">${t.flowerName}</div>
                  <div>수량 : <span class="oneProductQuantity">${t.pickUpOrderDetailCount}</span> 송이</div>
                  <div class="oneProductPrice">가격 : <span >${comma(t.pickUpOrderDetailCount * t.floristProductPrice)}</span> 원</div>
                </div>`
                            $(`.p${t.pickUpOrderNumber}`).append(products);

                        })
                    })
                })
                if (r.totalPages > 1) {
                    const page = parseInt(($("#page").val())) + 1;
                    $(".pagingButton").empty();

                    let start = Math.floor((page - 1) / 5) * 5 + 1; // 시작 페이지
                    let end = Math.min(start + 4, r.totalPages); // 끝 페이지
                    if (start > 1) {
                        $(".pagingButton").append(`<button class="btn btn-outline-dark pickOrder" data-page="${start - 1}"><</button>`);
                    }

                    for (let i = start; i <= end; i++) {
                        const msg = (page == i) ? `<button class="btn btn-outline-dark pickOrder" style="background-color: #dcf8e4; pointer-events: none";>${i}</button>` : `<button class="btn btn-outline-dark pickOrder" data-page="${i}">${i}</button>`;
                        $(".pagingButton").append(msg);
                    }

                    if (end < r.totalPages) {
                        $(".pagingButton").append(`<button class="btn btn-outline-dark pickOrder" data-page="${end + 1}">></button>`);
                    }
                }
                // 위의 상품 띄어주는 함수 끝
            })
        })
        //page넘기는 스크립트 끝
        this.mainContainer = document.querySelector('.mainBox');
        this.pickUpContentHandler();
    }

    pickUpContentHandler() {
        // 상품을 받았는지 체크하기 버튼 처리 시작
        $(".mainArea").on("click", "#pickUpOrderStatus", (e) => {
            const pickUpOrderNumber = e.target.dataset.no;
            if (confirm("꽃 픽업 되셨나요?")) {
                $.ajax({
                    url: "/myPage/pick_up/order_status_update",
                    method: "post",
                    data: {pickUpOrderNumber}
                }).then(r => {
                    if (r == "success") {
                        Swal.fire({
                             icon: 'success',
                             title: '완료 처리되었습니다.',
                             showConfirmButton: false,
                             timer: 1000
                             })
                    }
                })
            }
        })
        // 상품을 받았는지 체크하기 버튼 처리 끝
        // modal 시작

        $(".mainArea").on("click", "#pickUpReviewButton", (e) => {
            // 꽃집 seq
            const targetNo = e.target.dataset.no
            // 이벤트 타겟
            const target = e.target
            // 오더 seq
            const pickUpOrderNumber = e.target.dataset.number;
            // 이건 왜?
            const targetLeft = parseInt(target.getBoundingClientRect().left + 60);
            const targetTop = parseInt(target.getBoundingClientRect().top - 10);
            let productName;

            $.ajax({
                url: "/review/check_review",
                method: "post",
                data: {"pickUpOrderNumber": pickUpOrderNumber}
            }).then(r => {
                if (r) {
                    Swal.fire({
                         icon: 'warning',
                         text: '이미 작성된 리뷰가 있습니다.',
                         confirmButtonText:'확인'
                         })
                } else {
                    for (let i = 0; this.pickUpOrderDetail0.length > i; i++) {
                        if (pickUpOrderNumber == this.pickUpOrderDetail0[i].pickUpOrderNumber) {
                            this.createModal(this.pickUpOrderDetail0, targetLeft, targetTop);
                            productName = this.pickUpOrderDetail0[i].flowerName
                            break;
                        }
                        for (let i = 0; this.pickUpOrderDetail1.length > i; i++) {
                            if (pickUpOrderNumber == this.pickUpOrderDetail1[i].pickUpOrderNumber) {
                                this.createModal(this.pickUpOrderDetail1, targetLeft, targetTop);
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
    }

    createModal(data, targetLeft, targetTop) {
        if (document.querySelector('#pickUpModal')) {
            document.querySelector('body').removeChild(this.modalContainer);
        }
        const modalBox = document.createElement('div');
        modalBox.setAttribute('class', 'modalArea');
        modalBox.setAttribute('id', 'pickUpModal');
        modalBox.classList.add('modal');
        const modalHtml =
            `
            <div class="modalBox modal-content" id="reviewWriteModal">
                <div class="modalHeader">
                  <div><span style="font-size: 1.5rem;" id="productName">${data[0].floristName}</span> </div>
                  <div><select class="shoppingReviewScore" name="shoppingReviewScore"><option value="5">5</option><option value="4">4</option><option value="3">3</option><option value="2">2</option><option value="1">1</option></select>점</div>
                  <div><label class="inputLabel" for="pickInputFile">사진첨부<span class="material-symbols-outlined">image</span></label>
                    <form id="pickForm" method="post"enctype="multipart/form-data">
                        <input type="file" value=""  id="pickInputFile" name="reviewImage" accept=".jpg, .jpeg, .png" style="display: none;">
                        <input type="hidden" name="reviewSeq" id="pickUpReviewNumber">
                    </form></div>
                  <div><span class="material-symbols-outlined close">close</span></div>
                </div>
                <div class="modalBody">
                  <div><input type="text" class="inputText form-control pickUpInputText" name="shoppingReviewContent" placeholder="리뷰를 입력하세요."></div>
                  <input type="hidden" name="pickUpOrderNumber" id="pickUpOrderNumber" value="${data[0].pickUpOrderNumber}">
                  <input type="hidden" name="floristNumber" id="floristNumber" value="${data[0].floristNumber}">
                  <div><button class="btn btn-outline-success pickUpWrite">작성하기</button></div>
                </div>
            </div>
            `;
        modalBox.innerHTML = modalHtml;
        document.querySelector('body').append(modalBox);
        this.modalContainer = modalBox;
        this.modalContainer.querySelector('.modal-content').style.left = `${targetLeft}px`;
        this.modalContainer.querySelector('.modal-content').style.top = `${targetTop}px`;
        this.modalHandler();
    }

    removeModal() {
        if (this.modalContainer) {
            document.querySelector('body').removeChild(this.modalContainer);
        }
    }

    modalHandler() {
        this.modalContainer.querySelector(".close")
            .addEventListener('click', () => this.removeModal());

        $(document).keydown((e) => {
            if (e.keyCode == 27) {
                this.removeModal();
            }
        })

        this.modalContainer.querySelector(".pickUpWrite")
            .addEventListener('click', () => {
                const floristNumber = parseInt(this.modalContainer.querySelector("#floristNumber").value);
                const pickUpOrderNumber = parseInt(this.modalContainer.querySelector("#pickUpOrderNumber").value);

                if ($.trim(this.modalContainer.querySelector(".pickUpInputText").value) <= 0) {
                    Swal.fire({
                         icon: 'warning',
                         text: '리뷰 내용이 비어있습니다.',
                         confirmButtonText:'확인'
                         })
                    return;
                }
                $.ajax({
                    url: "/review/pick_up/write",
                    method: "post",
                    data: {
                        "floristNumber": floristNumber,
                        "userEmail": userContext.userEmail,
                        "pickUpOrderNumber": pickUpOrderNumber,
                        "pickUpOrderContent": this.modalContainer.querySelector(".pickUpInputText").value,
                        "pickUpOrderScore": this.modalContainer.querySelector(".shoppingReviewScore").value,
                    }
                }).then(r => {
                    console.log(r)
                    console.log($("#pickUpReviewNumber"))
                    $("#pickUpReviewNumber").val(r);
                    console.log($("#pickUpReviewNumber").val())
                    console.log($("#pickInputFile").val())
                    if ($("#pickInputFile").val().length > 1) {
                        const formData = new FormData($("#pickForm")[0])

                        $.ajax({
                            url: "/review/pick_up/save_photo",
                            method: "POST",
                            data: formData,
                            processData: false,
                            contentType: false,
                            cache: false
                        })
                    }
                    Swal.fire({
                         icon: 'success',
                         title: '리뷰가 작성되었습니다.',
                         showConfirmButton: false,
                         timer: 1000
                         })
                    this.removeModal();
                }).catch(err => console.log(err))
            })
    }

    clean() {
        $(".pagingButton").off('click');
        $(".mainArea").off('click', '#pickUpReviewButton');
        $(".mainArea").off('click', '#pickUpOrderStatus');

    }
}

class pickUpOrderRv {
    manageOrderBox;

    constructor() {
    }

    openMainContainer() {
        this.reviewView(1);
        $(".pagingButton").on("click", ".pickReviewPaging", (e) => {
            const page = parseInt(e.target.dataset.page);
            // $("#page").val(page);
            this.reviewView(page);
        })
    }

    removeMainContainer() {
        $("#page").val(0)
        $(".pagingButton").empty();
        $(".pageName").empty()
        $(".pageName").append("픽업 리뷰 관리")
        $(".mainBox").empty()
        $(".mainBox").prepend("<div class='shoppingReviewArea'></div>")
    }

    reviewView = (page) => {
        this.removeMainContainer();
        $.ajax({
            url: "/pick_up/review/read",
            method: "post",
            data: {"userEmail": userContext.userEmail, "page": parseInt(page) - 1}
        }).then(r => {
            $.each(r.content, (i, p) => {
                const photo = (p.floristReviewImage == null) ? "noImage.jpg" : `${p.floristReviewImage}`
                let option;
                for (let i = 5; i >= 1; i--) {
                    if (i == p.floristReviewScore) {
                        option = option + `<option value='${i}' selected>${i}</option>`
                    } else {
                        option = option + `<option value='${i}'>${i}</option>`
                    }
                }
                const floristNameIndex = floristData.findIndex(value => value.floristNumber == p.floristNumber);
                const msg = `<div class="shoppingReviewOneBox">
                            <img src="/image/upload/${photo}" alt="" class="shoppingReviewImage" />
                            <div class="shoppingOrderDate">주문번호 : ${p.pickUpOrderNumber}</div>
                            <div class="shoppingReviewRegDate">주문시간 : ${p.floristReviewRegDate}</div>
                            <div class="shoppingProductName">${floristData[floristNameIndex].floristName}</div>
                            <div class="shoppingReviewScore"><select id="score${p.floristReviewNumber}">${option}</select>점</div>
                            <div class="shoppingReviewContent"><input type="text" class="inputText form-control" id="content${p.floristReviewNumber}" value="${p.floristReviewContent}"> </div>
                            <div class="shoppingReviewModify"><button class=" btn btn-outline-primary orderReviewModify" data-no="${p.floristReviewNumber}">수정</button></div>
                            <div class="shoppingReviewDelete"><button class="btn btn-outline-danger orderReviewDelete" data-no="${p.floristReviewNumber}">X</button></div>
                        </div>`
                $(".shoppingReviewArea").append(msg);
            })
            if (r.totalPages > 1) {
                console.log(page)
                $(".pagingButton").empty();

                let start = Math.floor((page - 1) / 5) * 5 + 1; // 시작 페이지
                let end = Math.min(start + 4, r.totalPages); // 끝 페이지
                console.log(start)
                console.log(end)
                if (start > 1) {
                    $(".pagingButton").append(`<button class="btn btn-outline-dark pickReviewPaging" data-page="${start - 1}"><</button>`);
                }

                for (let i = start; i <= end; i++) {
                    const msg = (page == i) ? `<button class="btn btn-outline-dark pickReviewPaging" style="background-color: #dcf8e4; pointer-events: none";>${i}</button>` : `<button class="btn btn-outline-dark pickReviewPaging" data-page="${i}">${i}</button>`;
                    $(".pagingButton").append(msg);
                }

                if (end < r.totalPages) {
                    $(".pagingButton").append(`<button class="btn btn-outline-dark pickReviewPaging" data-page="${end + 1}">></button>`);
                }
            }
        }).catch(err => console.log(err))
    }

    orderModifyHandle() {
        $(".mainBox").on("click", ".orderReviewModify", (e) => {
            const page = $("#page").val()
            const target = e.target.dataset.no;
            const content = $(`#content${target}`).val();
            const score = $(`#score${target}`).val();


            console.log(page)
            console.log(target)
            console.log(content)
            console.log(score)
            $.ajax({
                url: "/pick_up/review/update",
                method: "post",
                data: {"orderReviewNumber": target, "pickUpOrderContent": content, "pickUpOrderScore": score}
            }).then(r => {
                Swal.fire({
                    icon: 'success',
                    title: '수정이 완료되었습니다.',
                    showConfirmButton: false,
                    timer: 1000
                    })
                this.reviewView(page);
            })
        })
    }

    orderReviewHandle() {
        $(".mainBox").on("click", ".orderReviewDelete", (e) => {
            const page = $("#page").val()
            console.log(page);
            const orderReviewNumber = e.target.dataset.no;

            if (confirm("리뷰를 삭제하시겠습니까?")) {
                $.ajax({
                    url: "/pick_up/review/delete",
                    method: "delete",
                    data: {orderReviewNumber}
                }).then(r => {
                    if (r == "success") {
                        Swal.fire({
                            icon: 'success',
                            title: '리뷰가 삭제되었습니다.',
                            showConfirmButton: false,
                            timer: 1000
                            })
                        this.reviewView(page);
                    }
                })
            }
        })
    }

    clean() {
        $(".pagingButton").off('click');
        $(".mainBox").off('click', '.orderReviewModify');
        $(".mainBox").off('click', '.orderReviewDelete');
    }
}

const pickUpOrderHsBtn = document.querySelector('.pickUpOrderHistory');
const pickUpOrderRvBtn = document.querySelector('.pickUpReviewManagement');
const contentBox = new pickUpOrderHS();
const manageBox = new pickUpOrderRv();


pickUpOrderHsBtn.addEventListener('click', () => {
    contentBox.clean();
    contentBox.orderReviewView();
})

pickUpOrderRvBtn.addEventListener('click', () => {
    manageBox.removeMainContainer();
    manageBox.clean();
    manageBox.openMainContainer();
    manageBox.orderModifyHandle();
    manageBox.orderReviewHandle();
})

class myPageMain {
    now = new Date();
    date = new Date()
    periodEndDate = new Intl.DateTimeFormat("ko-KR").format(this.now)
    threeMonth = new Date(this.date.setMonth(this.date.getMonth() - 3));
    periodStartDate = new Intl.DateTimeFormat("ko-KR").format(this.threeMonth);

    constructor() {
    }

    openStartPage() {

    }

    getPickUpOder() {
        const page = 10;
        $.ajax({
            url: "/myPage/index/Info",
            method: "get",
        }).then(r => {
            console.log(r)
        });
    }

    getShoppingOrder() {

    }
}

$(document).ready(() => {
    $.ajax({
        url: "/api/v1/florist/florist_list",
        method: "get",
    }).then(data => {
        floristData = data;
    })
    const myPage = new myPageMain();
    myPage.getPickUpOder();
})