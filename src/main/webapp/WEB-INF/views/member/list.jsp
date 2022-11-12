<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>list page</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- jQuery -->
  <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
  <!-- iamport.payment.js -->
  <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>

	<style type="text/css">
    .ag{
      background-color:cornsilk;
      overflow: scroll;
      width: 600px;
      height: 100px;
    }

    .warning{
      color: red;
    }
    #po{
      font-size: 10pt;
      color: darkgray;
    }


    #imgDiv{
      display: flex;
    }

    .newDiv{
      justify-content: center;
      align-items:center;
      position: relative;
      width: 80px;
	  	height: 80px;
      margin-right: 5px;
    }
    .thumnail{
		width: 80px;
		height: 80px;

		} 
    .imgDeleteBtn {
      position: absolute;
      top: 0px;
      right: 0px;
    }

    #inputDiv{
      display: none;
    }

    h5 {
      display: none;
    }

    .blocked{
      filter: brightness(20%); 
    }

	</style>
  
</head>
<body>
<!-- header start -->
<c:import url="/WEB-INF/views/template/header.jsp"></c:import>
<!-- header end -->
    <section id="chefs" class="chefs section-bg">
      <div class="container" data-aos="fade-up">

        <div class="section-header">
          <p>${what} <span>page</span></p>
        </div>

        <div class="row gy-4">

          <c:choose>

            <c:when test="${what eq 'memlist'}">


                    <form class="d-flex" action="find" method="get" id="sfrm">
                      <div>
                        <select name="roleNum" class="form-control" id="whatrn" value="${param.roleNum}">
                          <option value="" selected>-- 상품 카테고리 선택 --</option>
                          <option value="1">사업자</option>
                          <option value="2">개인회원</option>
                          <option value="0">관리자</option>
                        </select>
                      </div>
                      <input class="form-control me-2" type="text" name="search" placeholder="검색어 입력" aria-label="Search" value="${param.search}">
                      <button class="btn btn-outline-success" id="sbtn" type="button">Search</button>
                    </form>



              <c:forEach items="${list}" var="dto">
                <div class="col-lg-4 col-md-6 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="100">
                  <div class="chef-member">
                    <div class="member-img">

                      <c:if test="${empty dto.memberFileDTO.fileName}">
                          <img src="/resources/images/seller.jpg" class="img-fluid blocked" alt="">
                      </c:if>
                    <c:choose>
                      <c:when test="${dto.block == 1}">
                        <img src="/resources/upload/member/${dto.memberFileDTO.fileName}" class="img-fluid blocked" alt="">
                      </c:when>
                      <c:otherwise>
                        <img src="/resources/upload/member/${dto.memberFileDTO.fileName}" class="img-fluid" alt="">
                      </c:otherwise>
                    </c:choose>
                      
                 
                    </div>
                    <div class="member-info">
                      <h4>${dto.userId}</h4>
                      <span>${dto.roleDTO.roleName}</span>
                      <p>${dto.email}</p>
                      <div class="social">
                        <button class="blockbtn" type="button"><i class="bi bi-shield-slash-fill"></i></button> <!-- 멤버 차단-->
                        <button class="unblockbtn" type="button"><i class="bi bi-shield-check"></i></button>
                        <a href=""><i class="bi bi-instagram"></i></a>
                        <a href=""><i class="bi bi-linkedin"></i></a>
                      </div>
                    </div>
            
                  </div>
      
                </div>
              </c:forEach>
                 
            </c:when>

            <c:when test="${what eq 'followee'}">
          <c:forEach items="${list}" var="dto">
            <div class="col-lg-4 col-md-6 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="100">
              <div class="chef-member">
                <div class="member-img">
                  <img src="/resources/upload/member/${dto.memberFileDTO.fileName}" class="img-fluid" alt="">
                </div>
                <c:forEach items="${dto.followDTOs}" var="followDTOs">
                <div class="member-info">
                  <h4>${followDTOs.followee}</h4>
                  <span>guest</span>
                  <p>안녕하세요.</p>
                  <div class="social">
                    <button class="followeedel" type="button"><i class="bi bi-trash3"></i></button>
                    <a href=""><i class="bi bi-facebook"></i></a>
                    <a href=""><i class="bi bi-instagram"></i></a>
                    <a href=""><i class="bi bi-linkedin"></i></a>
                  </div>
                </div>
              </c:forEach>  
              </div>
  
            </div>
          </c:forEach>
             
        </c:when>


        <c:when test="${what eq 'follower'}">
          <c:forEach items="${list}" var="dto">
            <div class="col-lg-4 col-md-6 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="100">
              <div class="chef-member">
                <div class="member-img">
                  <img src="/resources/upload/member/${dto.memberFileDTO.fileName}" class="img-fluid" alt="">
                </div>
                <c:forEach items="${dto.followDTOs}" var="followDTOs">
                <div class="member-info">
                  <h4>${followDTOs.follower}</h4>
                  <span>guest</span>
                  <p>안녕하세요.</p>
                  <div class="social">
                    <button class="followerdel" type="button"><i class="bi bi-trash3"></i></button>
                    <a href=""><i class="bi bi-facebook"></i></a>
                    <a href=""><i class="bi bi-instagram"></i></a>
                    <a href=""><i class="bi bi-linkedin"></i></a>
                  </div>
                </div>
              </c:forEach>  
              </div>
  
            </div>
          </c:forEach>
             
        </c:when>

        <c:when test="${what eq 'pick'}">

                  <c:forEach items="${list.itemDTOs}" var="itemDTO">
             
                  <div class="col-lg-4 col-md-6 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="100">
                  <div class="chef-member">
                    <div class="member-img">
                      <img src="/resources/upload/sellfile/${itemDTO.fileDTOs['0'].fileName}" class="img-fluid" alt="">
                    </div>
                    <div class="member-info">
                      <h5>${itemDTO.itemNum}</h5>
                      <h4>${itemDTO.itemName}</h4>
                      <span>₩ ${itemDTO.itemPrice}</span>
                      <p>${itemDTO.itemContents}</p>
                    <div class="social">
                      <button class="pickdel" type="button"><i class="bi bi-trash3"></i></button>
                       <a href=""><i class="bi bi-facebook"></i></a>
                      <a href=""><i class="bi bi-instagram"></i></a>
                      <a href=""><i class="bi bi-linkedin"></i></a>
                    </div>
                  </div>
                  </div>
      
                </div>
              </c:forEach>  
              </c:when>

        <c:when test="${what eq 'cart'}">
          <input type="hidden" id="buyer_email" value="${sessionScope.member.email}">
          <input type="hidden" id="buyer_name" value="${sessionScope.member.userName}">
          <input type="hidden" id="buyer_tel" value="${sessionScope.member.phone}">
          <input type="hidden" id="userId" value="${sessionScope.member.userId}">
        	<table class="table">
				<thead>
					<tr>
						<th>번호</th><th>상품사진</th><th>상품명</th><th>가격</th><th>시작일자</th><th>종료일자</th><th>인원수</th><th>반려견수</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty list }">
					<c:forEach items="${list.itemDTOs}" var="itemDTO">
					<tr>
						<td class="itnValueA" data-item-num="${itemDTO.itemNum}" data-item-name="${itemDTO.itemName}">${itemDTO.itemNum }</td>
						<td><img style="border: 0px; outline: 0px; width: 70px; height: 70px;" src="/resources/upload/sellfile/${itemDTO.fileDTOs['0'].fileName}" class="img-fluid" alt=""></td>
          				<td>${itemDTO.itemName }</td>
          				<td>${itemDTO.shopCartDTOs[0].totalPrice }</td>
          				<td>${itemDTO.shopCartDTOs[0].revStartDay }</td>
          				<td>${itemDTO.shopCartDTOs[0].revEndDay }</td>
          				<td>${itemDTO.shopCartDTOs[0].adultsNum +1 }</td>
          				<td>${itemDTO.shopCartDTOs[0].dogNum +1}</td>
          				<td><button type="button" id="rvBtnFrm" class="btnCartDelete" data-item-num="${itemDTO.itemNum }" style="border: 1px solid gray; border-radius: 10px; width: 70px; height: 70px;"><img src="/resources/images/close.png" class="img-fluid" alt=""></button></td>
          			</tr>
        			</c:forEach>
        		</tbody>
          </c:if>
        	</table>
          <c:if test="${not empty list }">
            <div>
              총금액:<input type="text" readonly="readonly" value="${total.totalPrice}" id="totalPrice">
              <button type="button" class="btn btn-danger" id="multiCheckBtn">결제하기</button>
            </div>
          </c:if>
          <c:if test="${not empty list }">

            <div>
              <c:catch>
                <label for="contents" class="form-label">적용할 쿠폰</label>
                  <select class="form-select" id="coupon" name ="couponNum">
                    <option value="">선택</option>
                  <c:forEach items="${couponList}" var="coupon">
                    <c:choose>
                      <c:when test="${coupon.discountMethod eq '0'}">
                        <option value="${coupon.discountRate}|${coupon.couponNum}|${coupon.discountMethod}"> 쿠폰명 : ${coupon.couponName} ( 할인율 : ${coupon.discountRate} % )</option>
                      </c:when>
                      <c:otherwise>
                        <option value="${coupon.discountPrice}|${coupon.couponNum}|${coupon.discountMethod}"> 쿠폰명 : ${coupon.couponName} ( 할인금액 : ${coupon.discountPrice} 원 )</option>
                      </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
              </c:catch>
            </div>
          </c:if>
         
        </c:when>

        <c:when test="${what eq 'coupon'}">

      <div class="col-lg-4 col-md-6 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="100">
        <div class="chef-member">
          <div class="member-img">
            <img src="/resources/images/coupon.jpg" class="img-fluid" alt="">
            <div class="social">
              <a href=""><i class="bi bi-trash3"></i></a>
              <a href=""><i class="bi bi-facebook"></i></a>
              <a href=""><i class="bi bi-instagram"></i></a>
              <a href=""><i class="bi bi-linkedin"></i></a>
            </div>
          </div>
          <c:forEach items="${list}" var="dto">

              <div class="member-info">
          <h4>${dto.couponName}</h4>
          <span>${dto.expDate} 까지</span>
          <p>쿠폰상세내용</p>
        </div>
          </c:forEach>
        </div>

      </div>
          
        </c:when>
  
        <c:when test="${what eq 'Purchase List'}">
        <c:if test="${empty purchaseList}">
        	<div class="section-header">
         		<p>구매 상품이 없습니다</p>
        	</div>
        </c:if>
          <c:forEach items="${purchaseList}" var="check">
            <div class="col-lg-3 col-md-4 d-flex align-items-stretch justify-content-center" data-aos="fade-up" data-aos-delay="100">
              <div class="chef-member" style="width: 250px; height: 350px; margin-bottom: 15px;">
                <div class="member-img" style="width: 160%;">
                        <img src="/resources/upload/sellfile/${check.fileDTOs[0].fileName}" class="img-fluid" alt="">
                    </div>
                    <div class="member-info" style="width: 100%; height: 115px;">
                        <h4>${check.itemDTO.itemName}</h4>
                        <a href="./purchaseDetail?merchant_uid=${check.merchant_uid}">주문번호: ${check.merchant_uid}</a>
                        <span>결제 금액: ${check.amount}</span>
                        <p>${itemDTOs.itemContents}</p>
                    </div>
                </div>
            </div>
          </c:forEach>
        </c:when>

      <c:when test="${what eq 'Purchase Cancel List'}">
       <c:if test="${empty purchaseList}">
        	<div class="section-header">
         		<p>취소 상품이 없습니다</p>
        	</div>
        </c:if>
        <c:forEach items="${purchaseList}" var="check">
          <div class="col-lg-3 col-md-4 d-flex align-items-stretch justify-content-center" data-aos="fade-up" data-aos-delay="100">
            <div class="chef-member" style="width: 250px; height: 350px; margin-bottom: 15px;">
              <div class="member-img" style="width: 100%;">
                      <img src="/resources/upload/sellfile/${check.fileDTOs[0].fileName}" class="img-fluid" alt="">
                  </div>
                  <div class="member-info">
                      <h4>${check.itemDTO.itemName}</h4>
                      <a href="./purchaseDetail?merchant_uid=${check.merchant_uid}">주문번호: ${check.merchant_uid}</a>
                      <span>환불 금액: ${check.amount}</span>
                      <p>${itemDTOs.itemContents}</p>
                  </div>
              </div>
          </div>
        </c:forEach>
      </c:when>
      
      
      <c:when test="${what eq 'Seller List'}">
       <c:if test="${empty purchaseList}">
        	<div class="section-header">
         		<p>판매된 상품이 아직 없습니다</p>
         		<p>홍보 게시판에서 사업장을 홍보해보세요!</p>
        	</div>
        </c:if>
        <c:forEach items="${purchaseList}" var="check">
          <div class="col-lg-3 col-md-4 d-flex align-items-stretch justify-content-center" data-aos="fade-up" data-aos-delay="100">
            <div class="chef-member" style="width: 250px; height: 350px; margin-bottom: 15px;">
              <div class="member-img" style="width: 160%;">
                      <img src="/resources/upload/sellfile/${check.fileDTOs[0].fileName}" class="img-fluid" alt="">
                  </div>
                  <div class="member-info" style="width: 100%; height: 115px;">
                      <h4>${check.itemDTO.itemName}</h4>
                      <a href="./purchaseDetail?merchant_uid=${check.merchant_uid}">주문번호: ${check.merchant_uid}</a>
                      <span>결제 금액: ${check.amount}</span>
                      <p>${itemDTOs.itemContents}</p>
                  </div>
              </div>
          </div>
        </c:forEach>
      </c:when>
     
    <c:when test="${what eq 'Seller Cancel List'}">
     <c:if test="${empty purchaseList}">
        	<div class="section-header">
         		<p>구매 취소된 상품이 없습니다</p>
        	</div>
        </c:if>
      <c:forEach items="${purchaseList}" var="check">
        <div class="col-lg-3 col-md-4 d-flex align-items-stretch justify-content-center" data-aos="fade-up" data-aos-delay="100">
          <div class="chef-member" style="width: 250px; height: 350px; margin-bottom: 15px;">
            <div class="member-img" style="width: 100%;">
                    <img src="/resources/upload/sellfile/${check.fileDTOs[0].fileName}" class="img-fluid" alt="">
                </div>
                <div class="member-info">
                    <h4>${check.itemDTO.itemName}</h4>
                    <a href="./purchaseDetail?merchant_uid=${check.merchant_uid}">주문번호: ${check.merchant_uid}</a>
                    <span>환불 금액: ${check.amount}</span>
                    <p>${itemDTOs.itemContents}</p>
                </div>
            </div>
        </div>
      </c:forEach>
    </c:when>
     

            </c:choose>

        </div>

      </div>
    </section>


  <!-- footer start -->
  <c:import url="/WEB-INF/views/template/footer.jsp"></c:import>
  <!-- footer end -->
  
  <script>
    let abc = "${total.totalPrice}";
  </script>
  <script src="/resources/JS/follow.js"></script>
  <script src="/resources/JS/multiCheck .js"></script>

<!-- <script>
  function getParameter(name) {
      name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
      var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
          results = regex.exec(location.search);
      return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));

      let what = getParameter(what);
  }
  </script> -->
</body>
</html>