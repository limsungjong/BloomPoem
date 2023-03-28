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
            Swal.fire({
                icon: 'warning',
                text: '이메일을 입력해주세요.',
                confirmButtonText:'확인'
                })
            userEmail.focus();
            return;
        }
        if (userName.value.length <= 0) {
            Swal.fire({
                icon: 'warning',
                text: '이름을 입력해주세요.',
                confirmButtonText:'확인'
                })
            userName.focus();
            return;
        }
        if (userAddress.value.length <= 0) {
            console.log(userAddress.value.length);
            Swal.fire({
                icon: 'warning',
                text: '주소를 입력해주세요.',
                confirmButtonText:'확인'
                })
            userAddress.focus();
            return;
        }
        if (userAddressDetail.value.length <= 0) {
            console.log(userAddress.value.length);
            Swal.fire({
                icon: 'warning',
                text: '상세 주소를 입력해주세요.',
                confirmButtonText:'확인'
                })
            userAddressDetail.focus();
            return;
        }
        if (p1Num.value.length <= 0 ||
            p2Num.value.length <= 0 ||
            p3Num.value.length <= 0) {
            Swal.fire({
                icon: 'warning',
                text: '연락처를 입력해주세요.',
                confirmButtonText:'확인'
                })
            p1Num.focus();
            return;
        }
        if (userName.value.length < 0) {
            Swal.fire({
                icon: 'warning',
                text: '이메일을 입력해주세요.',
                confirmButtonText:'확인'
                })
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
            .then((res) => {
                console.log(res)
                if(res.status == 400) {
                    Swal.fire({
                        icon: 'warning',
                        text: '입력 형식을 다시 확인해주세요.',
                        confirmButtonText:'확인'
                        })
                    return;
                }
                return res.json();
            })
            .then((data) => {
                submitBtn.disabled = false;

                console.log(data);
                if(data == undefined) {
                    return;
                }
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
              >${userName.value}님의 이메일 ${userEmail.value}으로 인증번호를
              보내드렸습니다.</span
            >
            <br />
            <span
              >만약 이메일로 인증번호가 오지 않았다면 <span class="retry" style="color: #3ea4f8">클릭해주세요</span>.</span
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
                        retryBtn === null || retryBtn === void 0 ? void 0 : retryBtn.addEventListener("click", () => {
                            fetch("http://localhost:9000/api/v1/sign/otp_check", requestOptions)
                                .then(res => res.text())
                                .then((data) => {
                                    Swal.fire({
                                        icon: 'success',
                                        title: '인증 번호를 다시 보내드렸습니다.',
                                        showConfirmButton: false,
                                        timer: 1000
                                        })
                                }).catch(err => {
                                    Swal.fire({
                                        icon: 'error',
                                        text: '회원 가입중에 오류가 발생하였습니다. 다시 진행해주세요.',
                                        confirmButtonText: '확인'
                                        })
                                    location.href = "http://localhost:9000/sign_up";
                            })

                        });
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
                                        fetch("http://localhost:9000/api/v1/sign/sign_in", requestOptions)
                                            .then((data) => data.json())
                                            .then((data) => {
                                                var _a;
                                                if (data.status == 200) {
                                                    Swal.fire({
                                                        icon: 'success',
                                                        title: '회원가입에 성공하였습니다.',
                                                        showConfirmButton: false,
                                                        timer: 1000
                                                        })
                                                    history.back();
                                                }
                                                else {
                                                    (_a = modal.querySelector(".spinner")) === null || _a === void 0 ? void 0 : _a.remove();
                                                    ioBox.style.display = "flex";
                                                    Swal.fire({
                                                        icon: 'warning',
                                                        text: '인증 번호를 다시 확인해주세요.',
                                                        confirmButtonText:'확인'
                                                        })
                                                }
                                            })
                                            .catch((err) => {
                                                submitBtn.disabled = false;
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
                        Swal.fire({
                            icon: 'warning',
                            text: data.message,
                            confirmButtonText:'확인'
                            })
//                        alert(data.message);
                        return;
                    }
                    else if (data.status == 409) {
                        Swal.fire({
                            icon: 'warning',
                            text: data.message,
                            confirmButtonText:'확인'
                            })
//                        alert(data.message);
                        return;
                    }
                    submitBtn.disabled = false;
                    return;
                }
            })
            .catch((err) => {
                submitBtn.disabled = false;
                console.log(err);
            });
    });
}
