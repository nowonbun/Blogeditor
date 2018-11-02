<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="./share/pagetop.jsp"></jsp:include>
</head>
<body>
	<div class="container wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
		<jsp:include page="./share/header.jsp"></jsp:include>
		<main class="mt-5">
		<div class="container">
			<div class="list-title">
				<h1>${list_title}</h1>
			</div>
			<section>
				<input type="hidden" id="category_code" value="${category_code}">
				<div class="wow fadeIn" style="text-align: right;">
					<button type="button" class="btn btn-md btn-primary" id="writePost">Write Post</button>
				</div>
				<hr class="mb-3 mt-3">
				<c:forEach items="${list_item}" var="item">
					<!--Grid row-->
					<div class="row mt-3 wow fadeIn">
						<div class="col-lg-5 col-xl-4 mb-4">
							<div class="view overlay rounded z-depth-1">
								<img src="${item.image}" class="img-fluid summary-image" alt="${item.title}_image"> 
									<a href="./?category=${category_code}&post=${item.idx}">
									<div class="mask rgba-white-slight"></div>
								</a>
							</div>
						</div>
						<div class="col-lg-7 col-xl-7 ml-xl-4 mb-4">
							<h3 class="mb-3 font-weight-bold dark-grey-text">
								<p class="my-list-title">${item.title}</p>
							</h3>
							<p class="grey-text my-list-summary">${item.summary}</p>
							<div style="text-align:right;">
								<p class="my-list-date">${item.date}</p>
								<a href="./?category=${category_code}&post=${item.idx}" class="btn btn-primary btn-sm">Go read
									 <i class="fa fa-play ml-2"></i>
								</a>
							</div>
						</div>
					</div>
					<hr class="mb-3 mt-3">
				</c:forEach>
				<c:if test="${list_count eq 0}">
					<!--Grid row-->
					<div class="row wow fadeIn">
						<!--Grid column-->
						<div class="col-12">
							<h3 class="mb-3 font-weight-bold dark-grey-text no-list" style="text-align:center;">
								등록된 글이 없습니다.
							</h3>
						</div>
						<!--Grid column-->
					</div>
					<!--Grid row-->
					<hr class="mb-3 mt-3">
				</c:if>
				<!-- nav class="d-flex justify-content-center wow fadeIn">
					<ul class="pagination pg-blue">
						<li class="page-item disabled"><a class="page-link" href="#" aria-label="Previous"> <span aria-hidden="true">&laquo;</span> <span class="sr-only">Previous</span>
						</a></li>
						<li class="page-item active"><a class="page-link" href="#">1 <span class="sr-only">(current)</span>
						</a></li>
						<li class="page-item"><a class="page-link" href="#">2</a></li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#">4</a></li>
						<li class="page-item"><a class="page-link" href="#">5</a></li>
						<li class="page-item"><a class="page-link" href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span class="sr-only">Next</span>
						</a></li>
					</ul>
				</nav-->
			</section>
		</div>
		</main>
		<jsp:include page="./share/footer.jsp"></jsp:include>
		<jsp:include page="./share/pagebottom.jsp"></jsp:include>
		<script>
			var _ = (function(obj) {
				$(obj.onLoad);
				return obj;
			})({
				onLoad : function() {
					$("#writePost").on("click", function(){
						window.location.href="./index.html?category="+$("#category_code").val();
					});
					$(document).on("click", "#menuToggler",function(event){
						event.preventDefault();
						event.stopPropagation();
						event.stopImmediatePropagation();
					});
					$(document).on("click", function(){
						if($("#menuToggler").attr("aria-expanded") === 'true'){
							$("#menuToggler").click();
						}
					});
				}
			});
		</script>
	</div>
</body>
</html>