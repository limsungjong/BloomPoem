"use strict";
// // cherry blossom action
// {
//   const canvas = document.querySelector("canvas") as HTMLCanvasElement;
var _a;
//   canvas.width = window.innerWidth;
//   canvas.height = window.innerHeight;
//   const ctx = canvas.getContext("2d") as CanvasRenderingContext2D;
//   const TOTAL = 80;
//   const petalArray: Array<Petal> = [];
//   const petalImage = new Image();
//   petalImage.src = "./image/petal.png";
//   petalImage.onload = () => {
//     for (let i = 0; i < TOTAL; i++) {
//       petalArray.push(new Petal());
//     }
//     // render();
//   };
//   const render = () => {
//     ctx.clearRect(0, 0, canvas.width, canvas.height); // 지우개 역할
//     petalArray.forEach((petal) => {
//       petal.animate();
//     });
//     window.requestAnimationFrame(render); // 재귀함수 1초에 60번 가량 실행
//   };
//   window.addEventListener("resize", () => {
//     canvas.width = window.innerWidth;
//     canvas.height = window.innerHeight;
//   });
//   class Petal {
//     x: number;
//     y: number;
//     w: number;
//     h: number;
//     xSpeed: number;
//     ySpeed: number;
//     opacity: number;
//     flip: number;
//     flipSpeed: number;
//     constructor() {
//       this.x = Math.random() * canvas.width;
//       this.y = Math.random() * canvas.height * 2 - canvas.height;
//       this.w = 30 + Math.random() * 15;
//       this.h = 20 + Math.random() * 10;
//       this.opacity = this.w / 45;
//       this.xSpeed = 2 + Math.random();
//       this.ySpeed = 1 + Math.random();
//       this.flip = Math.random();
//       this.flipSpeed = Math.random() * 0.03;
//     }
//     draw() {
//       if (this.y > canvas.height || this.x > canvas.width) {
//         this.x = -petalImage.width;
//         this.y = Math.random() * canvas.height * 2 - canvas.height;
//         this.xSpeed = 2 + Math.random();
//         this.ySpeed = 1 + Math.random();
//       }
//       ctx.globalAlpha = this.opacity;
//       ctx.drawImage(
//         petalImage,
//         this.x,
//         this.y,
//         this.w * ((0.66 + Math.abs(Math.cos(this.flip))) / 3),
//         this.h * ((0.8 + Math.abs(Math.sin(this.flip))) / 2)
//       );
//     }
//     animate() {
//       this.x += this.xSpeed;
//       this.y += this.ySpeed;
//       this.draw();
//       this.flip += this.flipSpeed;
//     }
//   }
// }
// nav action
{
    const navBarActionBtn = document.querySelector("#navActionBtn");
    navBarActionBtn === null || navBarActionBtn === void 0 ? void 0 : navBarActionBtn.addEventListener("click", (e) => {
        const navBarAction = document.querySelector("nav");
        const te = navBarAction.style;
        if (!te.display || te.display == "none") {
            te.display = "block";
        }
        else {
            te.display = "none";
        }
        const icon = document
            .querySelector(".js-menu-button")
            .querySelector(".menu-icon");
        if (icon.getAttribute("class") == "menu-icon") {
            icon === null || icon === void 0 ? void 0 : icon.setAttribute("class", "menu-icon is-active");
        }
        else {
            icon === null || icon === void 0 ? void 0 : icon.setAttribute("class", "menu-icon");
        }
    });
    window.addEventListener("scroll", () => {
        if (scrollY > 400) {
        }
    });
}
// card action
{
    class CardFlipOnScroll {
        constructor(wrapper, sticky) {
            this.wrapper = wrapper;
            this.sticky = sticky;
            this.cards = sticky.querySelectorAll(".card");
            this.length = this.cards.length;
            this.start = 0;
            this.end = 0;
            this.step = 0;
        }
        init() {
            this.start = this.wrapper.offsetTop - 100;
            this.end =
                this.wrapper.offsetTop + this.wrapper.offsetHeight - innerHeight * 1.2;
            this.step = (this.end - this.start) / (this.length * 2);
        }
        animate() {
            this.cards.forEach((card, i) => {
                const s = this.start + this.step * i;
                const e = s + this.step * (this.length + 1);
                if (scrollY <= s) {
                    card.style.transform = `
                perspective(100vw)
                translateX(100vw) 
                rotateY(180deg)
              `;
                }
                else if (scrollY > s && scrollY <= e - this.step) {
                    card.style.transform = `
                perspective(100vw)
                translateX(${100 + ((scrollY - s) / (e - s)) * -100}vw)
                rotateY(180deg)
              `;
                }
                else if (scrollY > e - this.step && scrollY <= e) {
                    card.style.transform = `
                perspective(100vw)
                translateX(${100 + ((scrollY - s) / (e - s)) * -100}vw)
                rotateY(${180 + (-(scrollY - (e - this.step)) / this.step) * 180}deg)
              `;
                }
                else if (scrollY > e) {
                    card.style.transform = `
                perspective(100vw)
                translateX(0vw) 
                rotateY(0deg)
              `;
                }
            });
        }
        preLoad(img) {
            // background-repeat: no-repeat;
            // background-size: cover;
            // background-position: center;
            for (let i = 0; i < img.length; i++) {
                this.cards[i].style.backgroundImage = `url(${img[i]})`;
                this.cards[i].style.backgroundRepeat = "no-repeat";
                this.cards[i].style.backgroundSize = "cover";
                this.cards[i].style.backgroundPosition = "center";
            }
        }
    }
    const mainContent1 = document.querySelector(".main-content-1");
    const sticky = document.querySelector(".sticky");
    const cardFlipOnScroll = new CardFlipOnScroll(mainContent1, sticky);
    cardFlipOnScroll.init();
    cardFlipOnScroll.preLoad([
        "./image/pick.jpg",
        "./image/Shop.jpg",
        "./image/recommend.jpg",
        "./image/logo.png",
    ]);
    window.addEventListener("scroll", () => {
        cardFlipOnScroll.animate();
    });
    window.addEventListener("resize", () => {
        cardFlipOnScroll.init();
    });
}
// button wave action
{
    const onClick = (e, btn) => {
        if (btn) {
            const { x, y, width, height } = btn.getBoundingClientRect();
            const radius = Math.sqrt(width * width + height * height);
            btn.style.setProperty("--diameter", radius * 2 + "px");
            const { clientX, clientY } = e;
            const left = ((clientX - x - radius) / width) * 100 + "%";
            const top = ((clientY - y - radius) / height) * 100 + "%";
            btn.style.setProperty("--left", left);
            btn.style.setProperty("--top", top);
            btn.style.setProperty("--a", "");
            setTimeout(() => {
                btn.style.setProperty("--a", "ripple-effect 500ms linear");
            });
        }
    };
    const waveButtons = document.querySelectorAll(".wave-btn");
    waveButtons.forEach((btnElement) => {
        btnElement.addEventListener("click", (e) => onClick(e, btnElement));
    });
}
// folder Scroll
{
    class FolderScroll {
        constructor(wrapper, sticky2) {
            this.wrapper = wrapper;
            this.sticky = sticky2;
            this.children = this.sticky.querySelectorAll(".section");
            this.length = this.children.length;
            this.headerVh = 6;
            this.contentVh = 100 - this.headerVh * this.length;
            this.start = 0;
            this.end = 0;
        }
        init() {
            this.start = this.wrapper.offsetTop + 100;
            this.end =
                this.wrapper.offsetTop + this.wrapper.offsetHeight - innerHeight - 100;
            this.children.forEach((child, i) => {
                child.style.bottom = -100 + this.headerVh * (this.length - i) + "vh";
                child.querySelector(".title").style.height =
                    this.headerVh + "vh";
                child.querySelector(".contentWrap").style.height = this.contentVh + "vh";
            });
        }
        animate() {
            this.children.forEach((child, i) => {
                const unit = (this.end - this.start) / this.length;
                const s = this.start + unit * i + 100;
                const e = this.start + unit * (i + 1);
                if (scrollY <= s) {
                    child.style.transform = `translate3d(0, 0, 0)`;
                }
                else if (scrollY >= e) {
                    child.style.transform = `translate3d(0, ${-this.contentVh}%, 0)`;
                }
                else {
                    child.style.transform = `translate3d(0, ${((scrollY - s) / (unit - 100)) * -this.contentVh}%, 0)`;
                }
            });
        }
        preLoad(img) {
            // background-repeat: no-repeat;
            // background-size: cover;
            // background-position: center;
            const cards = document.querySelectorAll(".content");
            for (let i = 0; i < img.length; i++) {
                cards[i].style.backgroundImage = `url(${img[i]})`;
                cards[i].style.backgroundRepeat = "no-repeat";
                cards[i].style.backgroundSize = "contain";
                cards[i].style.backgroundPosition = "center";
            }
        }
    }
    const mainCon = document.querySelector(".main-content-2");
    const stickyCon = document.querySelector(".sticky2");
    const folderScroll = new FolderScroll(mainCon, stickyCon);
    folderScroll.init();
    window.addEventListener("scroll", () => {
        folderScroll.animate();
    });
    window.addEventListener("resize", () => {
        folderScroll.init();
    });
    folderScroll.preLoad([
        "./image/pickUp.png",
        "./image/flowerDelivery.jpg",
        "./image/flowerRecommend.png",
        "./image/flowerCommunity.jpg",
    ]);
}
(_a = document.querySelector(".moreBtn")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
    window.scrollBy({ top: window.innerHeight });
});

    // search창 이벤트
{
    const inputText = document.querySelector(".searchTerm");
    const inputBtn = document.querySelector(".searchButton");

    inputText.addEventListener('focus', () => {
        inputBtn.addEventListener("click", (e) => {
            postPickUpMap(inputText.value);
        });
        inputText.addEventListener("keyup", (e) => {
            if (e.keyCode === 13) postPickUpMap(inputText.value);
        });
    })

    function postPickUpMap(query) {
        document.querySelector("#target").value = query;
        document.querySelector('#postPickForm').submit();
    }
}