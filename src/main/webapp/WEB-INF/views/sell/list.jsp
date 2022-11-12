<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  <!-- jQuery -->
  <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>

  <title>${category.categoryName}</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

    <style>
      .pagination{
        justify-content: center;
      }

      .pagination > li > a {
        float: none;
      }


    .page-link {
      color: rgb(98, 91, 91); 
      background-color: #fff;
      border: 1px solid #ccc; 
    }

    .page-item.active .page-link {
    z-index: 1;
    color: rgb(228, 145, 145);
    font-weight:bold;
    background-color: #f1f1f1;
    border-color: #ccc;
    
    }

    .page-link:focus, .page-link:hover {
      color: rgb(238, 8, 8);
      background-color: #fafafa; 
      border-color: #ccc;
    }

    .btnsocial{
      border: 0px;  
      outline: 0px;
      background-color: transparent;
    }

    .img-fluid{
      height: 250px;
    }

    </style>

</head>

  <body>
<!-- header start -->
<c:import url="/WEB-INF/views/template/header.jsp"></c:import>
<!-- header end -->

    <!-- ======= List Section ======= -->

    <section id="chefs" class="chefs section-bg">
      <div class="container" data-aos="fade-up">
                  <section id="book-a-table" class="book-a-table">
                    <div class="container" data-aos="fade-up">
                      <section id="menu" class="menu">
                            <div class="section-header">
                              <p><span>${category.categoryName}</span><p>
                                <input type="hidden" id="itemCatg" value="${pager.itemCatg}">
                            </div>
                            <ul class="nav nav-tabs d-flex justify-content-center" data-aos="fade-up" data-aos-delay="200">
                              <li class="nav-item">
                                <a class="nav-link" href="/sell/list?itemCatg=1">
                                  <h4>Hotelling</h4>
                                </a>
                              </li><!-- End tab nav item -->
                              <li class="nav-item">
                                <a class="nav-link" href="/sell/list?itemCatg=2">
                                  <h4>One day Class</h4>
                                </a>
                              </li><!-- End tab nav item -->
                              <li class="nav-item">
                                <a class="nav-link" href="/sell/list?itemCatg=3">
                                  <h4>Training</h4>
                                </a>
                              </li><!-- End tab nav item -->
                            </ul>
                      </section>
                    </div>
                  </section>
			<div class="row gy-1">	
              <c:forEach items="${list}" var="ar">
                <div class="col-lg-3 col-md-4 d-flex align-items-stretch justify-content-center" data-aos="fade-up" data-aos-delay="100">
                  <div class="chef-member" style="width: 250px; height: 350px; margin-bottom: 15px; display: flex; flex-direction: column; align-items: center;">
                    <div class="member-img" style="width: 160%;">
                      <img src="/resources/upload/sellfile/${ar.fileDTOs[0].fileName}" class="img-fluid" alt="">
                      	<div class="social d-flex" style="text-align: center;">
          				<c:choose>
           					<c:when test="${empty member.userId}">
                        		<button type="button" class="btnsocial" id="btnPickFalse">
                            		<img src="/resources/images/love.png" class="btnPickFalse" style="cursor: pointer; width: 20%; margin-right: 130px;" data-item-num="${ar.itemNum}">
                        		</button>
           					</c:when>
           					<c:when test="${not empty member.userId }">
           						<c:set var="doneLoop" value="false"/>
            					<c:set var="doneLoop2" value="false"/>
            					<c:forEach items="${pick }" var="pick" varStatus="vs">
            						<c:choose>
             							<c:when test="${member.userId eq pick.pickDTOs[0].userId and ar.itemNum eq pick.pickDTOs[0].itemNum}">
             								<button type="button" class="btnsocial" id="btnPick">
                    							<img src="/resources/images/heart.png" class="btnPickDelete" style="cursor: pointer; width: 20%; margin-right: 130px;" data-item-num="${ar.itemNum}" data-id="${pick.pickDTOs[0].userId}">
                        		</button>
                        					<c:set var="doneLoop" value="true"/>
             							</c:when>
             							<c:when test="${vs.last and not doneLoop }">
             								<button type="button" class="btnsocial" id="btnPick">
                                  				<img src="/resources/images/love.png" class="btnPickAdd" style="cursor: pointer; width: 20%; margin-right: 130px;" data-item-num="${ar.itemNum}">
                        		</button>
             							</c:when>
            						</c:choose>
            					</c:forEach>
            					<!--<c:forEach items="${shopcart }" var="shopcart" varStatus="scs">
            						<c:choose>
            							<c:when test="${member.userId eq shopcart.shopCartDTOs[0].userId and ar.itemNum eq shopcart.shopCartDTOs[0].itemNum }">
	            							<button type="button" class="btnsocial" id="btnShopCart">
	                                  			<img src="/resources/images/trolley.png" class="btnShopCartDelete" style="cursor: pointer; width: 20%;" data-item-num="${ar.itemNum}" data-id="${shopcart.shopCartDTOs[0].userId}"> 
	                        				</button>
	                        				<c:set var="doneLoop2" value="true"/>
            							</c:when>
            							<c:when test="${scs.last and not doneLoop2 }">
            								<button type="button" class="btnsocial" id="btnShopCart">
                                  				<img src="/resources/images/shopping-cart.png" class="btnShopCartAdd" style="cursor: pointer; width: 20%;" data-item-num="${ar.itemNum}" data-item-price="${ar.itemPrice }">
                        					</button>
            							</c:when>
            						</c:choose>
            					</c:forEach>-->
            				</c:when>        
            			</c:choose>
                      	</div>
                    </div>
                    <div class="member-info" style="width: 100%; height: 115px;">
                      <h4><a href="./detail?itemNum=${ar.itemNum}">${ar.itemName}</a></h4>
                      <span>₩ ${ar.itemPrice}</span>
                    </div>
                  </div>
                </div>
              </c:forEach>
          </div>
        <!-- End grid1div -->
      </div>
    </section> 
    
    <!-- pagination start -->
    <div class="chefs section-bg" style="padding-bottom: 10px;">
      <nav aria-label="Page navigation example">
        <ul class="pagination">
          <li class="page-item ${pager.pre?'':'disabled'}">
            <a class="page-link" href="./list?page=${pager.startNum-1}&itemCatg=${pager.itemCatg}&search=${pager.search}" aria-label="Previous">
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
            <li class="page-item"><a class="page-link" href="./list?page=${i}&itemCatg=${pager.itemCatg}&search=${pager.search}"> ${i}</a> </li>
          </c:forEach>
          <li class="page-item ${pager.next?'':'disabled'}">
            <a class="page-link" href="./list?page=${pager.lastNum+1}&itemCatg=${pager.itemCatg}&search=${pager.search}" aria-label="Next">
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
    <!-- pagination end -->        



 <!-- footer start -->
 <section>
  <c:import url="/WEB-INF/views/template/footer.jsp"></c:import>
</section>
<!-- footer end -->

  <script src="/resources/JS/pick&shopcart.js"></script>
</body>

</html>