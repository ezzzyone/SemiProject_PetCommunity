<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>FC TCC           
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
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

  <div class="headerWrapper" style="position: relative;">
    <!-- header start -->
    <c:import url="/WEB-INF/views/template/header.jsp"></c:import>
    <!-- header end -->
  </div>

  
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
                                <a class="nav-link" href="/sell/Sellerlist?itemCatg=1">
                                  <h4>Hotelling</h4>
                                </a>
                              </li><!-- End tab nav item -->
                              <li class="nav-item">
                                <a class="nav-link" href="/sell/Sellerlist?itemCatg=2">
                                  <h4>One day Class</h4>
                                </a>
                              </li><!-- End tab nav item -->
                              <li class="nav-item">
                                <a class="nav-link" href="/sell/Sellerlist?itemCatg=3">
                                  <h4>Training</h4>
                                </a>
                              </li><!-- End tab nav item -->
                              </ul>
                          </section>
                        </div> 
                      </section>   

			<div class="row gy-1">	
			 <c:if test="${empty list}">
	        	<div class="section-header">
	         		<p>판매 중인 상품이 없습니다</p>
	         		<p>다양한 상품을 등록하고 고객의 눈길을 사로잡으세요!</p>
	        	</div>
	       	 </c:if>
			
              <c:forEach items="${list}" var="ar">
                <div class="col-lg-3 col-md-4 d-flex align-items-stretch justify-content-center" data-aos="fade-up" data-aos-delay="100">
                  <div class="chef-member" style="width: 250px; height: 350px; margin-bottom: 15px; display: flex; flex-direction: column; align-items: center;">
                      <div class="member-img" style="width: 160%;">
                        <img src="/resources/upload/sellfile/${ar.fileDTOs[0].fileName}" class="img-fluid" alt="">
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
    <c:if test="${pager.totalPage ne 0}">
      <div class="chefs section-bg" style="padding-bottom: 10px;">
        <nav aria-label="Page navigation example">
          <ul class="pagination">
            <li class="page-item ${pager.pre?'':'disabled'}">
              <a class="page-link" href="./Sellerlist?page=${pager.startNum-1}&itemCatg=${pager.itemCatg}&search=${pager.search}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
              </a>
            </li>
            <c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i">
              <li class="page-item"><a class="page-link" href="./Sellerlist?page=${i}&itemCatg=${pager.itemCatg}&search=${pager.search}"> ${i}</a> </li>
            </c:forEach>
            <li class="page-item ${pager.next?'':'disabled'}">
              <a class="page-link" href="./Sellerlist?page=${pager.lastNum+1}&itemCatg=${pager.itemCatg}&search=${pager.search}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
              </a>
            </li>
          </ul>
        </nav>
      </div>
    </c:if>  
    <!-- pagination end -->     


       

   <!-- footer start -->
   <c:import url="/WEB-INF/views/template/footer.jsp"></c:import>
   <!-- footer end -->

  <script src="/resources/JS/pick&shopcart.js"></script>
  <script src="/resources/assets/js/main.js"></script>
  <script>history.scrollRestoration = "manual"</script>

  <script>
  </script>
</body>

</html>