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
}
