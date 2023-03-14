const body = document.querySelector("body");

const mapOption = {
    center: new kakao.maps.LatLng(37.442928, 126.670556), // 지도의 중심좌표
    level: 4, // 지도의 확대 레벨
};

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

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: urlencoded,
        redirect: "follow",
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
                console.log(result.documents[0]);

                const requestOptions = {
                    method: "POST",
                    headers: myHeaders,
                    body: urlencoded,
                    redirect: "follow",
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
        .catch((error) => console.log("error", error));
});

class sideItemObj {
    bucketDataArr = [];
    // x 좌표
    // y 좌표
    // florist 꽃 집 정보
    // {}
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
    }

    // 사이드 아이템 생성하고 추가
    createItem(data) {
        const sideItem = document.createElement("ul");
        sideItem.setAttribute("class", "sideItem");
        sideItem.innerHTML = `
    <li class="floristTitle">
      <span>${this.floristData.floristName}</span>
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
        const markerPosition = new kakao.maps.LatLng(
            this.florist_longitude,
            this.florist_latitude
        );
        const marker = new kakao.maps.Marker({
            position: markerPosition,
        });
        markers.push(marker);
        const iwContent = `<div style="padding:5px; font-size:1.2rem; width:200px; height:40px; display:flex;">${this.floristData.floristName}</div>`, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

        // 인포윈도우를 생성합니다
        const infowindow = new kakao.maps.InfoWindow({
            content: iwContent,
            removable: iwRemoveable,
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
        });
    }

    // 마커에 이벤트 추가하기
    setAddEvent() {
        this.sideFloristItem.addEventListener("click", (e) => {
            this.fetchToCreateModal(this.floristData);
        });
    }

    // fetch florist_product_list_detail 에 보내서 모달 만들기
    fetchToCreateModal() {

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

        const urlencoded = new URLSearchParams();
        urlencoded.append("floristNumber", this.floristData.floristNumber);

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: urlencoded,
            redirect: 'follow'
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
                                document.querySelector('.content ul').remove();
                            }
                            switch (e.target.textContent) {
                                case "아름다운 꽃":
                                    this.createModalFlower();
                                    break;
                                case "장바구니":
                                    this.fetchToBucket();
                                    break;
                                case "매장 정보":
                                    break;
                                case "리뷰":
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
            </div>
            <div class="close-area">
                <i class="fas fa-times"></i>
            </div>
            <ul class="modal-nav">
              <li class="active">아름다운 꽃</li>
              <li>장바구니</li>
              <li>매장 정보</li>
              <li>리뷰</li>
            </ul>
            <div class="content">
            </div>
          </div>
        `;
        this.modalContainer = box;
        document.querySelector("body").append(box);
    }

    // flowerTab 만듬 =>
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
        <span class="flowerDetailSpan">${flower.flowerColor} ${flower.flowerName}</span>
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
                    alert("최대 갯수는 100개입니다.");
                    e.target.value = 100;
                }
            });

            flowerLiBox
                .querySelector(".flowerCntInc")
                .addEventListener("click", (e) => {
                    if (flowerCntInput.value > 99) {
                        alert("최대 갯수는 100개입니다.");
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
                        alert("최대 갯수는 100개입니다.");
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
                    console.log(flowerCntInput.value);
                    if (0 > parseInt(flowerCntInput.value) > 101) {
                        return;
                    }
                    const bucketData = {
                        flowerNumber: flower.flowerNumber,
                        flowerCount: parseInt(flowerCntInput.value),
                        floristNumber: this.floristData.floristNumber,
                        floristMainImage: flower.floristMainImage,
                        flowerName: flower.flowerName,
                        floristProductPrice: flower.floristProductPrice,
                    };
                    console.log(bucketData);
                    if (this.bucketDataArr.length == 0) {
                        this.bucketDataArr.push(bucketData);
                        alert("장바구니로 이동되었습니다.");
                        this.bucketToFetch();
                        return;
                    }

                    if (
                        !this.bucketDataArr.find(
                            (v) => v.flowerNumber == bucketData.flowerNumber
                        )
                    ) {
                        alert("장바구니로 이동되었습니다.");
                        this.bucketDataArr.push(bucketData);
                    }

                    console.log(this.bucketDataArr);
                    const duplicateFlowerNumber = this.bucketDataArr.findIndex(
                        (v) => v.flowerNumber == bucketData.flowerNumber
                    );

                    console.log(duplicateFlowerNumber);
                    if (duplicateFlowerNumber == 0 || duplicateFlowerNumber > 0) {
                        this.bucketDataArr[duplicateFlowerNumber].flowerCount =
                            this.bucketDataArr[duplicateFlowerNumber].flowerCount +
                            bucketData.flowerCount;
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
        console.log(this.bucketDataArr);
        const raw = JSON.stringify(this.bucketDataArr);

        const requestOptions = {
            method: "POST",
            headers: myHeaders,
            body: raw,
            redirect: "follow",
        };

        fetch(
            "http://localhost:9000/api/v1/pick_up/pick_up_cart_update",
            requestOptions
        )
            .then((response) => {
                console.log(response);
                return response.text();
            })
            .then((result) => console.log(result))
            .catch((error) => console.log("error", error));
    }

    // 장바구니 modal 만듬
    createModalBucket() {
        const bucketListTab = document.createElement("ul");
        bucketListTab.setAttribute("class", "bucketList");

        if (!this.bucketDataArr) {
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
        <span class="bucketDetailSpan">${bucket.flowerName}</span>
          <br />
        <span class="bucketDetailSpan">가격 : ${
                numberAddComa(bucket.floristProductPrice)
            }</span>
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
          value="1"
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
                        bucketLi.querySelector(".bucketTotalPrice").textContent =
                            numberAddComa(bucketInput.value * bucket.floristProductPrice);
                    });
                    this.createBucketFooter();
                }

                if (e.target.value > 100) {
                    alert("최대 구매 수령은 100송이 입니다.");
                    e.target.value = 100;
                    this.bucketDataArr.map((v) => {
                        if (v.flowerNumber == bucket.flowerNumber) {
                            v.flowerCount = 100;
                        }
                        bucketLi.querySelector(".bucketTotalPrice").textContent =
                            numberAddComa(bucketInput.value * bucket.floristProductPrice);
                    });
                    this.createBucketFooter();
                }
            });

            bucketLi.querySelector(".bucketBuy").addEventListener("click", () => {});

            bucketLi.querySelector(".bucketCntInc").addEventListener("click", (e) => {
                if (bucketInput.value > 99) {
                    alert("최대 갯수는 100개입니다.");
                    e.target.value = 100;
                    this.bucketDataArr.map((v) => {
                        if (v.flowerNumber == bucket.flowerNumber) {
                            v.flowerCount = 100;
                        }
                        bucketLi.querySelector(".bucketTotalPrice").textContent =
                            numberAddComa(bucketInput.value * bucket.floristProductPrice);
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
                bucketLi.querySelector(".bucketTotalPrice").textContent =
                    numberAddComa(bucketInput.value * bucket.floristProductPrice);
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

                bucketLi.querySelector(".bucketTotalPrice").textContent =
                    numberAddComa(bucketInput.value * bucket.floristProductPrice);
            });
            bucketListTab.append(bucketLi);
        });
        this.tabContent = bucketListTab;
        this.modalContainer.querySelector(".content").append(this.tabContent);
        this.createBucketFooter();
    }

    // 서버에 있는 장바구니 받아옴
    fetchToBucket() {
        if (document.cookie == "") {
            return;
        }
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const requestOptions = {
            method: "GET",
            headers: myHeaders,
            redirect: "follow",
        };


        fetch(
            "http://localhost:9000/api/v1/pick_up/get_pick_up_cart",
            requestOptions
        )
            .then((response) => {
                return response.json();
            })
            .then((result) => {
                if (result == undefined) return;
                this.bucketDataArr = result;
                this.createModalBucket();
                console.log(this.bucketDataArr)
            })
    }

    // 확인용 모달 만들기
    createCheckModal() {
        const checkModal = document.createElement("div");
        checkModal.innerHTML = `
    <div id="checkModal" class="modal-overlay">
      <div id="checkModal">
        <div class="head">
          <div class="close-btn">
            <i class="fas fa-times"></i>
          </div>
        </div>
        <div class="middle">
          <div class="textBox">
            <span>다른 가게의 물품이 있습니다.</span> <br />
            <span>지금 장바구니에 있는 가게의 물품과</span> <br />
            <span>함께 담을 수 없습니다.</span><br />
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
            method: 'DELETE',
            headers: myHeaders,
            redirect: 'follow'
        };
        fetch("http://localhost:9000/api/v1/pick_up/pick_up_cart_delete", requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
    }

    // 로그인 되었는지 확인함 boolean
    loginChecker() {
        const cookie = document.cookie;

        let check = false;
        if (cookie == "") {
            alert("로그인이 필요합니다.");
            location.href = "http://localhost:9000/sign/sign_in";
        }
        if (cookie == undefined) {
            alert("로그인이 필요합니다.");
            location.href = "http://localhost:9000/sign/sign_in";
        }
        if (cookie == null) {
            alert("로그인이 필요합니다.");
            location.href = "http://localhost:9000/sign/sign_in";
        }

        if (cookie.split("=")[0] == "Authorization") check = true;
        return check;
    }

    // 장바구니 footer 만듬
    createBucketFooter() {
        console.log("asd");
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

        console.log("605");
        this.footerBuyHandler();
    }

    bucketFetchToBuy() {
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const raw = JSON.stringify(this.bucketDataArr);
        const requestOptions = {
            method: "POST",
            headers: myHeaders,
            body:raw,
            redirect: "follow",
        };


        fetch(
            "http://localhost:9000/api/v1/pick_up_order/startBuy",
            requestOptions
        )
            .then((response) => {
                return response.json();
            })
            .then((result) => {
                if (result == undefined) return;
                console.log(result);
                console.log(this.bucketDataArr)
            })
    }

    // flower 탭에 있는 꽃 구매 핸들링
    flowerBuyHandler(flowerLiBox, v) {
        const buyBtn = flowerLiBox.querySelector("#flowerBuyBtn");
        buyBtn.addEventListener("click", () => {
            const asd = flowerLiBox.querySelector(".flowerCount").value;
            console.log(asd);
            console.log(v);
            this.buyBucket = {};
        });
    }

    // 장바구니 푸터 구매 핸들링
    footerBuyHandler() {
        console.log(this.tabContent);

        const allBtn = this.tabContent.querySelector("#allChoice");
        const delBtn = this.tabContent.querySelector("#deleteChoice");
        const allBuy = this.tabContent.querySelector("#allBuy");

        allBtn.addEventListener("click", () => {
            this.tabContent
                .querySelectorAll(`input[type="checkbox"]`)
                .forEach((v) => (v.checked = true));
        });

        delBtn.addEventListener("click", () => {
            this.tabContent
                .querySelectorAll(`input[type="checkbox"]:checked`)
                .forEach((element) => {
                    console.log(element.name);
                    this.bucketDataArr.splice(
                        this.bucketDataArr.findIndex(
                            (data) => data.flowerNumber == element.name
                        )
                    );
                    this.tabContent.removeChild(element.parentNode.parentElement);
                });
            this.createBucketFooter();
        });

        allBuy.addEventListener("click", () => {});

        return this;
    }
}

// 시작과 함께 리스트 띄우기 // root 사용됨
function getList() {
    fetch("http://localhost:9000/api/v1/florist/florist_list")
        .then((data) => data.json())
        .then(list => {
            rootFloristListPrint(list);
        }).catch(err => console.log(err));
}

window.addEventListener("DOMContentLoaded", getList);

// 드래그 하면 리스트 띄우기 // root 사용됨
function moveGetList(x, y) {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

    const urlencoded = new URLSearchParams();
    urlencoded.append("x", x);
    urlencoded.append("y", y);

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: urlencoded,
        redirect: "follow",
    };
    fetch("http://localhost:9000/api/v1/florist/florist_list_query_x_y", requestOptions)
        .then(data => data.json())
        .then((list) => rootFloristListPrint(list))
        .catch(err => console.log(err));
}

// map , sideModalCon create
// root
function rootFloristListPrint(floristList) {
    document.querySelectorAll('.sideItem')
        .forEach(v =>
            document.querySelector('.sideList').removeChild(v));
    floristList.forEach((data) => {
        const mapObj = new sideItemObj(
            String(data.floristLatitude),
            String(data.floristLongtitude),
            data
        );
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
