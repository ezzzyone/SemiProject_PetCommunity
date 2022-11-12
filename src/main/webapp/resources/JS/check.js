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

const needID = document.querySelector("#needID");
if(userId.value.length<=0){
  needID.addEventListener("click", function(){
    let result = window.confirm("금액 계산은 로그인 후 이용 가능합니다. \n로그인 화면으로 가시겠습니까?");
    if(!result){
        return;
    }
    else{
        return window.location.href='/member/login';
    }
  });
}

const btnShopCartFalse = document.getElementById("btnShopCartFalse");
if(btnShopCartFalse == null){
  const btnShopCartAdd = document.getElementById("btnShopCartAdd");
  if(btnShopCartAdd == null){
    const btnShopCartDelete = document.getElementById("btnShopCartDelete");
    btnShopCartDelete.addEventListener("click",function(){
      let result = window.confirm("이미 장바구니에 담긴 상품입니다. \n 장바구니에서 빼고 다시 넣겠습니까?");
      if(!result){
          return;
      }
      else{ 
          let itemNum = btnShopCartDelete.getAttribute("data-item-num");
          const xHttp = new XMLHttpRequest();
          xHttp.open("POST","../sell/shopcartdelete");
          xHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
          xHttp.send("itemNum="+itemNum);
          xHttp.onreadystatechange = function(){
              if(xHttp.readyState == 4 && xHttp.status == 200){
                  let result = xHttp.responseText.trim();
                  if(result == 1){
                      alert("장바구니에서 빼기 성공");
                      return window.location.href='/sell/list?itemCatg='+itemCatg.value;
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
  else{
    btnShopCartAdd.addEventListener("click",function(){
      let tpv = totalPrice.value;
      let rsv = revStartDate.value;
      let rev = revEndDate.value;
      let ac = adultsCount.value;
      let dc = dogCount.value;
      if(tpv<=0){
          alert("예상 금액 확인을 먼저 체크하신후 \n 이용해주세요.");
      }
      else if(tpv>0){
          let itemNum = btnShopCartAdd.getAttribute("data-item-num");
          let itemPrice = 0;
          if(rp == null){
            itemPrice = tpv;
          }
          else{
            tpv = totalPrice.value*100/(100-rp)
            itemPrice = tpv;
          }
          let revStartDay = rsv;
          let revEndDay = rev;
          let adultsNum = ac;
          let dogNUM = dc;
          const xHttp = new XMLHttpRequest();
          xHttp.open("POST","./shopcartadd");
          xHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
          xHttp.send("itemNum="+itemNum+"&totalPrice="+itemPrice+"&revStartDay="+revStartDay+"&revEndDay="+revEndDay+"&adultsNum="+adultsNum+"&dogNum="+dogNUM);
          xHttp.onreadystatechange = function(){
              if(xHttp.readyState == 4 && xHttp.status == 200){
                  let result = xHttp.responseText.trim();
                  if(result == 1){
                      alert("장바구니 담기 성공");
                      alert("장바구니에 담길때는 쿠폰이 적용 안된 금액으로 표시되므로 \n 장바구니에서 결제시 다시 쿠폰을 적용시켜주세요");
                      return window.location.href='/sell/list?itemCatg='+itemCatg.getAttribute("value");
                  }
                  else{
                      alert("장바구니에 담기 실패");
                      return;
                  }
              }
          }
      }
    });
  }
}
else{
  btnShopCartFalse.addEventListener("click",function(){
    let result = window.confirm("장바구니 담기는 로그인후 사용가능합니다. \n 로그인 화면으로 가시겠습니까?");
    if(!result){
        return;
    }
    else{
        return window.location.href='/member/login';
    }
  })
}

const coupon = document.getElementById("coupon");
let arr = [];
let rp = "";
let couponNum = "";
let method = "";
let cpn = "";


revStartDate.addEventListener("change", function(){
  totalPrice.value="0";
});

revEndDate.addEventListener("change", function(){
  totalPrice.value="0";
});


adultsCount.addEventListener("change", function(){
  totalPrice.value="0";
});

dogCount.addEventListener("change", function(){
  totalPrice.value="0";
});


//===================================================날짜 계산
if(userId.value.length>0){
      priceCount.addEventListener("click", function (){
        let dateResult = false;
        ipv = itemPrice.value;
        rsv = revStartDate.value;
        rev = revEndDate.value;
        ac = adultsCount.value;
        dc = dogCount.value;
        itg = itemCatg.value;
        itn = itemNum2.value;
        tpv = totalPrice.value;
        bev = buyer_email.value;
        bnv = buyer_name.value;
        btv = buyer_tel.value;
        uiv = userId.value;
        inv = itemName.value;
        console.log(itg);


        if(rsv.length<=0||rev.length<=0){
          alert("날짜를 입력하세요")
          return;
          } else{
            if(itg==2 && rev!=rsv){
            alert("원데이 클래스는 하루 단위로 예약이 가능합니다");
            return;
          } else{
              dateResult = true;
            }
          }
          

      if(ac.length<=0||dc.length<=0){
        alert("인원수 또는 반려견 수를 입력하세요")
        return;
      } else{
        dateResult = true;
      }

      if(dateResult){
          const redate = new Date(revEndDate.value);
          const rsdate = new Date(revStartDate.value);
          let redateC = redate.getTime();
          let rsdateC = rsdate.getTime();
          if(redateC<rsdateC){
            alert("날짜를 다시 입력하세요")
            return;
          } else{
            let tdate = "";
            if(redateC == rsdateC){
              tdate = 1;
            } else{
              tdate = (redateC - rsdateC) / (1000*60*60*24);
            }
            let priceC = tdate*ipv+ac*10000+dc*10000;
            totalPrice.value = priceC;
          }
          
      }
        
          // ------ 쿠폰 계산
          if(coupon.value != ""){
            arr = coupon.value.split("|");

            rp = arr[0];
            couponNum = arr[1];
            method = arr[2];
            

            rp = Number(rp);

            if(method == '0'){
              totalPrice.value = totalPrice.value*(100-rp)/ 100;
              tpv = totalPrice.value; 
            }else{
              totalPrice.value = totalPrice.value - rp;
              tpv = totalPrice.value;
            }
            
            coupon.value = couponNum;
            cpn = couponNum;
          }
          //----------------------
          

      })
};


//==================================================================결제창 실행
rvBtnFrm.addEventListener("click", function(){
    let dateResult = false;
    itn = itemNum2.value;
    itg = itemCatg.value;
    rsv = revStartDate.value;
    rev = revEndDate.value;
    ac = adultsCount.value;
    dc = dogCount.value;
    tpv = totalPrice.value;
    bev = buyer_email.value;
    bnv = buyer_name.value;
    btv = buyer_tel.value;
    uiv = userId.value;
    inv = itemName.value;
    cpn = couponNum;

    console.log(tpv);
    console.log(uiv);
  
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
        revStartDate: rsv,
        notice_url : 'http://localhost/member/purchaseList'
    }, function (rsp) { // callback
        // 결제검증
        if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
          // jQuery로 HTTP 요청
          $.ajax({
              url: "./payments", // 예: https://www.myservice.com/payments/complete
              type: "POST",
              data: {
                  'imp_uid': rsp.imp_uid,
                  'merchant_uid': rsp.merchant_uid,
                  'amount': tpv,
                  'revStartDate': rsv,
                  'itemNum': itn,
                  'revEndDate': rev,
                  'adultsCount': ac,
                  'dogCount': dc,
                  'userId': uiv,
                  'couponNum' : cpn
              },
              error : function(error){
                console.log(error);
              },
              success : function(paymentResult){
                console.log(paymentResult);
                if(paymentResult=="paid") {
                  alert("결제에 성공하였습니다!")
                  window.location.href = 'http://localhost/member/purchaseList';
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
