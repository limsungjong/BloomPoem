const body = document.querySelector("body");

const mapOption = {
    center: new kakao.maps.LatLng(37.442928, 126.670556), // 지도의 중심좌표
    level: 4, // 지도의 확대 레벨
};
let searchFlowerNameFloristList = [];
let markers = [];

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
// 공통된 하나의 글로벌 객체로 사용함. 추가 금지
const map = new kakao.maps.Map(document.querySelector("#map"), mapOption);

// 카카오 맵 마커 전부 제거 // markers에 있는 모든 marker 제거
function removeMarker() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

document.querySelector(".searchButton").addEventListener("click", (e) => {
    const queryInput = document.querySelector(".searchTerm");
    const myHeaders = new Headers();
    myHeaders.append("Authorization", "KakaoAK 7367f4f59192633ced366e0cd2cce9fa");
    myHeaders.append("Content-Type", "application/x-www-form-urlencoded");
    myHeaders.append("Cookie", "kd_lang=ko");

    const urlencoded = new URLSearchParams();
    urlencoded.append("query", `${queryInput.value}`);
    urlencoded.append("size", 1);
    urlencoded.append("category_group_code", "SW8");

    const requestOptions = {
        method: "POST", headers: myHeaders, body: urlencoded, redirect: "follow",
    };

    fetch("https://dapi.kakao.com/v2/local/search/keyword.json", requestOptions)
        .then((response) => response.json())
        .then((result) => {
            {
                const myHeaders = new Headers();
                myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

                const urlencoded = new URLSearchParams();
                urlencoded.append("x", result.documents[0].x);
                urlencoded.append("y", result.documents[0].y);

                const requestOptions = {
                    method: "POST", headers: myHeaders, body: urlencoded, redirect: "follow",
                };
                fetch("http://localhost:9000/api/v1/florist/florist_list_query_x_y", requestOptions)
                    .then((response) => response.json())
                    .then((result) => rootFloristListPrint(result))
                    .catch((error) => console.log("error", error));
            }
            removeMarker();
            const data = result.documents[0];
            const coords = new kakao.maps.LatLng(data.y, data.x);
            map.panTo(coords);
        })
        .catch((error) => {
            Swal.fire({
                icon: 'warning', text: '지역을 다시 검색해주세요.', confirmButtonText: '확인'
            })
        });
});
document.querySelector("#searchInput").addEventListener("keyup", (e) => {
    if (e.keyCode === 13) {
        const queryInput = document.querySelector(".searchTerm");
        const myHeaders = new Headers();
        myHeaders.append("Authorization", "KakaoAK 7367f4f59192633ced366e0cd2cce9fa");
        myHeaders.append("Content-Type", "application/x-www-form-urlencoded");
        myHeaders.append("Cookie", "kd_lang=ko");

        const urlencoded = new URLSearchParams();
        urlencoded.append("query", `${queryInput.value}`);
        urlencoded.append("size", 1);
        urlencoded.append("category_group_code", "SW8");

        const requestOptions = {
            method: "POST", headers: myHeaders, body: urlencoded, redirect: "follow",
        };

        fetch("https://dapi.kakao.com/v2/local/search/keyword.json", requestOptions)
            .then((response) => response.json())
            .then((result) => {
                {
                    const myHeaders = new Headers();
                    myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

                    const urlencoded = new URLSearchParams();
                    urlencoded.append("x", result.documents[0].x);
                    urlencoded.append("y", result.documents[0].y);

                    const requestOptions = {
                        method: "POST", headers: myHeaders, body: urlencoded, redirect: "follow",
                    };
                    fetch("http://localhost:9000/api/v1/florist/florist_list_query_x_y", requestOptions)
                        .then((response) => response.json())
                        .then((result) => rootFloristListPrint(result))
                        .catch((error) => console.log("error", error));
                }
                removeMarker();
                const data = result.documents[0];
                const coords = new kakao.maps.LatLng(data.y, data.x);
                map.panTo(coords);
            })
            .catch((error) => {
                Swal.fire({
                    icon: 'warning', text: '지역을 다시 검색해주세요.', confirmButtonText: '확인'
                })
            });
    }
});

class sideItemObj {
    bucketDataArr = [];
    singleBuyDataArr = [];
    buyContainer = null;
    payPopUp = null;
    floristReview = null;

    constructor(x, y, floristData, flowerDataArr, bucketDataArr) {
        this.florist_latitude = x;
        this.florist_longitude = y;
        this.floristData = floristData;
        this.flowerDataArr = flowerDataArr;
        if (bucketDataArr) {
            this.bucketDataArr = bucketDataArr;
        }
        this.modalContainer = null;
        this.tabContent = null;
        this.buyModalContainer = null;
        this.pickDateAndTime = null;
        this.reviewContainer = null;
        this.bouquetNumber = null;
        this.currentPage = 0;
    }

    // 사이드 아이템 생성하고 추가
    createItem() {
        const sideItem = document.createElement("ul");
        sideItem.setAttribute("class", "sideItem");
        sideItem.innerHTML = `
    <li class="floristTitle">
      <span>${this.floristData.floristName}</span> 
      <span class="reviewScore">
        ⭐${this.floristData.floristReviewScore == null ? "0.0" : this.floristData.floristReviewScore.toFixed(1)} / 5.0
        (${this.floristData.floristReviewCount == null ? "0" : this.floristData.floristReviewCount})
      </span>
    </li>
    <li class="floristPhoneNumber">
      <span>${this.floristData.floristPhoneNumber}</span>
    </li>
    <li class="floristAddress">
      <span>${this.floristData.floristAddress}</span>
    </li>
    `;
        document.querySelector(".sideList").appendChild(sideItem);
        this.sideFloristItem = sideItem;
    }

    // 생성된 아이템 위치로 마커추가
    setMarker() {
        const markerPosition = new kakao.maps.LatLng(this.florist_longitude, this.florist_latitude);
        const marker = new kakao.maps.Marker({
            position: markerPosition,
        });
        markers.push(marker);
        const iwContent = `<div style="padding:5px; font-size:1.2rem; width:200px; height:40px; display:flex;">${this.floristData.floristName}</div>`, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

        // 인포윈도우를 생성합니다
        const infowindow = new kakao.maps.InfoWindow({
            content: iwContent, removable: iwRemoveable,
        });
        marker.setMap(map);
        kakao.maps.event.addListener(marker, "mouseover", function () {
            // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
            infowindow.open(map, marker);
        });

        // 마커에 마우스아웃 이벤트를 등록합니다
        kakao.maps.event.addListener(marker, "mouseout", function () {
            // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
            infowindow.close();
        });
        kakao.maps.event.addListener(marker, "click", () => {
            // 마커 위에 인포윈도우를 표시합니다
            this.fetchToCreateModal(this.floristData);
            map.panTo(markerPosition);
        });
    }

    // 마커에 이벤트 추가하기
    setAddEvent() {
        this.sideFloristItem.addEventListener("click", (e) => {
            this.fetchToCreateModal(this.floristData);
            const markerPosition = new kakao.maps.LatLng(this.florist_longitude, this.florist_latitude);
            map.panTo(markerPosition);
        });
    }

    // fetch florist_product_list_detail 에 보내서 모달 만들기
    fetchToCreateModal() {

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

        const urlencoded = new URLSearchParams();
        urlencoded.append("floristNumber", this.floristData.floristNumber);

        const requestOptions = {
            method: 'POST', headers: myHeaders, body: urlencoded, redirect: 'follow'
        };

        fetch("http://localhost:9000/api/v1/florist/florist_product_list_detail", requestOptions)
            .then(response => response.json())
            .then(flowerDataArr => {
                this.flowerDataArr = flowerDataArr;
                this.createModalContainer();
                this.createModalFlower();
                this.modalHandler();
            })
            .catch(error => console.log('error', error));

    }

    // modal 컨테이너 핸들링
    modalHandler() {
        // 모달창 위의 close btn
        this.modalContainer.querySelector(".close-area").addEventListener("click", () => {
            if (this.bucketDataArr.length > 0) {
                this.createCheckModal();
            } else {
                document.querySelector("body").removeChild(this.modalContainer);
                colorReset()
            }
        });

        // 모달창 위의 4개 탭 전환하기
        const navMenus = this.modalContainer.querySelectorAll(".modal-nav li");
        navMenus.forEach((v, i) => {
            v.addEventListener("click", (e) => {
                if (e.target.classList.value == "active") {
                } else {
                    navMenus.forEach((ele) => {
                        if (ele == e.target) {
                            if (document.querySelector('.content ul')) {
                                document.querySelectorAll('.content ul').forEach(e => e.remove());
                            }
                            switch (e.target.textContent) {
                                case "아름다운 꽃":
                                    this.createModalFlower();
                                    break;
                                case "꽃 다발 커스텀":
                                    this.createBouquet();
                                    break;
                                case "장바구니":
                                    this.fetchToBucket();
                                    break;
                                case "리뷰":
                                    this.getReviewToFetch();
                                    break;
                            }
                            ele.setAttribute("class", "active");
                        } else {
                            ele.removeAttribute("class", "active");
                        }
                    });
                }
            });
        });
    }

    // modal 컨테이너 만듬 => this.modalContainer
    createModalContainer() {
        const box = document.createElement("div");
        box.setAttribute("id", "modal");
        box.setAttribute("class", "modal-overlay");
        box.innerHTML = `
          <div class="modal-window">
            <div class="title">
              <h2>${this.floristData.floristName}</h2>
              <span class="reviewScore">
                ⭐${this.floristData.floristReviewScore == null ? "0.0" : this.floristData.floristReviewScore.toFixed(1)} / 5.0
                (${this.floristData.floristReviewCount == null ? "0" : this.floristData.floristReviewCount})
              </span>
            </div>
            <div class="close-area">
                <i class="fas fa-times"></i>
            </div>
            <ul class="modal-nav">
              <li class="active">아름다운 꽃</li>
              <li>꽃 다발 커스텀</li>
              <li>장바구니</li>
              <li>리뷰</li>
            </ul>
            <div class="content">
            </div>
          </div>
        `;
        this.modalContainer = box;
        document.querySelector("body").append(box);
        this.fetchToBucket(true);
    }

    // flowerTab 만듬
    createModalFlower() {
        const flowerListTab = document.createElement("ul");
        flowerListTab.setAttribute("class", "flowerList");
        this.flowerDataArr.forEach((flower) => {
            const liFlowerHtml = `
      <div class="flowerImageBox">
        <img
          class="flowerMainImg"
          src="/image/florist_product/${flower.floristMainImage}"
          alt="꽃 이미지"
        />
      </div>
      <div class="flowerDetailContent">
        <span class="flowerDetailSpan">${flower.flowerColor == null ? "" : flower.flowerColor} ${flower.flowerName}</span>
          <br />
        <span class="flowerDetailSpan">가격 : ${numberAddComa(flower.floristProductPrice)}</span>
      </div>
      <div class="flowerDetailBuy">
        <div class="flowerCountBox">
          <div class = "flowerAddSub">
            <button class="btn btn-outline-primary flowerCntInc">
            +
            </button>
            <input
              class="flowerCount"
              type="number"
              min="1"
              max="100"
              value="1"
              data-flowerNumber=${flower.flowerNumber}
              />
            <button class="btn btn-outline-danger flowerCntDec">
            -
            </button>
          </div>
        <button class="btn btn-outline-secondary flowerCntIncTen">
          10개 추가하기
        </button>
        </div>
        <div class="flowerBuyBox">
          <button class="btn btn-outline-primary flowerBasketBtn">
            장바구니
          </button>
          <button class="btn btn-outline-success flowerBuyBtn" id="flowerBuyBtn">
            구매하기
          </button>
        </div>
      </div>
    `;
            const flowerLiBox = document.createElement("li");
            flowerLiBox.setAttribute("class", "flowerContent");
            flowerLiBox.innerHTML = liFlowerHtml;

            const flowerCntInput = flowerLiBox.querySelector(".flowerCount");

            flowerCntInput.addEventListener("change", (e) => {
                if (flowerCntInput.value < 0) {
                    e.target.value = 1;
                }
                if (flowerCntInput.value > 101) {
                    Swal.fire({
                        icon: 'warning', text: '최대 구매 수량은 100송이 입니다.', confirmButtonText: '확인'
                    })
                    e.target.value = 100;
                }
            });

            flowerLiBox
                .querySelector(".flowerCntInc")
                .addEventListener("click", (e) => {
                    if (flowerCntInput.value > 99) {
                        Swal.fire({
                            icon: 'warning', text: '최대 구매 수량은 100송이 입니다.', confirmButtonText: '확인'
                        })
                        e.target.value = 100;
                        return;
                    }
                    flowerCntInput.value++;
                });

            flowerLiBox
                .querySelector(".flowerCntDec")
                .addEventListener("click", (e) => {
                    if (flowerCntInput.value <= 1) {
                        return;
                    }
                    flowerCntInput.value--;
                });

            flowerLiBox
                .querySelector(".flowerCntIncTen")
                .addEventListener("click", (e) => {
                    if (flowerCntInput.value > 99) {
                        Swal.fire({
                            icon: 'warning', text: '최대 구매 수량은 100송이 입니다.', confirmButtonText: '확인'
                        })
                        flowerCntInput.value = 100;
                        return;
                    }
                    if (flowerCntInput.value == 101) {
                        flowerCntInput.value = 100;
                    }
                    flowerCntInput.value = parseInt(flowerCntInput.value) + 10;
                });

            flowerLiBox
                .querySelector(".flowerBasketBtn")
                .addEventListener("click", (e) => {
                    if (this.loginChecker() == false) return;
                    if (0 > parseInt(flowerCntInput.value) > 101) {
                        return;
                    }
                    const buyData = {
                        flowerName: flower.flowerName,
                        flowerCount: parseInt(flowerLiBox.querySelector('.flowerCount').value),
                        flowerNumber: flower.flowerNumber,
                        flowerColor: flower.flowerColor,
                        floristProductPrice: flower.floristProductPrice,
                        floristNumber: this.floristData.floristNumber,
                        floristName: this.floristData.floristName,
                        floristProductTotalPrice: flower.floristProductPrice * flowerLiBox.querySelector('.flowerCount').value,
                        floristMainImage: flower.floristMainImage,
                    };
                    if (this.bucketDataArr.length == 0) {
                        Swal.fire({
                            icon: 'success', title: '장바구니로 이동되었습니다.', showConfirmButton: false, timer: 1000
                        })
                        this.bucketDataArr.push(buyData);
                        this.bucketToFetch();
                        return;
                    }

                    if (!this.bucketDataArr.find((v) => v.flowerNumber == buyData.flowerNumber)) {
                        Swal.fire({
                            icon: 'success', title: '장바구니로 이동되었습니다.', showConfirmButton: false, timer: 1000
                        })
                        this.bucketDataArr.push(buyData);
                        this.bucketToFetch();
                        return;
                    }
                    const duplicateFlowerNumber = this.bucketDataArr.findIndex((v) => v.flowerNumber == buyData.flowerNumber);

                    console.log(duplicateFlowerNumber);
                    if (duplicateFlowerNumber == 0 || duplicateFlowerNumber > 0) {
                        Swal.fire({
                            icon: 'success', title: '장바구니에 추가되었습니다.', showConfirmButton: false, timer: 1000
                        })
                        this.bucketDataArr[duplicateFlowerNumber].flowerCount = this.bucketDataArr[duplicateFlowerNumber].flowerCount + buyData.flowerCount;
                    }
                    this.bucketToFetch();
                });
            this.flowerBuyHandler(flowerLiBox, flower);
            flowerListTab.append(flowerLiBox);
        });
        this.tabContent = flowerListTab;
        this.modalContainer.querySelector(".content").append(this.tabContent);
    }

    // 장바구니에 있는 물품 서버로 전송
    bucketToFetch() {
        if (this.loginChecker() === false) return;

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const raw = JSON.stringify(this.bucketDataArr);
        const requestOptions = {
            method: "POST", headers: myHeaders, body: raw, redirect: "follow",
        };

        fetch("http://localhost:9000/api/v1/pick_up/pick_up_cart_update", requestOptions)
            .then((response) => {
                return response.text();
            })
            .catch((error) => console.log("error", error));
    }

    // 장바구니 modal 만듬
    createModalBucket() {
        const bucketListTab = document.createElement("ul");
        bucketListTab.setAttribute("class", "bucketList");

        if (this.bucketDataArr.length === 0) {
            const empty = document.createElement('ul');
            const emptyBucketHtml = `
            <div class="emptyBucket">
                <span class="emptyBucketSpan">
                  <img src="/image/img_cartnotice.jpg">
                </span>
            </div>
            `;
            empty.innerHTML = emptyBucketHtml;
            this.modalContainer.querySelector(".content").append(empty);
            return;
        }

        this.bucketDataArr.forEach((bucket) => {
            const liBucketHtml = `
      <div class="checkBox">
        <input type="checkbox" name="${bucket.flowerNumber}"/>
      </div>
      <div class="flowerImageBox">
          <img
            class="flowerMainImg"
            src="/image/florist_product/${bucket.floristMainImage}"
            alt="꽃 이미지"
          />
      </div>
      <div class="bucketDetailContent">
        <span class="bucketDetailSpan">${bucket.flowerColor == null ? "" : bucket.flowerColor} ${bucket.flowerName}</span>
          <br />
        <span class="bucketDetailSpan">가격 : ${numberAddComa(bucket.floristProductPrice)}</span>
      </div>
      <div class="bucketDetailBuy">
        <div class="bucketCountBox">
        <button class="btn btn-outline-primary bucketCntInc">
        +
        </button>
        <input
          class="bucketCount"
          type="number"
          min="1"
          max="100"
          value=${bucket.flowerCount}
          data-flowerNumber=${bucket.flowerNumber}
          />
          
        <button class="btn btn-outline-danger bucketCntDec">
        -
        </button>
              
        </div>
        <div class="bucketBuyBox">
          <span>총 가격 : 
            <span class="bucketTotalPrice">
              ${numberAddComa(bucket.flowerCount * bucket.floristProductPrice)}
            </span>
          </span>
          <button class="btn btn-outline-success bucketBuy">
            구매하기
          </button>
        </div>
      </div>
    `;
            const bucketLi = document.createElement("li");
            bucketLi.setAttribute("class", "bucketContent");
            bucketLi.innerHTML = liBucketHtml;

            const bucketInput = bucketLi.querySelector(".bucketCount");
            bucketInput.addEventListener("change", (e) => {
                if (e.target.value <= 0) {
                    e.target.value = 1;
                    this.bucketDataArr.map((v) => {
                        if (v.flowerNumber == bucket.flowerNumber) {
                            v.flowerCount = 100;
                        }
                        bucketLi.querySelector(".bucketTotalPrice").textContent = numberAddComa(bucketInput.value * bucket.floristProductPrice);
                    });
                    this.createBucketFooter();
                }
                if (e.target.value > 100) {
                    Swal.fire({
                        icon: 'warning', text: '최대 구매 수량은 100송이 입니다.', confirmButtonText: '확인'
                    })
                    e.target.value = 100;
                    this.bucketDataArr.map((v) => {
                        if (v.flowerNumber == bucket.flowerNumber) {
                            v.flowerCount = 100;
                        }
                        bucketLi.querySelector(".bucketTotalPrice").textContent = numberAddComa(bucketInput.value * bucket.floristProductPrice);
                    });
                    this.createBucketFooter();
                }
                this.bucketDataArr.map((v) => {
                    if (v.flowerNumber == bucket.flowerNumber) {
                        v.flowerCount = parseInt(bucketLi.querySelector(".bucketCount").value)
                    }
                });
                bucketLi.querySelector(".bucketTotalPrice").textContent = numberAddComa(bucketInput.value * bucket.floristProductPrice);
                this.createBucketFooter();
                this.targetUpdateCart(bucket);
            });

            bucketLi.querySelector(".bucketBuy").addEventListener("click", () => {
                this.singleBuyDataArr = [];
                const buyData = {
                    flowerName: bucket.flowerName,
                    flowerCount: bucketLi.querySelector('.bucketCount').value,
                    flowerNumber: bucket.flowerNumber,
                    flowerColor: bucket.flowerColor,
                    floristProductPrice: bucket.floristProductPrice,
                    floristNumber: this.floristData.floristNumber,
                    floristName: this.floristData.floristName,
                    floristProductTotalPrice: bucket.floristProductPrice * bucketLi.querySelector('.bucketCount').value,
                    floristMainImage: bucket.floristMainImage,
                    bouquetNumber: bucket.bouquetNumber
                };
                this.singleBuyDataArr.push(buyData);
                this.singleCreateBuyModal(buyData);
            });

            bucketLi.querySelector(".bucketCntInc").addEventListener("click", (e) => {
                if (bucketInput.value > 99) {
                    Swal.fire({
                        icon: 'warning', text: '최대 구매 수량은 100송이 입니다.', confirmButtonText: '확인'
                    })
                    e.target.value = 100;
                    this.bucketDataArr.map((v) => {
                        if (v.flowerNumber == bucket.flowerNumber) {
                            v.flowerCount = 100;
                        }
                        bucketLi.querySelector(".bucketTotalPrice").textContent = numberAddComa(bucketInput.value * bucket.floristProductPrice);
                    });
                    this.createBucketFooter();
                    return;
                }
                this.bucketDataArr.map((v) => {
                    if (v.flowerNumber == bucket.flowerNumber) {
                        v.flowerCount++;
                    }
                });
                this.createBucketFooter();
                bucketInput.value++;
                bucketLi.querySelector(".bucketTotalPrice").textContent = numberAddComa(bucketInput.value * bucket.floristProductPrice);
                this.targetUpdateCart(bucket);
            });

            bucketLi.querySelector(".bucketCntDec").addEventListener("click", (e) => {
                if (bucketInput.value <= 1) {
                    return;
                }
                this.bucketDataArr.map((v) => {
                    if (v.flowerNumber == bucket.flowerNumber) {
                        v.flowerCount--;
                    }
                });
                this.createBucketFooter();
                bucketInput.value--;
                bucketLi.querySelector(".bucketTotalPrice").textContent = numberAddComa(bucketInput.value * bucket.floristProductPrice);
                this.targetUpdateCart(bucket);
            });
            bucketListTab.append(bucketLi);
        });
        this.tabContent = bucketListTab;
        this.modalContainer.querySelector(".content").append(this.tabContent);
        this.createBucketFooter();
    }

    // 서버에 있는 장바구니 받아옴
    fetchToBucket(first) {
        if (document.cookie == "") {
            return;
        }
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const requestOptions = {
            method: "GET", headers: myHeaders, redirect: "follow",
        };

        fetch("http://localhost:9000/api/v1/pick_up/get_pick_up_cart", requestOptions)
            .then((response) => {
                return response.json();
            })
            .then((result) => {
                if (result == undefined) return;
                if (result.find(cart => cart.floristNumber != this.floristData.floristNumber)) {
                    this.bucketDeleteFetch();
                }
                this.bucketDataArr = result;
                if (!first) this.createModalBucket();
            })
    }

    // 확인용 모달 만들기
    createCheckModal() {
        const checkModal = document.createElement("div");
        checkModal.innerHTML = `
    <div id="checkModal" class="modal-overlay" xmlns="http://www.w3.org/1999/html">
      <div id="checkModal">
        <div class="head">
          <div class="close-btn">
            <i class="fas fa-times"></i>
          </div>
        </div>
        <div class="middle">
          <div class="textBox">
            <span>${this.floristData.floristName}에서 담은 물품이 있습니다.</span> </br>
            <span>이 창에서 나가면 모든 물품이 제거 됩니다.</span> </br>
            <span>지금 장바구니 물품을 모두 제거 할까요?</span>
          </div>
        </div>
        <div class="checkBox">
          <div class="okBox">
            <i class="fas fa-check-circle"></i>
          </div>
          <div class="noBox">
            <i class="fas fa-times-circle"></i>
          </div>
        </div>
      </div>
    </div>`;

        checkModal.querySelector(".close-btn").addEventListener("click", (e) => {
            document.querySelector("body").removeChild(checkModal);
        });

        checkModal.querySelector(".okBox").addEventListener("click", (e) => {
            if (confirm("정말로 모두 제거할까요?")) {
                this.bucketDataArr = [];
                document.querySelector("body").removeChild(checkModal);
                if (!this.modalContainer.querySelector('.bucketList') == false) {
                    this.modalContainer.querySelector('.bucketList').remove();
                }
                this.bucketDeleteFetch();
                colorReset()
            }
        });

        checkModal.querySelector(".noBox").addEventListener('click', () => {
            document.querySelector("body").removeChild(checkModal);
        });

        document.querySelector("body").append(checkModal);
    }

    // 장바구니 지우는 fetch
    bucketDeleteFetch() {
        if (this.loginChecker() === false) return;

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

        const requestOptions = {
            method: 'DELETE', headers: myHeaders, redirect: 'follow'
        };
        fetch("http://localhost:9000/api/v1/pick_up/pick_up_cart_delete", requestOptions)
            .then(response => response.text())
            .catch(error => console.log('error', error));
    }

    // 로그인 되었는지 확인함 boolean
    loginChecker() {
        const cookie = document.cookie;

        let check = false;
        if (cookie == "") {
            Swal.fire({
                icon: 'warning', text: "로그인이 필요합니다.", confirmButtonText: '확인'
            }).then(result => {
                location.href = "http://localhost:9000/sign/sign_in";
            })
        }
        if (cookie == undefined) {
            Swal.fire({
                icon: 'warning', text: "로그인이 필요합니다.", confirmButtonText: '확인'
            }).then(result => {
                location.href = "http://localhost:9000/sign/sign_in";
            })
        }
        if (cookie == null) {
            Swal.fire({
                icon: 'warning', text: "로그인이 필요합니다.", confirmButtonText: '확인'
            }).then(result => {
                location.href = "http://localhost:9000/sign/sign_in";
            })
        }

        if (cookie.split("=")[0] == "Authorization") check = true;
        return check;
    }

    // 장바구니 footer 만듬
    createBucketFooter() {
        const footer = this.modalContainer.querySelector(".bucketFooter");
        if (footer) {
            footer.remove();
            // this.modalContainer.removeChild(footer);
        }
        const bucketFooter = document.createElement("div");
        bucketFooter.setAttribute("class", "bucketFooter");

        const bucketFooterHtml = `
      <div class="selectBox">
        <div class="allSelect">
          <button type="button" class="btn btn-outline-success" id="allChoice">
              모두 선택
          </button>
        </div>
        <div class="deleteSelect">
          <button type="button" class="btn btn-outline-danger" id="deleteChoice">
              선택 삭제
          </button>
        </div>
      </div>
      <div class="flowerCountBox">
        <span>
          전체 꽃 : ${this.bucketDataArr.reduce((acc, cur) => {
            return (acc += cur.flowerCount);
        }, 0)}
        </span>
      </div>
      <div class="flowerFullPriceBox">
        <span>
          전체 가격 : ${numberAddComa(this.bucketDataArr.reduce((acc, cur) => {
            return (acc += cur.flowerCount * cur.floristProductPrice);
        }, 0))}
        </span>
      </div>
      <div class="allBuy">
        <button type="button" class="btn btn-outline-primary" id="allBuy">
            전체 구매
        </button>
      </div>
    `;
        bucketFooter.innerHTML = bucketFooterHtml;
        this.tabContent.append(bucketFooter);
        this.footerBuyHandler();
    }

    // flower 탭에 있는 꽃 구매 핸들링
    flowerBuyHandler(flowerLiBox, flowerData) {
        const buyBtn = flowerLiBox.querySelector("#flowerBuyBtn");
        buyBtn.addEventListener("click", () => {
            if (this.loginChecker() == false) return;
            console.log("꽃 탭에서 단건 구매")
            this.singleBuyDataArr = [];
            console.log(this.bouquetNumber);
            const buyData = {
                flowerName: flowerData.flowerName,
                flowerCount: flowerLiBox.querySelector('.flowerCount').value,
                flowerNumber: flowerData.flowerNumber,
                floristProductPrice: flowerData.floristProductPrice,
                floristNumber: this.floristData.floristNumber,
                floristName: this.floristData.floristName,
                floristProductTotalPrice: flowerData.floristProductPrice * flowerLiBox.querySelector('.flowerCount').value,
                floristMainImage: flowerData.floristMainImage,
                bouquetNumber: this.bouquetNumber
            }
            this.singleBuyDataArr.push(buyData);
            this.singleCreateBuyModal(buyData);
        });
    }

    // 장바구니 푸터 구매 핸들링
    footerBuyHandler() {
        const allBtn = this.tabContent.querySelector("#allChoice");
        const delBtn = this.tabContent.querySelector("#deleteChoice");
        const allBuy = this.tabContent.querySelector("#allBuy");

        allBtn.addEventListener("click", () => {
            this.tabContent
                .querySelectorAll(`input[type="checkbox"]`)
                .forEach((v) => (v.checked = true));
            console.log(this.bucketDataArr)
        });

        delBtn.addEventListener("click", () => {
            this.tabContent
                .querySelectorAll(`input[type="checkbox"]:checked`)
                .forEach((element) => {
                    const target = this.bucketDataArr.splice(this.bucketDataArr.findIndex((data) => data.flowerNumber == element.name), 1);
                    this.targetDeleteCart(target);
                    this.tabContent.removeChild(element.parentNode.parentElement);
                });
            this.createBucketFooter();
        });

        allBuy.addEventListener("click", () => {
            this.createBuyModal();
        });

        return this;
    }

    // 전체 구매 모달 띄우기
    createBuyModal() {
        if (document.querySelector("#buyModal")) {
            document.removeChild(document.querySelector("#buyModal"));
        }
        const buyModalContainer = document.createElement("div");
        buyModalContainer.setAttribute("id", "buyModal");
        buyModalContainer.setAttribute("class", "modal-overlay");
        const buyModalHtml = `
      <div class="modal-window">
      <div class="contentBox">
        <div class="closeBtn">
          <i class="fas fa-times"></i>
        </div>
        <div class="titleBox">
          <h2>${this.floristData.floristName}에서 주문하고 있습니다.</h2>
        </div>
        <div class="middleBox">
          <div class="middleLeftBox">
          </div>
          <div class="middleRightBox">
            <div class="userSelectBox">
              <label for="selectDate">날짜를 선택</label>
              <div class="dateSelectBox">
                <input type="date" name="selectDate" id="selectDate" required />
              </div>
              <label for="selectTime">시간을 선택</label>
              <span class="timeSpan" >시간은 오전 9시부터 오후 5시까지만 가능합니다.</span>
              <div class="timeSelectBox">
                <input type="time" name="selectTime" id="selectTime" min="09:00" max="17:00" step="900" required />
              </div>
              <div class="buyBox">
                <div class="priceBox">
                  <span class="priceSpan">총 금액 : ${numberAddComa(this.bucketDataArr.reduce((acc, cur) => {
            return (acc += cur.flowerCount * cur.floristProductPrice);
        }, 0))}</span>
                </div>
                <div class="buyBtnBox" id="kakaoPayBtn">
                    <img src="/image/kakaoPay.png" alt="kakaoPayImage" class="kakaoPageImage"> </br>
                    <span>카카오 페이로 구매하기</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
      `;
        buyModalContainer.innerHTML = buyModalHtml;
        this.modalContainer.append(buyModalContainer);
        this.buyModalContainer = buyModalContainer;
        this.createBuyProductBox();
        this.buyModalHandler();

        const now_utc = Date.now() // 지금 날짜를 밀리초로
        // getTimezoneOffset()은 현재 시간과의 차이를 분 단위로 반환
        const timeOff = new Date().getTimezoneOffset() * 60000; // 분단위를 밀리초로 변환
        // new Date(now_utc-timeOff).toISOString()은 '2022-05-11T18:09:38.134Z'를 반환
        const today = new Date(now_utc - timeOff).toISOString().split("T")[0];
        this.buyModalContainer.querySelector('#selectDate').setAttribute("min", today);
        this.buyModalContainer.querySelector('#selectTime').addEventListener('change', () => {
            this.dateAndTimeController();
        })
    }

    // 단일 구매 모달
    singleCreateBuyModal(buyData) {
        if (document.querySelector("#buyModal")) {
            document.removeChild(document.querySelector("#buyModal"));
        }
        const buyModalContainer = document.createElement("div");
        buyModalContainer.setAttribute("id", "buyModal");
        buyModalContainer.setAttribute("class", "modal-overlay");
        const buyModalHtml = `
      <div class="modal-window">
      <div class="contentBox">
        <div class="closeBtn">
          <i class="fas fa-times"></i>
        </div>
        <div class="titleBox">
          <h2>${buyData.floristName}에서 주문하고 있습니다.</h2>
        </div>
        <div class="middleBox">
          <div class="middleLeftBox">
          </div>
          <div class="middleRightBox">
            <div class="userSelectBox">
              <label for="selectDate">날짜를 선택</label>
              <div class="dateSelectBox">
                <input type="date" name="selectDate" id="selectDate" required />
              </div>
              <label for="selectTime">시간을 선택</label>
              <span class="timeSpan" >시간은 오전 9시부터 오후 5시까지만 가능합니다.</span>
              <div class="timeSelectBox">
                <input type="time" name="selectTime" id="selectTime" min="09:00" max="17:00" step="900" required />
              </div>
              <div class="buyBox">
                <div class="priceBox">
                  <span class="priceSpan">총 금액 : ${numberAddComa(buyData.floristProductTotalPrice)}</span>
                </div>
                <div class="buyBtnBox" id="kakaoPayBtn">
                    <img src="/image/kakaoPay.png" alt="kakaoPayImage" class="kakaoPageImage"> </br>
                    <span>카카오 페이로 구매하기</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
      `;
        buyModalContainer.innerHTML = buyModalHtml;
        this.modalContainer.append(buyModalContainer);
        this.buyModalContainer = buyModalContainer;
        this.createBuyProductBox(buyData);
        this.singleBuyModalHandler();

        const now_utc = Date.now() // 지금 날짜를 밀리초로
        // getTimezoneOffset()은 현재 시간과의 차이를 분 단위로 반환
        const timeOff = new Date().getTimezoneOffset() * 60000; // 분단위를 밀리초로 변환
        // new Date(now_utc-timeOff).toISOString()은 '2022-05-11T18:09:38.134Z'를 반환
        const today = new Date(now_utc - timeOff).toISOString().split("T")[0];
        this.buyModalContainer.querySelector('#selectDate').setAttribute("min", today);
        this.buyModalContainer.querySelector('#selectTime').addEventListener('change', () => {
            this.dateAndTimeController();
        })
    }

    // 구매 모달에서 구매 박스 만들기
    createBuyProductBox(buyData) {
        if (buyData) {
            const buyProduct = document.createElement("div");
            buyProduct.setAttribute("class", "productBox");
            const productHtml = `
        <div class="flowerImageBox">
          <img
            class="flowerMainImg"
            src="/image/florist_product/${buyData.floristMainImage}"
            alt="꽃 이미지"
          />
        </div>
        <div class="flowerDetailTextBox">
          <span class="flowerDetailSpan">${buyData.flowerName}</span>
        </div>
        <div class="flowerOrderBox">
            <div class="flowerDetailCountBox">
              <span class="flowerDetailCountSpan">${buyData.flowerCount}송이</span>
            </div>
            <div class="flowerTotalPriceBox">
              <span class="flowerTotalPrice">금액 : ${numberAddComa(buyData.floristProductPrice * buyData.flowerCount)}</span>
            </div>
        </div>
      `;
            buyProduct.innerHTML = productHtml;
            this.buyModalContainer.querySelector('.middleLeftBox').append(buyProduct);
            return;
        }
        if (this.bucketDataArr == null) {
            return;
        }
        this.bucketDataArr.forEach((product) => {
            console.log(product)
            const buyProduct = document.createElement("div");
            buyProduct.setAttribute("class", "productBox");
            const productHtml = `
        <div class="flowerImageBox">
          <img
            class="flowerMainImg"
            src="/image/florist_product/${product.floristMainImage}"
            alt="꽃 이미지"
          />
        </div>
        <div class="flowerDetailTextBox">
          <span class="flowerDetailSpan">${product.flowerName}</span>
        </div>
        <div class="flowerOrderBox">
            <div class="flowerDetailCountBox">
              <span class="flowerDetailCountSpan">${product.flowerCount}송이</span>
            </div>
            <div class="flowerTotalPriceBox">
              <span class="flowerTotalPrice">금액 : ${numberAddComa(product.floristProductPrice * product.flowerCount)}</span>
            </div>
        </div>
      `;
            buyProduct.innerHTML = productHtml;
            this.buyModalContainer.querySelector('.middleLeftBox').append(buyProduct);
        });
    }

    // 구매 모달창 핸들링
    buyModalHandler() {
        const closeBtn = this.buyModalContainer.querySelector(".closeBtn");
        closeBtn.addEventListener("click", () => {
            console.log(this.bucketDataArr);
            document.querySelector("#modal").removeChild(this.buyModalContainer);
        });

        const kakaoPayBtn = this.buyModalContainer.querySelector('#kakaoPayBtn');
        kakaoPayBtn.addEventListener('click', () => {
            if (this.dateAndTimeController()) {
                this.bucketFetchToBuy();
            }
        })
    }

    singleBuyModalHandler() {
        const closeBtn = this.buyModalContainer.querySelector(".closeBtn");
        closeBtn.addEventListener("click", () => {
            document.querySelector("#modal").removeChild(this.buyModalContainer);
        });

        const kakaoPayBtn = this.buyModalContainer.querySelector('#kakaoPayBtn');
        kakaoPayBtn.addEventListener('click', () => {
            if (this.dateAndTimeController()) {
                this.singleFetchToBuy();
            }
        })
    }

    ////////////////// 카카오 페이 fetch /////////////
    bucketFetchToBuy() {
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const dateTime = this.outPutDateAndTime();
        console.log(dateTime)
        const requestOptions = {
            method: "POST",
            headers: myHeaders,
            body: JSON.stringify({'date': dateTime.date, 'time': dateTime.time, 'orderList': this.bucketDataArr}),
            redirect: "follow",
        };
        this.dateAndTimeController();
        this.BuyFetch(requestOptions);
    }

    singleFetchToBuy() {
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const dateTime = this.outPutDateAndTime();
        const requestOptions = {
            method: "POST",
            headers: myHeaders,
            body: JSON.stringify({'date': dateTime.date, 'time': dateTime.time, 'orderList': this.singleBuyDataArr}),
            redirect: "follow",
        };
        this.dateAndTimeController();
        this.BuyFetch(requestOptions);
    }

    BuyFetch(requestOptions) {
        // 카카오 페이 가장 처음 시작이다.
        fetch("http://localhost:9000/kakao_pay/single/ready", requestOptions)
            .then((response) => {
                return response.json();
            })
            .then((result) => {
                this.createBuySpinner();

                if (result == undefined) return;
                // 팝업을 띄운다. 여기 해당하는 팝업창의 이름을 kakaoPopUp으로 하고
                // 밑에 있는 html 폼의 이름도 kakaoPopUp이다.
                window.open('', 'kakaoPopUp', 'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=840,height=700,left=100,top=100');

                // html밑의 3개의 인풋에 result에 담긴 3개의 값을 할당한다.
                $("#orderId").val(result.orderId);
                $("#tId").val(result.tId);
                $("#pcUrl").val(result.pcUrl);

                // html밑의 kakaoForm을 실행한다. 이 때에 action에 /pick_up/order/pay/pop_up으로 post요청이 들어간다.
                $("#kakaoForm").submit();
            });
    }

    ////////////////////////////////////////////////

    // 구매 모달창에 있는 시간 날짜 핸들링
    dateAndTimeController() {
        const selectTimeInput = this.buyModalContainer.querySelector('#selectTime');
        const selectDateInput = this.buyModalContainer.querySelector('#selectDate');
        const timeArr = selectTimeInput.value.split(":") // ['시간','분']
        let bool = true;

        if (selectDateInput.value == "") {
            Swal.fire({
                icon: 'warning', text: '날짜를 입력해주세요.', confirmButtonText: '확인'
            })
            selectDateInput.focus();
            bool = false;
            return bool;
        }

        // 시간 막기
        if (timeArr[0] < 9) {
            Swal.fire({
                icon: 'warning', text: '시간은 오전 9시부터 가능합니다.', confirmButtonText: '확인'
            })
            selectTimeInput.value = "09:00";
            bool = false;
            return bool;
        }
        if (timeArr[0] > 17) {
            Swal.fire({
                icon: 'warning', text: '시간은 오후 5시까지 가능합니다.', confirmButtonText: '확인'
            })
            selectTimeInput.value = "09:00";
            bool = false;
            return bool;
        }
        return bool;
    }

    // 구매 모달창에 있는 시간 날짜 내보냄
    outPutDateAndTime() {
        const selectTimeInput = this.buyModalContainer.querySelector('#selectTime');
        const selectDateInput = this.buyModalContainer.querySelector('#selectDate');

        const date = {
            date: selectDateInput.value, time: selectTimeInput.value,
        }
        return date;
    }

    // 선택 삭제 버튼 핸들링
    targetDeleteCart(target) {
        if (this.loginChecker() === false) return;

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const raw = JSON.stringify(target);
        const requestOptions = {
            method: "POST", headers: myHeaders, body: raw, redirect: "follow",
        };
        fetch("http://localhost:9000/api/v1/pick_up/pick_up_cart_delete_target", requestOptions)
            .then(response => response.text())
            .catch(error => console.log('error', error));
    }

    // 타겟 카트 업데이트
    targetUpdateCart(target) {
        if (this.loginChecker() === false) return;

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const raw = JSON.stringify(target);
        const requestOptions = {
            method: "POST", headers: myHeaders, body: raw, redirect: "follow",
        };
        fetch("http://localhost:9000/api/v1/pick_up/pick_up_cart_update_target", requestOptions)
            .then(response => response.text())
            .catch(error => console.log('error', error));
    }

    // 구매창 스피너 만들기
    createBuySpinner() {
        if (this.modalContainer.querySelector(".spinnerBox")) return;
        const box = this.buyModalContainer.querySelector(".contentBox");
        const spinner = document.createElement("div");
        spinner.setAttribute("class", "spinnerBox");
        const spinnerHtml = `
    <div class="loaderText">결제 진행 중입니다</div>
    <div class="loader">Loading...</div>
        `;
        spinner.innerHTML = spinnerHtml;
        box.append(spinner);
    }

    // 꽃집 리뷰 데이터 받아오기
    getReviewToFetch(page) {
        if (document.querySelector('.content ul')) {
            document.querySelectorAll('.content ul').forEach(e => e.remove());
        }
        if (page == undefined) page = 0;
        let settings = {
            "url": "http://localhost:9000/api/v1/florist/florist_review", "method": "GET", "timeout": 0, "headers": {
                "Content-Type": "application/x-www-form-urlencoded"
            }, "data": {
                "page": page, "floristNumber": this.floristData.floristNumber
            }
        };

        $.ajax(settings).done((response) => {
            this.createFloristReviewModal(response);
            this.createFloristReviewFooter(response);
        });
    }

    // 꽃집 리뷰 모달 창 만들기
    createFloristReviewModal(response) {
        const reviewContainer = document.createElement("ul");
        this.reviewContainer = reviewContainer;
        reviewContainer.setAttribute("class", "reviewContainer");
        response.content.forEach((data) => {
            const floristReviewBox = document.createElement("div");
            floristReviewBox.setAttribute("class", "reviewBoxWrapper");
            const three = data.userEmail.slice(0, 3);
            const last = data.userEmail.slice(3, 9).replace(/./g, "*");
            const boxHtml = `
          <li class="reviewBox">
            <div class="reviewBoxLeft"> 
              <span class="reviewScore">⭐${data.floristReviewScore}</span>
              <span class="userEmail">${three + last}</span>
              <span class="reviewRegDate">${data.floristReviewRegDate}</span>
            </div>
              <div class="reviewContentBox">
                <p class="reviewContent">${data.floristReviewContent}</p>
              </div>
                <div class="reviewImgBox">
                  ${data.floristReviewImage == null ? "" : `<img class="reviewImg" src="/image/upload/${data.floristReviewImage}" alt="" />`}
              </div>
            </li>
          `;
            floristReviewBox.innerHTML = boxHtml;
            reviewContainer.append(floristReviewBox);
        });
        document.querySelector(".content").append(this.reviewContainer);
    }

    // 꽃집 리뷰 푸터
    createFloristReviewFooter(response) {
        const floristReviewBtnBox = document.createElement('div');
        floristReviewBtnBox.setAttribute('class', 'footerBtnBox');

        const totalPages = response.totalPages; // 전체 페이지 수
        const currentPage = this.currentPage; // 현재 페이지 번호
        const displayBtnCount = 5; // 보여줄 페이지 버튼 개수
        const startPage = Math.max(currentPage - 2, 0); // 시작 페이지 번호
        const endPage = Math.min(startPage + displayBtnCount - 1, totalPages - 1); // 끝 페이지 번호

        // 이전 버튼 생성
        const prevBtn = document.createElement('button');
        prevBtn.setAttribute('class', 'btn btn-outline-dark');
        prevBtn.textContent = '<';
        prevBtn.disabled = (currentPage == 0); // 첫 페이지면 비활성화
        prevBtn.addEventListener('click', (e) => {
            this.currentPage--;
            this.getReviewToFetch(this.currentPage);
        });
        floristReviewBtnBox.append(prevBtn);

        // 페이지 버튼 생성
        for (let i = startPage; i <= endPage; i++) {
            const btnBox = document.createElement('button');
            btnBox.setAttribute('class', 'btn btn-outline-dark');
            btnBox.setAttribute('data-no', i);
            btnBox.textContent = i + 1;
            btnBox.addEventListener('click', (e) => {
                const pageNo = Number(e.target.getAttribute('data-no'));
                this.currentPage = pageNo;
                this.getReviewToFetch(this.currentPage);
            });
            floristReviewBtnBox.append(btnBox);
        }

        // 다음 버튼 생성
        const nextBtn = document.createElement('button');
        nextBtn.setAttribute('class', 'btn btn-outline-dark');
        nextBtn.textContent = '>';
        nextBtn.disabled = (currentPage == totalPages - 1); // 마지막 페이지면 비활성화
        nextBtn.addEventListener('click', (e) => {
            this.currentPage++;
            this.getReviewToFetch(this.currentPage);
        });
        floristReviewBtnBox.append(nextBtn);

        this.reviewContainer.append(floristReviewBtnBox);
    }

    // 부케 모달창 만들기
    createBouquet() {
        $(".content").off();
        let flowerNumbers = [];
        let flowerNames = [];
        const bouquetTab = document.createElement("ul");
        bouquetTab.setAttribute("class", "bouquet");
        const viewColor = (color) => {
            if (color == undefined) {
                color = " ";
            }
            const rgb = $("#selectRgb").val();
            color = $("#selectColorName").val()

            $(".content").append(bouquetTab);
            const colorDiv = `<div class="customMainText">꽃 다발 커스텀하기(1/3)</div>
                                <div class="customSubText">원하는 꽃 다발의 종이를 선택해주세요.</div>
                                <div  class="customColorArea">
                                  <div class="customColorBox">
                                </div>
                                      <div class="customFooterBox">
                                        <div class="resetButtonBox"><button class="btn btn-outline-dark colorReset">색 초기화</button></div>
                                        <div class="selectColorBox">선택 된 색 : <span class="selectColor">${color}</span></div>
                                        <div class="buttonBox"><button class="btn btn-outline-dark colorNextButton">다음</button></div>
                                      </div>
                                    </div>`
            $(".bouquet").empty();
            $(".bouquet").append(colorDiv)
            $(".customColorBox").css("background-color", `${rgb}`)
            $.ajax({
                url: "/color/read", method: "get"
            }).then(r => {
                $.each(r, (i, r) => {
                    const msg = `    
                            <div class="oneColorBox">
                              <div class="colorBoxBox" data-color="${r.bouquetColorName}" data-rgb="${r.bouquetColorRgb}" style="background-color: ${r.bouquetColorRgb};"></div>
                              <div class="colorNameBox" id="color${r.bouquetColorName}">${r.bouquetColorName}</div>
                            </div>`
                    $(".customColorBox").append(msg);
                })
            })
        }
        viewColor()
        const flowersView = () => {
            $(".bouquet").empty();
            $(".bouquet").append("<div class='modalFlowerSelectArea'></div>")
            const selectFlowerBox = `<div class="customMainText">꽃 다발 커스텀하기(2/3)</div>
                  <div class="customSubText" style="margin-left: 144px;">원하는 꽃을 선택해주세요.<div style="font-size: 1rem">*수량은 뒤에서 변경 가능</div></div>
                  <div class="customFlowerArea">
                    <div class="customFlowerBox">
                            </div>
                    <div class="customFooterBox">
                    <div class="backButtonBox"><button class="btn btn-outline-dark backButton">이전</button></div>
                      <div class="customFlowerSelectBox">선택된 꽃 : <span class="selectFlower"></span></div>
                      <div class="buttonBox"><button class="btn btn-outline-dark flowerNextButton">다음</button></div>
                    </div>
                  </div>`
            $(".modalFlowerSelectArea").append(selectFlowerBox);
            const rgb = $("#selectRgb").val();
            $(".customFlowerBox").css("background-color", `${rgb}`)
            $(".customFlowerBox").empty();
            $.each(this.flowerDataArr, (i, r) => {
                const flowers = `<div class="oneFlowerBox">
                                        <div class="customFlowerImageBox"><img src="/image/florist_product/${r.floristMainImage}" data-flower_name="${r.flowerName}(${(r.flowerColor != null) ? r.flowerColor : ""})" data-flower_number="${r.flowerNumber}" alt="flowerImage" class="customFlowerImage"></div>
                                        <div class="customFlowerNameBox" id="flower${r.flowerNumber}">${r.flowerName}(<span>${(r.flowerColor != null) ? r.flowerColor : ""}</span>)</div>
                                        <div class="customFlowerPrice"><span style="font-size: 1.0rem">${comma(r.floristProductPrice)}</span> 원</div>
                                      </div>`
                $(".customFlowerBox").append(flowers);

            })
            $(".modalFlowerSelectArea").on("click", ".customFlowerImage", (e) => {
                const flowerNumber = e.target.dataset.flower_number
                const flowerName = e.target.dataset.flower_name
                const flowerDiv = `<span class="span${flowerNumber}"><span>  ${flowerName} </span><span class="delete" data-flower_no="${flowerNumber}">❎ </span></span>`
                for (let i = 0; i < flowerNumbers.length; i++) {
                    if (flowerNumbers[i] == "" + flowerNumber) {
                        $(`#flower${flowerNumber}`).css("font-weight", "400")
                        flowerNumbers.splice(i, i);
                        $(`.span${flowerNumber}`).remove();
                        flowerNames.splice(i, i);
                        return;
                    }
                }
                $(`#flower${flowerNumber}`).css("font-weight", "bold")
                flowerNumbers.push(flowerNumber);
                $(".selectFlower").append(flowerDiv);
                flowerNames.push(flowerName);
            })
            //flower 이미지 클릭 시 이벤트 끝

            //flower delete button 클릭 이벤트 시작
            $(".modalFlowerSelectArea").on("click", ".delete", (e) => {
                const flowerNumber = e.target.dataset.flower_no
                for (let i = 0; i < flowerNumbers.length; i++) {
                    if (flowerNumbers[i] == "" + flowerNumber) {
                        $(`#flower${flowerNumber}`).css("font-weight", "400")
                        flowerNumbers.splice(i, i);
                        $(`.span${flowerNumber}`).remove();
                        flowerNames.splice(i, i);
                        return;
                    }
                }
            })
        }
        // color의 박스를 선택했을때 선택하는 이벤트 시작
        $(".bouquet").on("click", ".colorBoxBox", (e) => {
            const color = e.target.dataset.color
            const rgb = e.target.dataset.rgb

            $(".selectColor").empty();
            $(".customColorBox").css("background-color", `${rgb}`)
            $(".selectColor").text(color);
            $(".colorNameBox").css("font-weight", "400")
            $(`#color${color}`).css("font-weight", "bold")
            $("#selectColorName").val();
            $("#selectRgb").val();
            $("#selectColorName").val(color);
            $("#selectRgb").val(rgb);
        })
        // color의 박스를 선택했을때 선택하는 이벤트 끝

        //color에서 다음 버튼을 눌렀을때 시작하는 이벤트 시작
        $(".bouquet").on("click", ".colorNextButton", (e) => {
            const color = $("#selectColorName").val();
            if (color == undefined || color == "") {
                Swal.fire({
                    icon: 'warning', text: '색상을 선택해주세요.', confirmButtonText: '확인'
                })

            } else {
                flowersView();
                //flower 이미지 클릭 시 이벤트 시작
            }
        })
        //color에서 다음 버튼을 눌렀을때 시작하는 이벤트 끝

        //flowerSelect에서 next 버튼 클릭시 이벤트 시작
        $(".content").on("click", ".flowerNextButton", () => {
            const selectColor = $("#selectColorName").val();
            const selectRgb = $("#selectRgb").val();
            let price = 0;
            for (let i = 0; this.flowerDataArr.length > i; i++) {
                for (let j = 0; flowerNumbers.length > j; j++) {
                    if (flowerNumbers[j] == this.flowerDataArr[i].flowerNumber) {
                        price += parseInt(this.flowerDataArr[i].floristProductPrice);
                    }
                }
            }
            if (flowerNumbers[0] == null) {
                Swal.fire({
                    text: '꽃을 선택해주세요.', confirmButtonText: '확인'
                })
            } else {
                $(".bouquet").empty();
                $(".bouquet").append("<div class='modalAllSelectArea'></div>")
                const allSelectPage = `       <div class="customMainText">꽃 다발 커스텀하기(3/3)</div>
                                                  <div class="customSubText">고른 꽃의 수량과 정보를 확인하세요.</div>
                                                    <div class="allSelectArea">
                                                    <div style="text-align: center; margin: 5px;">꽃의 위치를 움직여 원하는 위치를 꽃집에 알려주어요.</div>
                                                      <div class="allSelectBox" id="allSelectBox">

                                                      </div>
                                                  <div class="selectFooterBox">
                                                    <div class="selectInfoBox">
                                                      <div>선택한 종이 색 : <span>${selectColor}</span></div>
                                                      <div >선택한 꽃 : <span style="font-size: 1rem; width: 500px">${flowerNames.toString()}</span></div>
                                                    </div>
                                                    
                                                    <div class="buyInfoBox">
                                                      <div><button class="btn btn-outline-dark allSelectBefore">이전</button> </div>
                                                      <div style="font-size: 1.5rem"> 총 ${flowerNumbers.length}개</div>
                                                      <div style="font-size: 1.5rem">가격 : <span class="selectAlltotalCost">${price}</span> 원</div>
                                                      <div class="selectButtonBox">
                                                        <div><button class="btn btn-outline-primary allSelectBasket">장바구니</button></div>
                                                      </div>
                                                    </div>
                                                  </div>

                                    `
                $(".modalAllSelectArea").append(allSelectPage);
                $(".allSelectBox").css("background-color", `${selectRgb}`)
                for (let i = 0; this.flowerDataArr.length > i; i++) {
                    for (let j = 0; flowerNumbers.length > j; j++) {
                        if (flowerNumbers[j] == this.flowerDataArr[i].flowerNumber) {
                            const flowers = `         
                                            <div class="oneSelectFlowerBox mover product${i}">
                                              <div class="selectFlowerImageBox"><img src="/image/florist_product/${this.flowerDataArr[i].floristMainImage}" class="selectFlowerImage" style="-webkit-user-drag: none; " ></div>
                                              <div class="selectFlowerNameBox">${this.flowerDataArr[i].flowerName}(${(this.flowerDataArr[i].flowerColor == null) ? "" : this.flowerDataArr[i].flowerColor})</div>
                                              <div class="selectFlowerPriceBox"> <span style="font-size: 1rem" id="price${this.flowerDataArr[i].flowerNumber}">${this.flowerDataArr[i].floristProductPrice}</span> 원</div>
                                              <div class="selectFlowerCountBox">
                                              <button class="btn btn-outline-dark decrease" data-number="${this.flowerDataArr[i].flowerNumber}">-</button>
                                               <span style="font-size: 1rem" id="flowerCount${this.flowerDataArr[i].flowerNumber}">1</span> 송이 
                                               <button class="btn btn-outline-dark increase" data-number="${this.flowerDataArr[i].flowerNumber}">+</button></div>
                                            </div>`
                            $(".allSelectBox").append(flowers);
                        }

                    }
                }
                $(".content").on("mouseenter", ".mover", (e => {
                    const $target = e.target.closest(".mover")
                    $($target).css("z-index", "3");
                    draggable($target);
                }))
                $(".content").on("mouseleave", ".mover", (e) => {
                    const $target = e.target.closest(".mover")
                    $($target).css("z-index", "0");
                })
                $(".allSelectBefore").on("click", () => {
                    flowerNames = [];
                    flowerNumbers = [];
                    flowersView();
                })
                $(".modalAllSelectArea").on("click", ".decrease", (e) => {
                    let flowerPrice = 0;

                    const flowerNumber = e.target.dataset.number
                    $.each(this.flowerDataArr, (i, r) => {
                        if (this.flowerDataArr[i].flowerNumber == flowerNumber) {
                            flowerPrice = this.flowerDataArr[i].floristProductPrice;
                        }
                    })
                    const count = parseInt($(`#flowerCount${flowerNumber}`).text());
                    if (count == 1) {
                        Swal.fire({
                            icon: 'warning', text: '최소 수량은 1개 입니다.', confirmButtonText: '확인'
                        })
                    } else {
                        $(`#flowerCount${flowerNumber}`).text(count - 1);
                        $(`#price${flowerNumber}`).text(flowerPrice * (count - 1));
                        let totalCost = parseInt($(".selectAlltotalCost").text());
                        console.log(totalCost);
                        totalCost -= flowerPrice;
                        $(".selectAlltotalCost").text(totalCost);
                    }

                })
                $(".modalAllSelectArea").on("click", ".increase", (e) => {
                    let flowerPrice = 0;
                    const flowerNumber = e.target.dataset.number
                    $.each(this.flowerDataArr, (i, r) => {
                        if (this.flowerDataArr[i].flowerNumber == flowerNumber) {
                            flowerPrice = this.flowerDataArr[i].floristProductPrice;
                        }
                    })
                    const count = parseInt($(`#flowerCount${flowerNumber}`).text());
                    $(`#flowerCount${flowerNumber}`).text(count + 1);
                    $(`#price${flowerNumber}`).text(flowerPrice * (count + 1));
                    let totalCost = parseInt($(".selectAlltotalCost").text());
                    totalCost += flowerPrice;
                    $(".selectAlltotalCost").text(totalCost);
                })


            }
        })

        //color에서 초기화 버튼 시작
        $(".bouquet").on("click", ".colorReset", () => {
            colorReset();
        })
        //color에서 초기화 버튼 끝
        //이전버튼 누를시 color로 돌아가는 버튼 시작
        $(".bouquet").on("click", ".backButton", (e) => {
            const color = $("#selectColorName").val();
            viewColor(color);
        })
        //이전버튼 누를시 color로 돌아가는 버튼 끝
        //장바구니 클릭시 이벤트 시작
        $(".content").on("click", ".allSelectBasket", (e) => {
            const cookie = document.cookie.split("=")[1]
            if (cookie == null) {
                Swal.fire({
                    icon: 'warning', text: '로그인이 필요합니다.', confirmButtonText: '확인'
                })
                location.href = "/sign/sign_in"
                return
            }
            // bouquet save
            const totalCost = parseInt($(".selectAlltotalCost").text());

            // "floristNumber" :  this.floristData.floristNumber

            const bouquetColorRgb = $("#selectRgb").val();
            $.ajax({
                url: "/bouquet/save",
                method: "post",
                data: {totalCost, bouquetColorRgb, "floristNumber": this.floristData.floristNumber, "cookie": cookie}
            }).then(r => {
                this.bouquetNumber = r;
                console.log("bouquetNumber : " + this.bouquetNumber);
                const floristNumber = this.floristData.floristNumber;
                //이미지 서버로 전송
                const capture = document.getElementById("allSelectBox");
                html2canvas(capture, {
                    allowTaint: true, useCORS: true, /* 아래 3 속성이 canvas의 크기를 정해준다. */
                    width: capture.offsetWidth, height: capture.offsetHeight, scale: 1

                }).then(function (canvas) {
                    let imageURL = canvas.toDataURL('image/png');
                    imageURL = imageURL.replace("data:image/png;base64,", "");
                    //이미지 추가
                    $.ajax({
                        url: "/pickup/save_photo", method: "post", data: {"imageURL": imageURL, "bouquetNumber": r}
                    })
                }).catch(function (err) {
                    console.log(err);
                });
                //장바구니에 추가
                $.ajax({
                    url: "/pickup/cart/insert",
                    method: "post",
                    data: {"cookie": cookie, "floristNumber": floristNumber, "bouquetNumber": r}
                }).then(r => {
                    Swal.fire({
                        icon: 'success', title: '장바구니에 추가되었습니다.', showConfirmButton: false, timer: 1000
                    })
                })

            })

        })
    }
}

const placeAddress = document.querySelector("#placeName");
if (placeAddress) {
    document.querySelector("#searchInput").value = placeAddress.value;
}
const lat = document.querySelector('#lat');
const long = document.querySelector('#long');
const floristLat = document.querySelector("#floristName");
const floristLong = document.querySelector("#floristLatitude");
const floristNumber = document.querySelector("#floristNumber");
const flowerName = document.querySelector("#flowerName");

if (flowerName) {
    document.querySelector('#flowerTargetName').textContent = `${flowerName.value}`;
}
document.querySelector('#resetBtn').addEventListener('click', () => {
    if (flowerName) {
        flowerName.value = ""
        searchFlowerNameFloristList = [];
        document.querySelector('#flowerTargetName').textContent = ``;
        removeMarker();
        let center = map.getCenter();
        moveGetList(center.La, center.Ma);
    }
})

// flowerName 있는 경우라면 해당하는 꽃집 가져오고 아니라면 전체 긁어옴
function getList() {
    if (flowerName) {
        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

        let urlencoded = new URLSearchParams();
        urlencoded.append("query", flowerName.value);
        let requestOptions = {
            method: 'POST', headers: myHeaders, body: urlencoded, redirect: 'follow'
        };

        fetch("http://localhost:9000/api/v1/florist/florist/query", requestOptions)
            .then(response => response.json())
            .then(list => {
                searchFlowerNameFloristList = list;
                rootFloristListPrint(list);
            })
            .catch(error => console.log('error', error));
    } else {

        fetch("http://localhost:9000/api/v1/florist/florist_list")
            .then((data) => data.json())
            .then(list => {
                rootFloristListPrint(list);
            }).catch(err => console.log(err));
    }
}

if (lat === null) {
    window.addEventListener("DOMContentLoaded", getList);
} else {
    if (lat.value && lat.value) {
        const coords = new kakao.maps.LatLng(lat.value, long.value);
        map.panTo(coords);
        moveGetList(long.value, lat.value);
    } else {
        window.addEventListener("DOMContentLoaded", getList);
    }
}

// 드래그 하면 리스트 띄우기 // root 사용됨
function moveGetList(x, y) {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

    const urlencoded = new URLSearchParams();
    urlencoded.append("x", x);
    urlencoded.append("y", y);

    const requestOptions = {
        method: "POST", headers: myHeaders, body: urlencoded, redirect: "follow",
    };
    fetch("http://localhost:9000/api/v1/florist/florist_list_query_x_y", requestOptions)
        .then(data => data.json())
        .then((list) => {
            if (searchFlowerNameFloristList.length > 0) {
                list = transFormList(searchFlowerNameFloristList, list)
            }
            rootFloristListPrint(list);

        })
        .catch(err => console.log(err));
    if (lat !== null && long !== null) {
        lat.value = "";
        long.value = "";
    }
}

// map , sideModalCon create
// root
function rootFloristListPrint(floristList) {
    document.querySelectorAll('.sideItem')
        .forEach(v => document.querySelector('.sideList').removeChild(v));
    floristList.forEach((data) => {
        const mapObj = new sideItemObj(String(data.floristLatitude), String(data.floristLongtitude), data);
        mapObj.createItem(data);
        mapObj.setMarker();
        mapObj.setAddEvent();
    });
}

// 지도 드래그시에 해당하는 중심 좌표를 기준으로 리스트 다시 작성
kakao.maps.event.addListener(map, 'dragend', function (data) {
    removeMarker();
    let center = map.getCenter();
    moveGetList(center.La, center.Ma);
});

function transFormList(obj1, obj2) {
    return obj1.filter(arr1 => obj2.some(arr2 => arr1.floristName === arr2.floristName))
}

function removeSpinnerBox() {
    document.querySelector(".spinnerBox").remove();
}

const colorReset = () => {
    $("#selectColorName").val("");
    $("#selectRgb").val("");
    $(".colorNameBox").css("font-weight", "400")
    $(".customColorBox").css("background-color", "#ffffff");
    $(".selectColor").text(" ");
}

function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}