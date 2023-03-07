$("#QnA_save_button").on("click", function () {
  document.QnAForm.qnaNumber.value = "";
  document.QnAForm.action = "/QnA";
  document.QnAForm.submit();
});

$("#QnA_list_button").on("click", function () {
  document.QnAForm.action = "/QnA";
  document.QnAForm.submit();
});
