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


  <style>
    ul{
    list-style: none;
    }
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

  
    <!-- ======= Menu Section ======= -->
    <section id="chefs" class="chefs section-bg">
      <div class="container" data-aos="fade-up">
                 
                 <div class="section-header">
                  <p><span>Search</span><p>
                 </div>

          <c:if test="${empty list}">
            <div class="section-header">
              <div class="not_found02"> 
                <p><em>'${pager.search}'</em>에 대한 검색결과가 없습니다.</p>  
                <ul> 
                  <li>단어의 철자가 정확한지 확인해 보세요.</li> 
                  <li>한글을 영어로 혹은 영어를 한글로 입력했는지 확인해 보세요.</li> 
                  <li>검색어의 단어 수를 줄이거나, 보다 일반적인 검색어로 다시 검색해 보세요.</li> 
                  <li>두 단어 이상의 검색어인 경우, 띄어쓰기를 확인해 보세요. </li>
                  <li>검색 옵션을 변경해서 다시 검색해 보세요.</li> 
                </ul> 
              </div>
            </div>
          </c:if>
          <div class="row gy-1">	
            <c:forEach items="${list}" var="ar">
              <div class="col-lg-3 col-md-4 d-flex align-items-stretch" data-aos="fade-up" data-aos-delay="100">
                <div class="chef-member" style="width: 250px; height: 350px; margin-bottom: 15px;">
                  <div class="member-img" style="width: 100%;">
                    <img src="/resources/upload/sellfile/${ar.fileDTOs[0].fileName}" class="img-fluid" alt="">
                    <div class="social d-flex">
                      <button type="button" class="btnsocial" id="btnPick">
                        <i class="bi bi-balloon-heart btnPick" style="cursor: pointer;" data-item-num="${ar.itemNum}"></i>
                      </button>
                        <i class="bi bi-facebook" style="cursor: pointer;" id="btnShopCart"></i></a>
                      </p>
                    </div>
                  </div>
                  <div class="member-info" style="width: 100%; height: 115px;">
                    <h4><a href="./detail?itemNum=${ar.itemNum}">${ar.itemName}</a></h4>
                    <!-- <p>${ar.itemAddress}</p> -->
                    <span>${ar.itemPrice}</span>
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
        <li class="page-item ${sellPager.pre?'':'disabled'}">
          <a class="page-link" href="./search?page=${sellPager.startNum-1}&itemCatg=${sellPager.itemCatg}&search=${sellPager.search}" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </a>
        </li>
        <c:forEach begin="${sellPager.startNum}" end="${sellPager.lastNum}" var="i">
          <li class="page-item"><a class="page-link" href="./search?page=${i}&itemCatg=${sellPager.itemCatg}&search=${sellPager.search}"> ${i}</a> </li>
        </c:forEach>
        <li class="page-item ${pager.next?'':'disabled'}">
          <a class="page-link" href="./search?page=${sellPager.lastNum+1}&itemCatg=${sellPager.itemCatg}&search=${sellPager.search}" aria-label="Next">
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




</body>

</html>