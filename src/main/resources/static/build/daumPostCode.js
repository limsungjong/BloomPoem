
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
