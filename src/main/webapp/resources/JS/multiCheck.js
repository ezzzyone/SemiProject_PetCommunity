var IMP = window.IMP; // 생략 가능
IMP.init("imp12326472"); // 예: imp00000000

const cartCount = document.querySelectorAll(".cartCount");

let totalPrice = document.getElementById("totalPrice");
const priceCount = document.getElementById("priceCount");
const itemPrice = document.getElementById("itemPrice");
let ipv = "";
const itemName = document.getElementById("itemName");
let inv = "";
const itemNum2 = document.getElementById("itemNum");
let itn = "";
const itemCatg = document.getElementById("itemCatg");
let itg = "";
const buyer_email = document.getElementById("buyer_email");
let bev = "";
const buyer_name = document.getElementById("buyer_name");
let bnv = "";
const buyer_tel = document.getElementById("buyer_tel");
let btv = "";
const userId = document.getElementById("userId");
let uiv = "";
const revStartDate = document.getElementById("revStartDate");
let rsv ="";
const revEndDate = document.getElementById("revEndDate");
let rev = "";
const adultsCount = document.getElementById("adultsCount");
let ac = "";
const dogCount = document.getElementById("dogCount");
let dc = "";
const rvFrm = document.getElementById("rvFrm");
let date = new Date();
let merchant_uid = date.getTime();
const rvBtnFrm = document.getElementById("rvBtnFrm");
let msg = "";

let itemNumArr = [];
let userIdArr = [];
// itemNumArr.push(); 

const btnbtn = document.getElementById("btnbtn")

btnbtn.addEventListener("click", function(){
  for(let i=0; i<cartCount.length; i++){
    let item_num = cartCount.getAttribute("data-item-num");
    let user_Id = cartCount.getAttribute("data-user-id");
    itemNumArr.push(item_num); 
    userIdArr.push(user_Id); 
    console.log(itemNumArr[i]);
    console.log(userIdArr[i]);   
    }    
    });

    cartCount.forEach( function(c){
      
    });



// const btnShopCartFalse = document.getElementById("btnShopCartFalse");
// if(btnShopCartFalse == null){
//   const btnShopCartAdd = document.getElementById("btnShopCartAdd");
//   if(btnShopCartAdd == null){
//     const btnShopCartDelete = document.getElementById("btnShopCartDelete");
//     btnShopCartDelete.addEventListener("click",function(){
//       let result = window.confirm("이미 장바구니에 담긴 상품입니다. \n 장바구니에서 빼고 다시 넣겠습니까?");
//       if(!result){
//           return;
//       }
//       else{ 
//           let itemNum = btnShopCartDelete.getAttribute("data-item-num");
//           const xHttp = new XMLHttpRequest();
//           xHttp.open("POST","../sell/shopcartdelete");
//           xHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//           xHttp.send("itemNum="+itemNum);
//           xHttp.onreadystatechange = function(){
//               if(xHttp.readyState == 4 && xHttp.status == 200){
//                   let result = xHttp.responseText.trim();
//                   if(result == 1){
//                       alert("장바구니에서 빼기 성공");
//                       return window.location.href='/sell/list?itemCatg='+itemCatg.value;
//                   }
//                   else{
//                       alert("장바구니에서 빼기 실패");
//                       return;
//                   }
//               }
//           }
//       }
//     });
//   }
//   else{
//     btnShopCartAdd.addEventListener("click",function(){
//       let tpv = totalPrice.value;
//       if(tpv<=0){
//           alert("예상 금액 확인을 먼저 체크하신후 \n 이용해주세요.");
//       }
//       else if(tpv>0){
//           let itemNum = btnShopCartAdd.getAttribute("data-item-num");
//           let itemPrice = tpv;
//           const xHttp = new XMLHttpRequest();
//           xHttp.open("POST","./shopcartadd");
//           xHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//           xHttp.send("itemNum="+itemNum+"&itemPrice="+itemPrice);
//           xHttp.onreadystatechange = function(){
//               if(xHttp.readyState == 4 && xHttp.status == 200){
//                   let result = xHttp.responseText.trim();
//                   if(result == 1){
//                       alert("장바구니 담기 성공");
//                       return window.location.href='/sell/list?itemCatg='+itemCatg.getAttribute("value");
//                   }
//                   else{
//                       alert("장바구니에 담기 실패");
//                       return;
//                   }
//               }
//           }
//       }
//     });
//   }
// }
// else{
//   btnShopCartFalse.addEventListener("click",function(){
//     let result = window.confirm("장바구니 담기는 로그인후 사용가능합니다. \n 로그인 화면으로 가시겠습니까?");
//     if(!result){
//         return;
//     }
//     else{
//         return window.location.href='/member/login';
//     }
//   })
// }

// const coupon = document.getElementById("coupon");
// let arr = [];
// let rp = "";
// let couponNum = "";
// let method = "";
// let cpn = "";


// revStartDate.addEventListener("change", function(){
//   totalPrice.value="0";
// });

// revEndDate.addEventListener("change", function(){
//   totalPrice.value="0";
// });


// adultsCount.addEventListener("change", function(){
//   totalPrice.value="0";
// });

// dogCount.addEventListener("change", function(){
//   totalPrice.value="0";
// });


//           // ------ 쿠폰 계산
//           if(coupon.value != ""){
//             arr = coupon.value.split("|");

//             rp = arr[0];
//             couponNum = arr[1];
//             method = arr[2];
            

//             rp = Number(rp);

//             if(method == '0'){
//               totalPrice.value = totalPrice.value*(100-rp)/ 100;
//               tpv = totalPrice.value; 
//             }else{
//               totalPrice.value = totalPrice.value - rp;
//               tpv = totalPrice.value;
//             }
            
//             coupon.value = couponNum;
//             cpn = couponNum;
//           }
//           //----------------------
          


// //==================================================================결제창 실행
// rvBtnFrm.addEventListener("click", function(){
//     let dateResult = false;
//     itn = itemNum2.value;
//     itg = itemCatg.value;
//     rsv = revStartDate.value;
//     rev = revEndDate.value;
//     ac = adultsCount.value;
//     dc = dogCount.value;
//     tpv = totalPrice.value;
//     bev = buyer_email.value;
//     bnv = buyer_name.value;
//     btv = buyer_tel.value;
//     uiv = userId.value;
//     inv = itemName.value;
//     cpn = couponNum;

//     let itemNumArr = [];
//     itemNumArr.push(); 
    
//     btnbtn.addEventListener("click", function(){
//         let item_num = c.getAttribute("data-item-num");
//         let item_name = c.getAttribute("data-item-name");
//         console.log(item_num);
//         console.log(item_name);
//         for(let i=0; i<cartCount.length; i++){
//           console.log(i);
//         }
//       })
//     });

//     console.log(tpv);
//     console.log(uiv);
  
//     if(tpv<=0){
//     alert("합계 금액 오류로 결제 진행이 어렵습니다")
//     return;
//     } else{
//       dateResult = true;
//     }
  
//   if(dateResult){
//     requestPay();
//   } 


// //=====================================================================결제 api
//   function requestPay() {
    
//     // IMP.request_pay(param, callback) 결제창 호출
//     IMP.request_pay({ // param
//         pg: "html5_inicis",
//         pay_method: "card",
//         merchant_uid: merchant_uid,
//         name: inv,
//         amount: tpv,//tpv
//         buyer_email: bev,
//         buyer_name: bnv,
//         buyer_tel: btv,
//         notice_url : 'http://localhost/member/purchaseList'
//     }, function (rsp) { // callback
//         // 결제검증
//         if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
//           // jQuery로 HTTP 요청
//           $.ajax({
//               url: "./cartPayments", // 예: https://www.myservice.com/payments/complete
//               type: "POST",
//               traditional : true,
//               data: {
//                   'imp_uid': rsp.imp_uid,
//                   'merchant_uid': rsp.merchant_uid,
//                   'amount': tpv,
//                   'itemNum': itn,
//                   'userId': uiv,
//                   'couponNum' : cpn
//               },
//               error : function(xhr,status,error){
//                 console.log(xhr.responseText);
//                 console.log(status);
//                 console.log(error); 
//                 let data = xhr.responseText;
//                 console.log(data);
//               },
//               success : function(paymentResult){
//                 console.log(paymentResult);
//                 if(paymentResult=="paid") {
//                   alert("결제에 성공하였습니다!")
//                   window.location.href = 'http://localhost/member/purchaseList';
//               } else{
//                 alert("결제에 실패하였습니다. 결제 실패 사유: " + paymentResult);
//                 location.reload();
//               }
//               }})
//         } else {
//           alert("결제에 실패하였습니다. 에러 내용: " +  rsp.error_msg);
//         }
//       })
//   };


