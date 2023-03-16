$("#Qna_update_button").on("click", function() {
		document.QnaForm.action = "/qna/update";
		document.QnaForm.submit();
	});