// 우편 번호까지 모두 받고 api/v1/sign/signUp 전송
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

    const reg = new RegExp(emailTest);
    if (!reg.test(userEmail.value)) {
      console.log("error");
      return;
    }

    fetch("http://localhost:9000/api/v1/sign/sign_up", requestOptions)
      .then((data) => console.log(data))
      .catch((err) => console.log(err));
  });
}
