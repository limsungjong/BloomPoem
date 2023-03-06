const body = document.querySelector("body");
const modalCon = document.querySelector("#modal");
const closeBtn = document.querySelector(".close-area");
const container = document.querySelector("#map");

const googleMapApi = "AIzaSyAzdBLLKTSqZ_-KMogDx6tHdaMz4OmgNOM";
const mapOption = {
    center: new kakao.maps.LatLng(37.442928, 126.670556), // 지도의 중심좌표
    level: 4, // 지도의 확대 레벨
};

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
// 공통된 하나의 글로벌 객체로 사용함. 추가 금지
const map = new kakao.maps.Map(document.querySelector("#map"), mapOption);

// document.querySelector(".searchButton").addEventListener("click", (e) => {
//   // 주소-좌표 변환 객체를 생성합니다
//   const geocoder = new kakao.maps.services.Geocoder();

//   // 주소로 좌표를 검색합니다
//   geocoder.addressSearch(
//     document.querySelector(".searchTerm").value,
//     function (result, status) {
//       console.log(result);
//       console.log(status);
//       // 정상적으로 검색이 완료됐으면
//       if (status === kakao.maps.services.Status.OK) {
//         console.log(result);
//         console.log(status);
//         const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
//         const marker = new kakao.maps.Marker({
//           position: coords,
//         });
//         map.panTo(coords);
//         console.log(coords);
//         // 그 다음에 위도와 경도를 가지고 다시 서버에 정보를 요청하여
//         // 다시 리스트를 받아서 보여줘야 함

//         // 결과값으로 받은 위치를 마커로 표시합니다
//         marker.setMap(map);
//       }
//     }
//   );
// });
document.querySelector(".searchButton").addEventListener("click", (e) => {
    const myHeaders = new Headers();
    myHeaders.append("Authorization", "KakaoAK 7367f4f59192633ced366e0cd2cce9fa");
    myHeaders.append("Content-Type", "application/x-www-form-urlencoded");
    myHeaders.append("Cookie", "kd_lang=ko");

    const urlencoded = new URLSearchParams();
    urlencoded.append("query", "인천터미널");

    const requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: urlencoded,
        redirect: "follow",
    };

    fetch("https://dapi.kakao.com/v2/local/search/keyword.json", requestOptions)
        .then((response) => response.json())
        .then((result) => {
            const data = result.documents[0];
        })
        .catch((error) => console.log("error", error));
});

class sideItemObj {
    constructor(x, y, data) {
        this.florist_latitude = x;
        this.florist_longitude = y;
        this.element = null;
        this.data = data;
    }

    // 사이드 아이템 생성하고 추가
    createItem(data) {
        const sideItem = document.createElement("ul");
        sideItem.setAttribute("class", "sideItem");
        sideItem.innerHTML = `
    <li class="floristImgBox">
      <img class="floristImg" />
    </li>
    <li class="floristTitle">
      <span>${data.florist_name}</span>
    </li>
    <li class="floristPhoneNumber">
      <span>${data.florist_phone_number}</span>
    </li>
    <li class="floristAddress">
      <span>${data.florist_address}</span>
    </li>
    `;
        document.querySelector(".sideList").appendChild(sideItem);
        this.element = sideItem;
    }

    // 생성된 아이템 위치로 마커추가
    setMarker() {
        const markerPosition = new kakao.maps.LatLng(
            this.florist_latitude,
            this.florist_longitude
        );
        const marker = new kakao.maps.Marker({
            position: markerPosition,
        });
        const iwContent = `<div style="padding:5px; font-size:1.2rem; width:200px; height:40px; display:flex;">${this.data.florist_name}</div>`, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
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
            createModal(this.data);
        });
    }
}

const flo1 = {
    florist_number: 1,
    florist_address: "인천 미추홀구 학익소로 53 동문빌딩",
    florist_phone_number: "032-438-0011",
    florist_name: "그린플라워",
    florist_latitude: "37.4429280",
    florist_longitude: "126.670556",
};
const flo2 = {
    florist_number: 1,
    florist_address: "인천 미추홀구 매소홀로 468 1층",
    florist_phone_number: "0507-1347-4301",
    florist_name: "로지플라워",
    florist_latitude: "37.4393150",
    florist_longitude: "126.673826",
};
const flo3 = {
    florist_number: 1,
    florist_address: "인천 미추홀구 매소홀로 482 대진타운 1층 101호",
    florist_phone_number: "010-3168-4050",
    florist_name: "엘리스&플라워",
    florist_latitude: "37.4392206",
    florist_longitude: "126.675510",
};
const flo4 = {
    florist_number: 1,
    florist_address: "인천 미추홀구 한나루로 360 학산빌딩 1층",
    florist_phone_number: "0507-1376-6360",
    florist_name: "르밍플라워",
    florist_latitude: "37.4402290",
    florist_longitude: "126.662454",
};
const flo5 = {
    florist_number: 1,
    florist_address: "인천 미추홀구 한나루로 399 사랑,꽃",
    florist_phone_number: "0507-1381-7776",
    florist_name: "사랑꽃",
    florist_latitude: "37.4433246",
    florist_longitude: "126.664248",
};
const flo6 = {
    florist_number: 1,
    florist_address: "인천 남동구 인하로 523 미추홀빌딩 1층 클로리스플라워",
    florist_phone_number: "0507-1493-4112",
    florist_name: "클로리스플라워",
    florist_latitude: "37.4432622",
    florist_longitude: "126.704805",
};
const floristList = [flo1, flo2, flo3, flo4, flo5, flo6];

// map create
{
    floristList.forEach((data) => {
        const mapOjb = new sideItemObj(
            data.florist_latitude,
            data.florist_longitude,
            data
        );
        mapOjb.createItem(data);
        mapOjb.setMarker();
        mapOjb.setAddEvent();
    });
}

// add modal
function createModal(data) {
    const box = document.createElement("div");
    box.setAttribute("id", "modal");
    box.setAttribute("class", "modal-overlay");
    box.innerHTML = `
  <div class="modal-window">
    <div class="title">
      <h2>${data.florist_name}</h2>
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
