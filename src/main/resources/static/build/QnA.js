$("#QnA_write_button").on("click", function () {
  document.QnAForm.qnaNumber.value = "";
  document.QnAForm.action = "/QnA/write";
  document.QnAForm.submit();
});
