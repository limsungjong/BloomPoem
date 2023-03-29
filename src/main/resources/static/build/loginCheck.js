"use strict"

{
    window.addEventListener("DOMContentLoaded", () => {
        let myHeaders = new Headers();
        const coo = document.cookie.split("=");
        if (coo[0] == "") {
            return;
        }
        console.log(coo);
        myHeaders.append(`Cookie`, `${coo[0]}=${coo[1]}`);
        let requestOptions = {
            method: "POST",
            headers: myHeaders,
            redirect: "follow",
        };
        fetch("http://localhost:9000/api/v1/user/get_user", requestOptions)
            .then((response) => response.json())
            .then((result) => {
                Swal.fire({
                    icon: 'warning',
                    text: '로그인 되어 있는 사용자입니다.',
                    confirmButtonText:'확인'
                    })
                location.href="http://localhost:9000/"
            })
            .catch((error) => console.log("error", error));
    });
}
