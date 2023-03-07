"use strict";
{
    const emailTest = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
    const reg = new RegExp(emailTest);
    const mailInput = document.querySelector("#emailInput");
    const mailSubmitBtn = document.querySelector("#emailSubmit");
    const loadingSpinner = document.createElement("div");
    loadingSpinner.setAttribute("class", "spinner");
    mailInput.addEventListener("change", function () {
        this.value = this.value.trim();
        const text = document.querySelector(".emailTestFail");
        if (!reg.test(this.value)) {
            text.style.display = "block";
        }
        else {
            text.style.display = "none";
        }
    });
    mailSubmitBtn.addEventListener("click", (e) => {
        if (mailInput.value.trim().length == 0) {
            alert("이메일을 입력해주세요.");
            mailInput.value = "";
            mailInput.focus();
            return;
        }
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        const inputData = JSON.stringify({
            userEmail: mailInput.value,
        });
        const requestOptions = {
            method: "POST",
            headers: myHeaders,
            body: inputData,
            redirect: "follow",
        };
        if (!reg.test(mailInput.value)) {
            console.log("error");
            return;
        }
        fetch("http://localhost:9000/api/v1/sign/otp_check", requestOptions)
            .then((data) => data.json())
            .then((data) => {
                if (data.status == 200) {
                    const body = document.querySelector("body");
                    const modal = document.createElement("div");
                    const modalElement = `
      <div id="modal" class="modal-overlay">
      <div class="modal-window">
        <div class="userText">
          <span
            >이메일 ${mailInput.value}으로 
            </br>
            </br>
            인증번호를
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
              autocomplete="off"
            />
            <input
              class="inputOtp"
              id="inpit2"
              type="text"
              maxlength="1"
              autocomplete="off"
            />
            <input
              class="inputOtp"
              id="inpit3"
              type="text"
              maxlength="1"
              autocomplete="off"
            />
            <input
              class="inputOtp"
              id="inpit4"
              type="text"
              maxlength="1"
              autocomplete="off"
            />
            <input
              class="inputOtp"
              id="inpit5"
              type="text"
              maxlength="1"
              autocomplete="off"
            />
            <input
              class="inputOtp"
              id="inpit6"
              type="text"
              maxlength="1"
              autocomplete="off"
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
                        if (confirm("아직 로그인이 진행중입니다. 정말로 닫으실건가요?")) {
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
                                    const myHeaders = new Headers();
                                    myHeaders.append("Content-Type", "application/json");
                                    const raw = JSON.stringify({
                                        userEmail: mailInput.value,
                                        userOtp: otp,
                                    });
                                    console.log(inputData);
                                    const requestOptions = {
                                        method: "POST",
                                        headers: myHeaders,
                                        body: raw,
                                        redirect: "follow",
                                    };
                                    fetch("http://localhost:9000/api/v1/sign/sign_in", requestOptions)
                                        .then((data) => {
                                            var _a;
                                            console.log(data);
                                            otp = "";
                                            if (data.status == 200) {
                                                alert("로그인에 성공하였습니다.");
                                                // location.href = "http://localhost:9000/";
                                                // location.reload();
                                                console.log(data.headers);
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
            })
            .catch((err) => console.log(err));
    });
}
