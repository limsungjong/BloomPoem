"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
const aaa = document.querySelector(".img");
const xxx = document.querySelector(".xxx");
let url = "http://localhost:9000";
xxx.addEventListener("click", (e) => __awaiter(void 0, void 0, void 0, function* () {
    // fetch("http://localhost:9000/test/list")
    //   .then((data) => console.log(data))
    //   .catch((err) => console.log(err));
    var _a;
    let response = yield fetch(`${url}/test/list`);
    if (response.ok) {
        // HTTP 상태 코드가 200~299일 경우
        // 응답 몬문을 받습니다(관련 메서드는 아래에서 설명).
        let json = yield response.json();
        const e = `<img class="img" src="${url}/image/${json[0]}" />`;
        const qqq = document.createElement("div");
        qqq.innerHTML = e;
        (_a = document.querySelector("body")) === null || _a === void 0 ? void 0 : _a.append(qqq);
    }
    else {
        alert("HTTP-Error: " + response.status);
    }
}));
