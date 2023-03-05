"use strict";
let emailTest = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
const userEmail = document.querySelector("#emailInput");
const userAddress = document.querySelector("#addressInput");
const userAddressDetail = document.querySelector("#addressDetailInput");
const p1Num = document.querySelector("#phoneInput1");
const p2Num = document.querySelector("#phoneInput2");
const p3Num = document.querySelector("#phoneInput3");
const userName = document.querySelector("#nameInput");
const submitBtn = document.querySelector("#submit");
const body = document.querySelector("body");
const loadingSpinner = document.createElement("div");
loadingSpinner.setAttribute("class", "spinner");
const reg = new RegExp(emailTest);
// 유저 입력 확인하기
// 유저 이메일 regex 체크
{
    userEmail.addEventListener("change", function () {
        this.value = this.value.trim();
        const text = document.querySelector(".emailTestFail");
        if (!reg.test(this.value)) {
            text.style.display = "block";
        }
        else {
            text.style.display = "none";
        }
    });
}
// 전화번호 다음으로 넘기기
{
    p1Num.addEventListener("keyup", function () {
        if (p1Num.value.length == 3) {
            p2Num.focus();
        }
    });
    p2Num.addEventListener("keyup", function () {
        if (p2Num.value.length == 4) {
            p3Num.focus();
        }
    });
}
// 우편 번호까지 모두 받고 api/v1/sign/signUp 전송
{
    submitBtn.addEventListener("click", (e) => {
        // 입력 값 확인
        userEmail.value.trim();
        userAddress.value.trim();
        userAddressDetail.value.trim();
        p1Num.value.trim();
        p2Num.value.trim();
        p3Num.value.trim();
        userName.value.trim();
        if (userEmail.value.length <= 0) {
            alert("이메일을 입력하세요.");
            userEmail.focus();
            return;
        }
        if (userName.value.length <= 0) {
            alert("이름을 입력하세요.");
            userName.focus();
            return;
        }
        if (userAddress.value.length <= 0) {
            console.log(userAddress.value.length);
            alert("주소를 입력하세요.");
            userAddress.focus();
            return;
        }
        if (userAddressDetail.value.length <= 0) {
            console.log(userAddress.value.length);
            alert("상세 주소를 입력하세요.");
            userAddressDetail.focus();
            return;
        }
        if (p1Num.value.length <= 0 ||
            p2Num.value.length <= 0 ||
            p3Num.value.length <= 0) {
            alert("연락처를 입력하세요.");
            p1Num.focus();
            return;
        }
        if (userName.value.length < 0) {
            alert("이메일을 입력하세요.");
            userName.focus();
            return;
        }
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Cookie", "JSESSIONID=5D73E913A087AFB1C62CB113A06A13D2");
        const inputData = JSON.stringify({
            userEmail: userEmail.value,
            userAddress: userAddress.value,
            userAddressDetail: userAddressDetail.value,
            userPhoneNumber: `${p1Num.value}-${p2Num.value}-${p3Num.value}`,
            userName: userName.value,
        });
        const requestOptions = {
            method: "POST",
            headers: myHeaders,
            body: inputData,
            redirect: "follow",
        };
        const reg = new RegExp(emailTest);
        if (!reg.test(userEmail.value)) {
            console.log("error");
            return;
        }
        fetch("http://localhost:9000/api/v1/sign/sign_up", requestOptions)
            .then((data) => data.json())
            .then((data) => {
                console.log(data);
                {
                    if (data.status == 201) {
                        const modal = document.createElement("div");
                        const modalElement = `
        <div id="modal" class="modal-overlay">
        <div class="modal-window">
          <div class="title">
            <h2>안녕하세요 ${userName.value}님</h2>
          </div>
          <div class="userText">
            <span
              >유저님의 이메일 ${userEmail.value}으로 인증번호를
              보내드렸습니다.</span
            >
            <br />
            <span
              >만약 이메일로 인증번호가 오지 않았다면 <span class="retry">클릭해주세요</span>.</span
            >
          </div>
          <div class="close-area">X</div>
          <div class="content">
            <div class="inputOtpBox">
              <input
                class="inputOtp"
                id="inpit1"
                type="text"
                maxlength="1"
              />
              <input
                class="inputOtp"
                id="inpit2"
                type="text"
                maxlength="1"
              />
              <input
                class="inputOtp"
                id="inpit3"
                type="text"
                maxlength="1"
              />
              <input
                class="inputOtp"
                id="inpit4"
                type="text"
                maxlength="1"
              />
              <input
                class="inputOtp"
                id="inpit5"
                type="text"
                maxlength="1"
              />
              <input
                class="inputOtp"
                id="inpit6"
                type="text"
                maxlength="1"
              />
            </div>
          </div>
        </div>
      </div>
        `;
                        modal.innerHTML = modalElement;
                        body === null || body === void 0 ? void 0 : body.append(modal);
                        const closeBtn = modal.querySelector(".close-area");
                        const modalOver = modal.querySelector(".modal-overlay");
                        closeBtn.addEventListener("click", (e) => {
                            if (confirm("아직 회원 가입이 진행중입니다. 정말로 닫으실건가요?")) {
                                modalOver.style.display = "none";
                            }
                        });
                        const retryBtn = modal.querySelector(".retry");
                        retryBtn === null || retryBtn === void 0 ? void 0 : retryBtn.addEventListener("click", () => { });
                        const iList = modal.querySelectorAll(".inputOtp");
                        iList.forEach((v, k) => {
                            v.addEventListener("keyup", () => {
                                var _a;
                                if (v.value.length == 1) {
                                    if (iList[k + 1] == undefined) {
                                        const ioBox = modal.querySelector(".inputOtpBox");
                                        ioBox.style.display = "none";
                                        (_a = modal.querySelector(".content")) === null || _a === void 0 ? void 0 : _a.append(loadingSpinner);
                                        let otp = "";
                                        iList.forEach((v) => (otp += v.value));
                                        const body = JSON.stringify({
                                            userEmail: userEmail.value,
                                            userOtp: otp,
                                        });
                                        const requestOptions = {
                                            method: "POST",
                                            headers: myHeaders,
                                            body: body,
                                            redirect: "follow",
                                        };
                                        fetch("http://localhost:9000/api/v1/sign/otp_check", requestOptions)
                                            .then((data) => data.json())
                                            .then((data) => {
                                                var _a;
                                                if (data.status == 200) {
                                                    alert("회원가입에 성공하였습니다.");
                                                    location.href = "http://localhost:9000/sign/sign_in";
                                                }
                                                else {
                                                    (_a = modal.querySelector(".spinner")) === null || _a === void 0 ? void 0 : _a.remove();
                                                    ioBox.style.display = "flex";
                                                    alert("인증 번호를 다시 확인해주세요.");
                                                }
                                            })
                                            .catch((err) => {
                                                console.log(err);
                                            });
                                    }
                                    iList[k + 1].focus();
                                }
                            });
                        });
                        return;
                    }
                    else if (data.status == 400) {
                        alert(data.message);
                    }
                    else if (data.status == 409) {
                        alert(data.message);
                    }
                    else {
                        alert(data.message);
                    }
                }
            })
            .catch((err) => {
                console.log(err);
            });
    });
}
