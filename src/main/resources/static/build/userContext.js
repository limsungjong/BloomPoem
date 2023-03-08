"use strict";
const logOut = document.createElement("div");
logOut.innerHTML = `<button id="logOutBtn">logOut</button>`;
document.querySelector('footer').append(logOut);
let userContext = {};
{
    window.addEventListener("DOMContentLoaded", () => {
        var myHeaders = new Headers();
        const coo = document.cookie.split("=");
        if (coo[0] == "") {
            return;
        }
        console.log(coo);
        myHeaders.append(`Cookie`, `${coo[0]}=${coo[1]}`);
        var requestOptions = {
            method: "POST",
            headers: myHeaders,
            redirect: "follow",
        };
        fetch("http://localhost:9000/api/v1/user/get_user", requestOptions)
            .then((response) => response.json())
            .then((result) => {
                userContext = result;
            })
            .catch((error) => console.log("error", error));
    });
}
{
    {
        const body = document.querySelector("body");
        {
            const box = document.createElement("header");
            const headerHtml = `
    <header class="header">
      <img src="/image/logo최종.png" alt="logo" class="logo" />
    </header>
    `;
        }
    }

    document.querySelector('#logOutBtn').addEventListener('click', (e) => {
        const coo = document.cookie.split("=");
        if (coo[0] == "") {
            return;
        }
        console.log(coo);
        let myHeaders = new Headers();
        myHeaders.append(`Cookie`, `${coo[0]}=${coo[1]}`);

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            redirect: 'follow'
        };

        fetch("http://localhost:9000/api/v1/sign/sign_out", requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result);
                location.href = "http://localhost:9000/"
            })
            .catch(error => console.log('error', error));
    })
}
