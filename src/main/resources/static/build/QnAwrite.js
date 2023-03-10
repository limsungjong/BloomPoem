$(document).ready(function(){

    $("#QnA_title").focus();

//    $("#QnA_save_button").on("click", function () {
//    $("#QnA_save_button").prop("disabled", true);	//저장 버튼 비활성화
//
//        if($.trim($("#QnA_title").val()).length <= 0){
//            alert("제목을 입력해 주세요.");
//            $("#QnA_title").val("");
//            $("#QnA_title").focus();
//
//            $("#QnA_save_button").prop("disabled", false);	//저장 버튼 활성화
//
//            return;
//        }
//
//        if($.trim($("#QnA_content").val()).length <= 0){
//            alert("내용을 입력해 주세요.");
//            $("#QnA_content").val("");
//            $("#QnA_content").focus();
//
//            $("#QnA_save_button").prop("disabled", false);	//저장 버튼 활성화
//
//            return;
//        }
//
//        var form = $("#QnAForm")[0];
//		var formData = new FormData(form);
//		console.log(formData);
//
//        $.ajax({
//            type:"POST",
//            enctype:"multipart/form-data",
//            url:"/QnA/write",
//            data:formData,
//            processData:false,
//            contentType:false,
//            cache:false,
//            beforeSend:function(xhr){
//                xhr.setRequestHeader("AJAX", "true");
//            },
//            success:function(response){
//                if(response.code == 0){
//                alert("문의가 정상적으로 등록 되었습니다.");
//                location.href="/QnA";
//                }
//            }
//            else if(response.code == 400){
//                alert("파라미터 값이 올바르지 않습니다.");
//                $("#QnA_save_button").prop("disabled", false);
//            }
//            else{
//                alert("게시물 등록 중 오류가 발생하였습니다.");
//                $("#QnA_save_button").prop("disabled", false);
//            }
//        });

//        document.QnAForm.qnaNumber.value = "";
//        document.QnAForm.action = "/QnA";
//        document.QnAForm.submit();
//    });

    $("#QnA_list_button").on("click", function () {
        document.QnAForm.action = "/QnA";
        document.QnAForm.submit();
    });

});

