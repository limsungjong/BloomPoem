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
                const cookie = document.cookie.split("=");
                var myHeaders = new Headers();
                myHeaders.append("Cookie", `${cookie[0]}=${cookie[1]}`);
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
                    .then((result) => floristListPrint(result))
                    .catch((error) => console.log("error", error));
            }
            removeMarker();
            const data = result.documents[0];
            const coords = new kakao.maps.LatLng(data.y, data.x);
            map.panTo(coords);
        })
        .catch((error) => console.log("error", error));
});

function removeMarker() {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

class sideItemObj {
    constructor(x, y, floristData, flowerData) {
        this.florist_latitude = x;
        this.florist_longitude = y;
        this.element = null;
        this.floristData = floristData;
        this.flowerData = flowerData;
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
        this.element = sideItem;
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
            createModal(this.data);
        });
    }

    setAddEvent() {
        this.element.addEventListener("click", (e) => {
            console.log(this.floristData);
            console.log(this.flowerData);
            createModal(this.floristData);
        });
    }
}

window.addEventListener("DOMContentLoaded", getList);

function getList() {
    fetch("http://localhost:9000/api/v1/pick_up/pick_up_list")
        .then((data) => data.json())
        .then(list => {
            floristListPrint(list);
        }).catch(err => console.log(err));
}

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
        .then((list) => floristListPrint(list))
        .catch(err => console.log(err));
}

// map create
function floristListPrint(floristList) {
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

// add modal
function createModal(floristData, flowerData) {
    console.log(floristData)
    console.log(flowerData)
    const box = document.createElement("div");
    box.setAttribute("id", "modal");
    box.setAttribute("class", "modal-overlay");
    box.innerHTML = `
  <div class="modal-window">
    <div class="title">
      <h2>${floristData.floristName}</h2>
    </div>
    <div class="close-area">X</div>
    <ul class="modal-nav">
      <li class="active">아름다운 꽃</li>
      <li>장바구니</li>
      <li>매장 정보</li>
      <li>리뷰</li>
    </ul>
    <div class="content"></div>
  </div>
  `;
    document.querySelector("body").appendChild(box);
    document.querySelector(".close-area").addEventListener("click", () => {
        document.querySelector("body").removeChild(box);
    });
    const navMenus = box.querySelectorAll(".modal-nav li");
    navMenus.forEach((v, i) => {
        v.addEventListener("click", (e) => {
            if (e.target.classList.value == "active") {
                console.log("ac");
                return;
            } else {
                navMenus.forEach((ele) => {
                    if (ele == e.target) {
                        ele.setAttribute("class", "active");
                    } else {
                        ele.removeAttribute("class", "active");
                    }
                });
            }
        });
    });
}


// 지도 드래그시에 해당하는 중심 좌표를 기준으로 리스트 다시 작성
kakao.maps.event.addListener(map, 'dragend', function (data) {
    removeMarker();
    let center = map.getCenter();
    moveGetList(center.La, center.Ma);
});