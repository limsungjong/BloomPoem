const body = document.querySelector("body");
const modalCon = document.querySelector("#modal");
const closeBtn = document.querySelector(".close-area");
const container = document.querySelector("#map");

const mapOption = {
    center: new kakao.maps.LatLng(37.442928, 126.670556), // 지도의 중심좌표
    level: 4, // 지도의 확대 레벨
};

let markers = [];

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
// 공통된 하나의 글로벌 객체로 사용함. 추가 금지
const map = new kakao.maps.Map(document.querySelector("#map"), mapOption);

// 마커 전부 제거 markers에 있는 모든 marker 제거
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
                var myHeaders = new Headers();
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
                fetch("http://localhost:9000/api/v1/pick_up/pick_up_list_query", requestOptions)
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
    // x 좌표
    // y 좌표
    // florist 꽃 집 정보
    // {}
    constructor(x, y, floristData, flowerDataArr, bucketDataArr) {
        this.florist_latitude = x;
        this.florist_longitude = y;
        this.floristData = floristData;
        this.flowerDataArr = flowerDataArr;
        this.bucketDataArr = bucketDataArr;
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
        var infowindow = new kakao.maps.InfoWindow({
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

        fetch("http://localhost:9000/api/v1/pick_up/florist_product_list_detail", requestOptions)
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
        document.querySelector("body").appendChild(this.modalContainer);

        // 모달창 위의 close btn
        document.querySelector(".close-area").addEventListener("click", () => {
            document.querySelector("body").removeChild(this.modalContainer);
        });

        // 모달창 위의 4개 탭 전환하기
        const navMenus = this.modalContainer.querySelectorAll(".modal-nav li");
        navMenus.forEach((v, i) => {
            v.addEventListener("click", (e) => {
                if (e.target.classList.value == "active") {

                } else {
                    navMenus.forEach((ele) => {
                        if (ele == e.target) {
                            document.querySelector('.content ul').remove();
                            switch (e.target.textContent) {
                                case "아름다운 꽃":
                                    this.createModalFlower();
                                    break;
                                case "장바구니":
                                    this.createModalBucket();
                                    break;
                                case "매장 정보":
                                    this.createModalFlower();
                                    break;
                                case "리뷰":
                                    this.createModalFlower();
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
            <div class="close-area">X</div>
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
    }

    // flowerTab 만듬 =>
    createModalFlower() {
        const flowerListTab = document.createElement("ul");
        flowerListTab.setAttribute('class', 'flowerList');

        this.flowerDataArr.forEach((flower => {
            const liFlowerHtml = `
          <div class="flowerImageBox">
              <img
                class="flowerMainImg"
                src="/image/florist_product/${flower.floristMainImage}"
                alt="꽃 이미지"
              />
          </div>
          <div class="flowerDetailContent">
            <span class="flowerDetailSpan">${flower.flowerName}</span>
              <br />
            <span class="flowerDetailSpan">가격 : ${flower.floristProductPrice}</span>
          </div>
          <div class="flowerDetailBuy">
            <div class="flowerCountBox">

                  <input
                    class="flowerCount"
                    type="number"
                    min="1"
                    max="100"
                    value="1"
                    data-flowerNumber=${flower.flowerNumber}
                  />

              <button class="btn btn-outline-secondary productBasket">
                10개 추가하기
              </button>
            </div>
            <div class="flowerBuyBox">
              <button class="btn btn-outline-primary productBasket">
                장바구니
              </button>
              <button class="btn btn-outline-success productBuy">
                구매하기
              </button>
            </div>
          </div>
        `;
            const flowerLi = document.createElement('li');
            flowerLi.setAttribute('class', 'flowerContent');
            flowerLi.innerHTML = liFlowerHtml;
            flowerListTab.append(flowerLi);
        }))
        this.tabContent = flowerListTab;
        this.modalContainer.querySelector(".content").append(this.tabContent);
    }

    createModalBucket() {
        const bucketListTab = document.createElement("ul");
        bucketListTab.setAttribute('class', 'bucketList');

        this.bucketDataArr.forEach((bucket => {
            const liBucketHtml = `
          <div class="flowerImageBox">
              <img
                class="flowerMainImg"
                src="/image/florist_product/${bucket.floristMainImage}"
                alt="꽃 이미지"
              />
          </div>
          <div class="flowerDetailContent">
            <span class="flowerDetailSpan">${bucket.flowerName}</span>
              <br />
            <span class="flowerDetailSpan">가격 : ${flower.floristProductPrice}</span>
          </div>
          <div class="flowerDetailBuy">
            <div class="flowerCountBox">

                  <input
                    class="flowerCount"
                    type="number"
                    min="1"
                    max="100"
                    value="1"
                    data-flowerNumber=${flower.flowerNumber}
                  />

              <button class="btn btn-outline-secondary productBasket">
                10개 추가하기
              </button>
            </div>
            <div class="flowerBuyBox">
              <button class="btn btn-outline-primary productBasket">
                장바구니
              </button>
              <button class="btn btn-outline-success productBuy">
                구매하기
              </button>
            </div>
          </div>
        `;
            const flowerLi = document.createElement('li');
            flowerLi.setAttribute('class', 'flowerContent');
            flowerLi.innerHTML = liFlowerHtml;
            flowerListTab.append(flowerLi);
        }))
        this.tabContent = flowerListTab;
        this.modalContainer.querySelector(".content").append(this.tabContent);
    }
}


// 시작과 함께 리스트 띄우기 // root 사용됨
function getList() {
    fetch("http://localhost:9000/api/v1/pick_up/pick_up_list")
        .then((data) => data.json())
        .then(list => {
            rootFloristListPrint(list);
        }).catch(err => console.log(err));
}

window.addEventListener("DOMContentLoaded", getList);

// 드래그 하면 리스트 띄우기 // root 사용됨
function moveGetList(x, y) {
    var myHeaders = new Headers();
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
    fetch("http://localhost:9000/api/v1/pick_up/pick_up_list_query", requestOptions)
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