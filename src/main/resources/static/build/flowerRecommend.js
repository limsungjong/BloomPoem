"use strict"


document.querySelectorAll('.oneFlower').forEach(data => {
    data.querySelector('.flowerBuyDiv').addEventListener('click', (e) => {
        document.querySelector('#flowerName').value = data.querySelector('.flowerNameDiv').textContent;
        document.querySelector('#postPickForm').submit();
    })
})
