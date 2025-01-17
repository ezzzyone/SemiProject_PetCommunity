<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>Account Delete Page</title>
<meta content="" name="description">
<meta content="" name="keywords">

<!-- Favicons -->
<link href="/resources/assets/img/favicon.png" rel="icon">
<link href="/resources/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

<!-- Google Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,600;1,700&family=Amatic+SC:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&family=Inter:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&display=swap" rel="stylesheet">

<!-- Vendor CSS Files -->
<link href="/resources/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
<link href="/resources/assets/vendor/aos/aos.css" rel="stylesheet">
<link href="/resources/assets/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
<link href="/resources/assets/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">

<!-- Template Main CSS File -->
<link href="/resources/assets/css/main.css" rel="stylesheet">

<style type="text/css">
.col{
  background-color:cornsilk;
  overflow: scroll;
  width: 600px;
  height: 100px;
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
</style>

</head>
<body>
<!-- header start -->
<c:import url="/WEB-INF/views/template/header.jsp"></c:import>
<!-- header end -->
<!-- ======= Join Section ======= -->
<section id="book-a-table" class="book-a-table">
  <div class="container" data-aos="fade-up">

    <div class="section-header">
      <p><span>Confirm page</span></p>
    </div>


      <div class="col-lg-8 align-items-center">
        <form action="delete" method="post" data-aos="fade-up" data-aos-delay="100" id="delfrm">

            <div class="row gy-4">
            <div class="col-lg-8 col-md-12">
              <input type="text" name="pw" class="form-control" placeholder="password" value="wldnjs" id="inputpw">
              <div class="validate"></div>
            </div>
          </div>
          <br>
         <div class="text-center">
           <button type="submit" id="del">회원탈퇴</button>
         </div>
         
        </form>
      </div>

    </div>

</section>



<!-- footer start -->
<c:import url="/WEB-INF/views/template/footer.jsp"></c:import>
<!-- footer end -->

<script src="/resources/JS/member.js"></script>

</body>
</html>