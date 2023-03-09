"use strict";

let userContext = {};
{
    window.addEventListener("DOMContentLoaded", () => {
        let myHeaders = new Headers();
        const coo = document.cookie.split("=");
        if (coo[0] == "") {
            return;
        }
        myHeaders.append(`Cookie`, `${coo[0]}=${coo[1]}`);
        let requestOptions = {
            method: "POST",
            headers: myHeaders,
            redirect: "follow",
        };
        fetch("http://localhost:9000/api/v1/user/get_user", requestOptions)
            .then((response) => response.json())
            .then((result) => {
                console.log(result);
                if (result.status == 500) {
                    alert("접속 시간 초과로 인해 로그 아웃 되었습니다.");
                    document.cookie = "Authorization" + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
                }
                userContext = result;
                console.log(result.status)
            })
            .catch((error) => console.log("error", error));
    });
}

{
    // document.querySelector('#logOutBtn').addEventListener('click', (e) => {
    //     const coo = document.cookie.split("=");
    //     if (coo[0] == "") {
    //         return;
    //     }
    //     console.log(coo);
    //     let myHeaders = new Headers();
    //     myHeaders.append(`Cookie`, `${coo[0]}=${coo[1]}`);
    //
    //     let requestOptions = {
    //         method: 'POST',
    //         headers: myHeaders,
    //         redirect: 'follow'
    //     };
    //
    //     fetch("http://localhost:9000/api/v1/sign/sign_out", requestOptions)
    //         .then(response => response.text())
    //         .then(result => {
    //             console.log(result);
    //             location.href = "http://localhost:9000/"
    //         })
    //         .catch(error => console.log('error', error));
    // })
}
