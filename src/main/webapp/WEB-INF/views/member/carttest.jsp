<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>Search</title>
  <meta content="" name="description">
  <meta content="" name="keywords">
  <!-- jQuery -->
  <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
  <!-- iamport.payment.js -->
  <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
</head>
<body>

  <div class="headerWrapper" style="position: relative;">
    <!-- header start -->
    <c:import url="/WEB-INF/views/template/header.jsp"></c:import>
    <!-- header end -->
</div>

<c:forEach items="${list.itemDTOs}" var="itemDTO">

          <div class="col-lg-4 col-md-6 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="100">
            <div class="chef-member">
              <div class="member-img">
                <img src="/resources/upload/sellfile/${itemDTO.fileDTOs['0'].fileName}" class="img-fluid" alt="">
              </div>
                <div class="member-info">
                  <h5 value = ${itemDTO.itemNum}>${itemDTO.itemNum}</h5>
                  <h4>${itemDTO.itemName}</h4>
                  <span>${itemDTO.shopCartDTOs[0].totalPrice}</span>
                  <p>${itemDTO.itemContents}</p>
                  <div class="social">
                    <button class="cartdel" type="button"><i class="bi bi-trash3"></i></button>
                    <button class="cartCount" type="button" data-item-num="${itemDTO.itemNum}" data-user-id="${itemDTO.userId}"><i class="bi bi-heart"></i></button>
                    <a href=""><i class="bi bi-instagram"></i></a>
                    <a href=""><i class="bi bi-linkedin"></i></a>
                  </div>
              </div>
       
            </div>

          </div>
        </c:forEach>

        <button id="btnbtn">계산</button>





<script src="/resources/JS/multiCheck.js"></script>

         <!-- footer start -->
 <section>
    <c:import url="/WEB-INF/views/template/footer.jsp"></c:import>
  </section>
  <!-- footer end -->
 
 
 
 
 </body>
 
 </html>