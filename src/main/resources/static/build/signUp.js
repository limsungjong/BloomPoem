"use strict";
let emailTest = /([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|\"([]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|[[\t -Z^-~]*])/;
const userEmail = document.querySelector("#emailInput");
const userAddress = document.querySelector("#addressInput");
const userAddressDetail = document.querySelector("#addressDetailInput");
const p1Num = document.querySelector("#phoneInput1");
const p2Num = document.querySelector("#phoneInput2");
const p3Num = document.querySelector("#phoneInput3");
const userName = document.querySelector("#nameInput");
// input 체크
{
    const reg = new RegExp(emailTest);
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
}
