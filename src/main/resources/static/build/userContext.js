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
                    return;
                }
                {
                    const header = document.querySelector('header');
                    {
                        const navBox = document.querySelector("nav");
                        navBox.setAttribute("class", "userBox");
                        navBox.innerHTML = ``;
                        if (!result.userEmail) {
                            const box = document.createElement("div");
                            const signInBox = `
                                <a href="http://localhost:9000/sign/sign_in">
                                <span class="signin">SignIn
                                    </span>
                                </a>
                                <a href="http://localhost:9000/sign/sign_up">
                                <span class="signup">SignUp
                                    </span>
                                </a>`;
                            navBox.innerHTML = signInBox;
                        } else {
                            navBox.innerHTML =
                                `<span id="logOutBtn">Sign Out</span>`;

                            navBox.querySelector('#logOutBtn')
                                .addEventListener('click', (e) => {
                                    document.cookie = "Authorization" + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
                                    let requestOptions = {
                                        method: 'POST',
                                        redirect: 'follow'
                                    };
                                    fetch("http://localhost:9000/api/v1/sign/sign_out", requestOptions)
                                        .then(response => {
                                            history.back();
                                        })
                                        .catch(error => console.log('error', error));
                                });
                        }
                    }
                }
            })
            .catch((error) => console.log("error", error));
    });
}