"use strict"


function addEvent() {
    document.querySelectorAll('.oneFlower').forEach(data => {
        data.querySelector('.flowerBuyDiv').addEventListener('click', (e) => {
            Swal.fire({
                title: `${data.querySelector('.flowerNameDiv').textContent} 픽업 페이지로 이동하시겠습니까?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '이동'
            }).then((result) => {
                if (result.isConfirmed) {
                    document.querySelector('#flowerName').value = data.querySelector('.flowerNameDiv').textContent;
                    document.querySelector('#postPickForm').submit();
                }
            })
        })
    })
}

addEvent();