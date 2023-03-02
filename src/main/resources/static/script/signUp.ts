let emailTest =
  /([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|\"([]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|[[\t -Z^-~]*])/;
const userEmail = document.querySelector("#emailInput") as HTMLInputElement;
const userAddress = document.querySelector("#addressInput") as HTMLInputElement;
const userAddressDetail = document.querySelector(
  "#addressDetailInput"
) as HTMLInputElement;
const p1Num = document.querySelector("#phoneInput1") as HTMLInputElement;
const p2Num = document.querySelector("#phoneInput2") as HTMLInputElement;
const p3Num = document.querySelector("#phoneInput3") as HTMLInputElement;
const userName = document.querySelector("#nameInput") as HTMLInputElement;
// input 체크
{
  const reg = new RegExp(emailTest);
  {
    userEmail.addEventListener("change", function () {
      this.value = this.value.trim();
      const text = document.querySelector(".emailTestFail") as HTMLSpanElement;
      if (!reg.test(this.value)) {
        text.style.display = "block";
      } else {
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
