<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="./share/pagetop.jsp"></jsp:include>
</head>
<body lang="ko">
	<div id="bodyMain" class="container wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
		<jsp:include page="./share/header.jsp"></jsp:include>
		<main class="mt-5">
		<div style="text-align: right">
			<c:if test="${postModel.idx eq -1}">
				<a class="btn btn-primary btn-sm" role="button" id="createPost">작성하기</a>
				<a class="btn btn-primary btn-sm" role="button" id="modifyPost" style="display: none;">수정하기</a>
				<a class="btn btn-primary btn-sm" role="button" id="deletePost" style="display: none;">삭제하기</a>
			</c:if>
			<c:if test="${postModel.idx ne -1}">
				<a class="btn btn-primary btn-sm" role="button" id="createPost" style="display: none;">작성하기</a>
				<a class="btn btn-primary btn-sm" role="button" id="modifyPost">수정하기</a>
				<a class="btn btn-primary btn-sm" role="button" id="deletePost">삭제하기</a>
			</c:if>
		</div>
		<div class="row mb-3">
			<div class="col-12 d-flex align-items-stretch">
				<div class="card my-blog-post">
					<div class="card-body">
						<form id="mainForm">
							<input type="hidden" id="category_code" value="${postModel.categoryCode}"> <input type="hidden" id="post_code" value="${postModel.idx}">
							<h2 class="card-title">
								<input class="form-control form-control-lg" type="text" id="title" placeholder="제목" value="${postModel.title}">
							</h2>
							<div class="row justify-content-end mb-3">
								<div class="col-2">
									<img src="./img/copyrightmark.png" style="height:20px">
								</div>
								<div class="col-5">
									<span class="my-list-date">작성일시 : ${postModel.createDate}</span>
								</div>
								<div class="col-5">
									<span class="my-list-date">최종수정일시 : ${postModel.lastUpdateDate}</span>
								</div>
							</div>
							<div class="card-text">
								<textarea id="summernote"><c:out value="${postModel.contents}" escapeXml="true" /></textarea>
							</div>
							<div class="mt-2 row">
								<div class="col-4">
									<div class="md-form">
										<input type="text" id="urlkey" class="form-control" value="${postModel.urlkey}"> <label for="urlkey">url unique key</label>
									</div>
								</div>
								<div class="col-4">
									<div class="md-form">
										<input type="number" id="changeflag" class="form-control" value="${postModel.changeflag}" min="1" max="9"> <label for="changefleg">change flag</label>
									</div>
								</div>
								<div class="col-4">
									<div class="md-form">
										<input type="number" id="priority" class="form-control" value="${postModel.priority}" min="1" max="9"> <label for="priority">priority</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 col-lg-5 mb-2">
									<p>
										<img src="${postModel.image}" id="imagepanel" class="mt-3 mb-3 summary-image img-fluid">
									</p>
									<input class="form-control form-control-sm" type="text" id="image" placeholder="IMAGE" readonly style="width: 250px; display: inline;">
									<div class="file-field" style="display: inline;">
										<div class="btn btn-primary btn-sm float-left" style="margin: 0px;">
											<span>Choose</span> <input type="file" id="img_file" accept="image/*" onchange="$('#image').val($(this).val())">
										</div>
									</div>
									<div style="font-size: 7pt;">Size 680(width) * 340(height) and Less than 100,000 byte</div>
									<div class="md-form">
										<input type="text" id="imageComment" class="form-control" value="${postModel.imageComment}"> <label for="imageComment">Image Comment</label>
									</div>
								</div>
								<div class="col-md-12 col-lg-7 mb-2">
									<div style="text-align: right;">
										<a class="btn btn-primary btn-sm" role="button" id="getSummary">서머리 가져오기</a>
									</div>
									<textarea id="summaryArea" style="width: 100%; height: 80%; resize: none;">${postModel.summary}</textarea>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${postModel.isPreNextPostView eq true}">
			<div class="row mt-3 mb-3">
				<div class="col-12 d-flex align-items-stretch">
					<div class="card my-style-custom">
						<div class="card-body my-pre-post-nav">
							<div class="row">
								<c:if test="${postModel.isPrePost eq true}">
									<div class="col-12 mb-1">
										<a href="./?category=02&post=${postModel.prePostIdx}"> <span class="my-pre-next-icon"><i class="fa fa-chevron-up"></i>이전글</span>${postModel.prePost}
										</a>
										<p class="my-list-date float-right">${postModel.prePostDate}</p>
									</div>
								</c:if>
								<c:if test="${postModel.isPrePost eq true && postModel.isNextPost eq true}">
									<div class="col-12 my-blog-line"></div>
								</c:if>
								<c:if test="${postModel.isNextPost eq true}">
									<div class="col-12 mt-1">
										<a href="./?category=02&post=${postModel.nextPostIdx}"><span class="my-pre-next-icon"><i class="fa fa-chevron-down"></i>다음글</span>${postModel.nextPost}</a>
										<p class="my-list-date float-right">${postModel.nextPostDate}</p>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if> <c:if test="${postModel.isViewRecently eq true}">
			<fieldset class="box-shadow-0 px-3 py-3 blog-radius mb-3 my-style-custom">
				<legend class="box-shadow-0 blog-legend px-3 blog-radius mb-0">
					<label>최신글</label>
				</legend>
				<ul class="pl-3">
					<c:forEach items="${postModel.recentlyList}" var="item">
						<li>
							<div class="row">
								<div class="col-12 mb-0">
									<a href="./?category=${item.categoryCode}&post=${item.idx}">${item.title} </a>
									<p class="my-list-date float-right">${item.date}</p>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</fieldset>
		</c:if>
		<fieldset class="box-shadow-0 px-3 py-3 blog-radius mb-3 my-style-custom">
			<legend class="box-shadow-0 blog-legend px-3 blog-radius">
				<label>댓글 달기</label>
			</legend>
			<div style="text-align: center;">
				<img src="./img/image_fix.png" style="height: 40px">
			</div>
		</fieldset>
		</main>
		<jsp:include page="./share/footer.jsp"></jsp:include>
	</div>
	<jsp:include page="./share/pagebottom.jsp"></jsp:include>
	<script>
		var _ = (function(obj) {
			$(obj.onLoad);
			return obj;
		})({
			image : null,
			onLoad : function() {
				$(document).on("click", "#menuToggler", function(event) {
					event.preventDefault();
					event.stopPropagation();
					event.stopImmediatePropagation();
				});
				$(document).on("click", function() {
					if ($("#menuToggler").attr("aria-expanded") === 'true') {
						$("#menuToggler").click();
					}
				});
				if ($.trim($("#imagepanel").prop("src")) !== "") {
					_.image = $("#imagepanel").prop("src");
				}
				var code = $('#summernote').val();
				$('#summernote').summernote({
					height : 500
				});
				$('#summernote').summernote('code', code);
				$(document).off("change", '.file-field input[type="file"]').on("change", '.file-field input[type="file"]', function() {
					var file = $("#img_file")[0].files[0];
					if (file.size > 200000) {
						toastr.error('The image size is exceeded.');
						return;
					}
					_.readFile(file, function(node) {
						_.image = node.binary;
						$("#imagepanel").prop("src", _.image);
					});
				});
				$("#createPost").on("click", function() {
					if (!_.validate()) {
						return;
					}
					_.sendAjax("insertPost.ajax", function() {
						window.location.href = "./?category=" + $("#category_code").val() + "&post=" + $("#post_code").val();
					});
				});
				$("#modifyPost").on("click", function() {
					if (!_.validate()) {
						return;
					}
					_.sendAjax("modifyPost.ajax", function() {
						window.location.href = "./?category=" + $("#category_code").val() + "&post=" + $("#post_code").val();
					});
				});
				$("#deletePost").on("click", function() {
					_.sendAjax("deletePost.ajax", function() {
						window.location.href = "./?category=" + $("#category_code").val();
					});
				});
				$("#getSummary").on("click", function() {
					$("#summaryArea").val($(".note-editable")[0].outerText.replace(/\n\n/gi, "\n"));
				});
				var data = $.cookie("NOTIFICATION");
				if (data !== undefined && data !== null) {
					_.notification(JSON.parse(data));
					$.removeCookie("NOTIFICATION");
				}
			},
			sendAjax : function(url, cb) {
				$.ajax({
					url : url,
					type : "POST",
					dataType : "json",
					data : JSON.stringify(_.getData()),
					success : function(data) {
						$.cookie("NOTIFICATION", JSON.stringify(data));
						$("#post_code").val(data.postCode);
						if (cb !== null && cb !== undefined && typeof cb === "function") {
							cb.call(this);
						}
					},
					error : function(xhr, error, thrown) {
						toastr.error('error.');
						console.log(xhr);
						console.log(error);
						console.log(thrown);
					}
				});
			},
			validate : function() {
				if ($.trim($("#category_code").val()) === "") {
					toastr.error('No empty of category_code.');
					return false;
				}

				if ($.trim($("#title").val()) === "") {
					toastr.error('No empty of title.');
					return false;
				}

				var contents = $("#summernote").summernote('code');
				if ($.trim(contents) === "") {
					toastr.error('No empty of contents.');
					return false;
				}

				if (_.image == null) {
					toastr.error('No empty of iamge.');
					return false;
				}
				if ($.trim($("#summaryArea").val()) === "") {
					toastr.error('No empty of summary.');
					return false;
				}
				
				if ($.trim($("#imageComment").val()) === "") {
					toastr.error('No empty of Image Comment.');
					return false;
				}
				return true;
			},
			getData : function() {
				return {
					idx : $("#post_code").val(),
					categoryCode : $("#category_code").val(),
					title : $("#title").val(),
					contents : $("#summernote").summernote("code"),
					urlkey : $("#urlkey").val(),
					changeflag : $("#changeflag").val(),
					priority : $("#priority").val(),
					summary : $("#summaryArea").val().replace(/\n/gi, "<br>"),
					image : _.image,
					imageComment : $("#imageComment").val()
				};
			},
			notification : function(json) {
				if (json.type === "W") {
					toastr.warning(json.message);
				} else if (json.type === "I") {
					toastr.info(json.message);
				} else if (json.type === "S") {
					toastr.success(json.message);
				} else {
					toastr.error(json.message);
				}
			},
			readFile : function(file, cb) {
				node = new function() {
					return {
						reader : new FileReader(),
						file : file
					}
				};
				node.reader.onload = function(e) {
					node.binary = this.result;
					if (cb !== null && cb !== undefined && typeof cb === "function") {
						cb.call(this, node);
						return;
					}
				}
				node.reader.readAsDataURL(file);
				return node;
			}
		})
	</script>
</body>
</html>