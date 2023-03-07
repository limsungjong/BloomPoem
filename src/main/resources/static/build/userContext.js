"use strict";
let userContext = {};
{
    window.addEventListener("DOMContentLoaded", () => {
        var myHeaders = new Headers();
        const coo = document.cookie.split("=");
        if (coo[0] == "") {
            return;
        }
        console.log(coo);
        myHeaders.append(`${coo[0]}`, `Bearer ${coo[1]}`);
        var requestOptions = {
            method: "POST",
            headers: myHeaders,
            redirect: "follow",
        };
        fetch("http://localhost:9000/api/v1/sign/sign", requestOptions)
            .then((response) => response.text())
            .then((result) => {
                userContext = {jwt: coo[1]};
            })
            .catch((error) => console.log("error", error));
    });
}
// const userBtn = document.querySelector(".user") as HTMLButtonElement;
// userBtn.addEventListener("click", async (ev) => {
//   try {
//     const myHeaders = new Headers();
//     myHeaders.append("Content-Type", "application/json");
//     // myHeaders.append("Authorization")
//     const requestOptions: RequestInit = {
//       method: "POST",
//       headers: myHeaders,
//       redirect: "follow",
//     };
//     userContext = fetch(
//       "http://localhost:9000/api/v1/log_in_check",
//       requestOptions
//     );
//   } catch (error) {}
//   const user = sessionStorage.getItem("Authorization");
//   console.log(user);
//   console.log(user);
//   console.log(user);
// });
