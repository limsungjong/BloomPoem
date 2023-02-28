let emailTest =
    /([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|\"([]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|[[\t -Z^-~]*])/;
const userEmail = document.querySelector("#emailInput");
const userAddress = document.querySelector("#addressInput");
const userAddressDetail = document.querySelector("#addressDetailInput");
const p1Num = document.querySelector("#phoneInput1");
const p2Num = document.querySelector("#phoneInput2");
const p3Num = document.querySelector("#phoneInput3");
const userName = document.querySelector("#nameInput");
// input 체크
{
}

// 우편 번호까지 모두 받고 전송
{
    const addressInput = document.querySelector("#addressInput");
    addressInput.addEventListener("click", () => {
        addressInput.value = "";
        new daum.Postcode({
            oncomplete: function (data) {
                const { jibunAddress, zonecode, roadAddress, userSelectedType } = data;
                document.querySelector("#addressInput").value =
                    userSelectedType == "R"
                        ? `( ${zonecode} ) ${roadAddress}`
                        : `( ${zonecode} ) ${jibunAddress}`;
            },
        }).open();
    });
    document.querySelector("#submit").addEventListener("click", (e) => {
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Cookie", "JSESSIONID=CAC23876470920D5AB5B67C6F297AAC0");

        const inputData = JSON.stringify({
            userEmail: userEmail.value,
            userAddress: addressInput.value,
            userAddressDetail: userAddressDetail.value,
            userPhoneNumber: `${p1Num.value}-${p2Num.value}-${p3Num.value}`,
            userName: document.querySelector("#nameInput").value,
        });

        const requestOptions = {
            method: "POST",
            headers: myHeaders,
            body: inputData,
            redirect: "follow",
        };
        console.log(userEmail.value);
        const reg = new RegExp(emailTest);
        if (!reg.test(userEmail.value)) {
            console.log("error");
            return;
        }

        fetch("http://localhost:9000/api/v1/sign/signUp", requestOptions)
          .then((data) => console.log(data))
          .catch((err) => console.log(err));
    });
}
