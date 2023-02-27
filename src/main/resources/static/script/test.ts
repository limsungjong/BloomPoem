const aaa: HTMLImageElement = document.querySelector(
  ".img"
) as HTMLImageElement;
const xxx: HTMLButtonElement = document.querySelector(
  ".xxx"
) as HTMLButtonElement;

let url = "http://localhost:9000";
xxx.addEventListener("click", async (e) => {
  // fetch("http://localhost:9000/test/list")
  //   .then((data) => console.log(data))
  //   .catch((err) => console.log(err));

  let response = await fetch(`${url}/test/list`);

  if (response.ok) {
    // HTTP 상태 코드가 200~299일 경우
    // 응답 몬문을 받습니다(관련 메서드는 아래에서 설명).
    let json: Array<string> = await response.json();
    const e = `<img class="img" src="${url}/image/${json[0]}" />`;
    const qqq = document.createElement("div");
    qqq.innerHTML = e;
    document.querySelector("body")?.append(qqq);
  } else {
    alert("HTTP-Error: " + response.status);
  }
});
