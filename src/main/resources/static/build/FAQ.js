const qList = document.querySelectorAll(".FAQ_toggle_Q");
const aList = document.querySelectorAll(".FAQ_toggle_A");
for (let index = 0; index < qList.length; index++) {
  const question = qList[index];
  console.log(question);

  const answer = aList[index];
  console.log(answer);

  $(question).click(function () {
    $(answer).toggle();
  });
}
console.log(qList);
console.log(aList);
