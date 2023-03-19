'use strict'

const pickUpOrderHsBtn = document.querySelector('.pickUpOrderHistory');
const pickUpOrderRvBtn = document.querySelector('.pickUpReviewManagement');
let showOrder;
pickUpOrderHsBtn.addEventListener('click', () => {
    const contentBox = new pickUpOrderHS();
    contentBox.fetchToGetOrderList();
})

pickUpOrderRvBtn.addEventListener('click', () => {

})

class pickUpOrderHS{
    orderList;
    constructor() {
    }
    fetchToGetOrderList() {
        let requestOptions = {
            method: 'POST',
            redirect: 'follow'
        };

        fetch("http://localhost:9000/myPage/get/reviewList", requestOptions)
            .then(response => response.json())
            .then(result => {
                console.log(result);
                this.orderList = result;
            })
            .catch(error => console.log('error', error));
    }

    sidePickUpBtnHandler() {

    }

    pickUpContentHandler() {

    }
}

class pickUpOrderRv{
    constructor() {
    }
}