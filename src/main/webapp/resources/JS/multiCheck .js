
var IMP = window.IMP; // 생략 가능
IMP.init("imp12326472"); // 예: imp00000000

var sellItemDTO = '${sellItemDTO}';

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
const multiCheckBtn = document.getElementById("multiCheckBtn");
const itnValueA = document.getElementsByClassName("itnValueA");



const cartCheck = document.getElementsByClassName("cartCheck");
for(let i=0; i<cartCheck.length; i++){
  cartCheck[i].addEventListener("click",function(){
    console.log(cartCheck[i].checked);
  })
}




const btnCartDelete = document.getElementsByClassName("btnCartDelete")
for(let i=0; i<btnCartDelete.length; i++){

    btnCartDelete[i].addEventListener("click",function(){
          let result = window.confirm("장바구니에서 빼시겠습니까?");
          if(!result){
              return;
          }
          else{ 
              let itemNum = btnCartDelete[i].getAttribute("data-item-num");
              const xHttp = new XMLHttpRequest();
              xHttp.open("POST","../../../sell/shopcartdelete");
              xHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
              xHttp.send("itemNum="+itemNum);
              xHttp.onreadystatechange = function(){
                  if(xHttp.readyState == 4 && xHttp.status == 200){
                      let result = xHttp.responseText.trim();
                      if(result == 1){
                          alert("장바구니에서 빼기 성공");
                          return window.location.href="/member/cart";
                      }
                      else{
                          alert("장바구니에서 빼기 실패");
                          return;
                      }
                  }
              }
          }
    });
}



const coupon = document.getElementById("coupon");
let arr = [];
let rp = "";
let couponNum = "";
let method = "";
let cpn = "";





let couponi = 0;
coupon.addEventListener("change", function(){     
  // ------ 쿠폰 계산
         if(coupon.value != ""){
                couponi++;
              if(couponi > 1){
                  return;
              }
            arr = coupon.value.split("|");

            rp = arr[0];
            couponNum = arr[1];
            method = arr[2];
            
           
            rp = Number(rp);

            if(method == '0'){
              totalPrice.value = totalPrice.value*(100-rp)/ 100;
              tpv = totalPrice.value; 
              // coupontotal.value=totalPrice.value;
              // abc = totalPrice.value;
            }else{
              totalPrice.value = totalPrice.value - rp;
              tpv = totalPrice.value;
              // coupontotal.value = totalPrice.value;
              // abc = totalPrice.value;
            }
            
            coupon.value = couponNum;
            cpn = couponNum;
          }else{
            totalPrice.value = abc;
            couponi--;
          }
})

//==================================================================결제창 실행
multiCheckBtn.addEventListener("click", function (){
  let dateResult = false;
  let itnArr = [];
    
  for(let i=0; i<itnValueA.length; i++){
    itnArr.push(itnValueA[i].getAttribute("data-item-num"));
  }

    inv = itnValueA[0].getAttribute("data-item-name")+"외";
    tpv = totalPrice.value;
    bev = buyer_email.value;
    bnv = buyer_name.value;
    btv = buyer_tel.value;
    uiv = userId.value;
    cpn = couponNum;
  
    if(tpv<=0){
    alert("예상 결제 금액을 다시 확인해주세요")
    return;
    } else{
      dateResult = true;
    }
  
  if(dateResult){
    requestPay();
  } 
});

//=====================================================================결제 api
  function requestPay() {
   
    // IMP.request_pay(param, callback) 결제창 호출
    IMP.request_pay({ // param
        pg: "html5_inicis",
        pay_method: "card",
        merchant_uid: merchant_uid,
        name: inv,
        amount: tpv,//tpv
        buyer_email: bev,
        buyer_name: bnv,
        buyer_tel: btv,
        notice_url : 'http://localhost/member/purchaseList'
    }, function (rsp) { // callback
        // 결제검증
        if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
          let itnArr = [];
    
          for(let i=0; i<itnValueA.length; i++){
            itnArr.push(itnValueA[i].getAttribute("data-item-num"));
          }
         
          // jQuery로 HTTP 요청
          $.ajax({
              url: "./cartPayments", // 예: https://www.myservice.com/payments/complete
              type: "POST",
              traditional: true,
              data: {
                  'imp_uid': rsp.imp_uid,
                  'merchant_uid': rsp.merchant_uid,
                  'amount': tpv,
                  'itemNum': itnArr,
                  'userId': uiv,
                  'couponNum' : cpn
              },
              error : function(xhr,status,error){
                console.log(xhr.responseText);
                console.log(status);
                console.log(error);
                let data = xhr.responseText;
                console.log(data);
              },
              success : function(paymentResult){
                console.log(paymentResult);
                if(paymentResult=="paid") {
                  alert("결제에 성공하였습니다!")
                  $.ajax({
                    url: "../sell/allCartDelete",
                    type: "POST",
                    data: {
                      'userId': uiv
                    },
                    error : function(xhr,status,error){
                      console.log(xhr.responseText);
                      console.log(status);
                      console.log(error);
                      let data = xhr.responseText;
                      console.log(data);
                    },
                    success : function(result){
                      if(result == "1"){
                        window.location.href = 'http://localhost/member/purchaseList';
                      }
                    }
                  })
              } else{
                alert("결제에 실패하였습니다. 결제 실패 사유: " + paymentResult);
                location.reload();
              }
              }})
          // .done(function (paymentResult) {
          //   console.log(paymentResult);
          //   if(paymentResult=="paid") {
          //     alert("결제에 성공하였습니다!")
          //     window.location.href = 'http://localhost/member/purchaseList';
          //   }
        } else {
          alert("결제에 실패하였습니다. 에러 내용: " +  rsp.error_msg);
          // location.reload(); 
        }
      })
  };

