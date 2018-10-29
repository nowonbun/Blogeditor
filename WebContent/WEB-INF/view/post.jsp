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
		<div style="text-align: right">
			<a class="btn btn-primary btn-sm" role="button" id="createPost">작성하기</a>
		</div>
		<div class="row mb-3">
			<div class="col-12 d-flex align-items-stretch">
				<div class="card my-blog-post">
					<div class="card-body">
						<form id="mainForm">
							<input type="hidden" id="category_code" value="${category_code}">
							<h1 class="card-title">
								<input class="form-control form-control-lg" type="text" id="title" placeholder="제목">
							</h1>
							<div class="card-text">
								<textarea id="summernote">
							</textarea>
							</div>
							<div class="mt-2 row">
								<div class="col-3">
									<input class="form-control form-control-sm" type="text" id="urlkey" placeholder="urlkey">
								</div>
								<div class="col-2">
									<input class="form-control form-control-sm" type="number" id="changefleg" placeholder="CHANGEFREG">
								</div>
								<div class="col-2">
									<input class="form-control form-control-sm" type="number" id="priority" placeholder="PRIORITY">
								</div>
								<div class="col-5">
									<div class="row">
										<div class="col-8">
											<input class="form-control form-control-sm" type="text" id="image" placeholder="IMAGE" readonly>
										</div>
										<div class="file-field col-4">
											<div class="btn btn-primary btn-sm float-left" style="margin: 0px;">
												<span>Choose</span> <input type="file" id="img_file" accept="image/*" onchange="$('#image').val($(this).val())">
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${isViewRecently eq true}">
			<fieldset class="box-shadow-0 px-3 py-3 blog-radius mb-3 my-style-custom">
				<legend class="box-shadow-0 blog-legend px-3 blog-radius">
					<label>최신글</label>
				</legend>
				<div style="text-align: center;">
					<img src="./img/image_fix.png" style="height: 40px">
				</div>
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
				$('#summernote').summernote({
					height : 500
				});
				$(".note-popover.popover").each(function() {
					$(this).hide();
				});
				$(document).off("change", '.file-field input[type="file"]').on("change", '.file-field input[type="file"]', function() {
					var file = $("#img_file")[0].files[0];
					if (file.size > 1024) {
						console.log(file.size);
					}
					_.readFile(file, function(node) {
						_.image = node.binary;

					});
				});
				$("#createPost").on("click", function() {
					if ($.trim($("#category_code").val()) === "") {
						toastr.error('No empty of category_code.');
						return;
					}
					
					if ($.trim($("#title").val()) === "") {
						toastr.error('No empty of title.');
						return;
					}
					
					var contents = $("#summernote").summernote('code');
					if ($.trim(contents) === "") {
						toastr.error('No empty of contents.');
						return;
					}
					
					if (_.image == null) {
						toastr.error('No empty of iamge.');
						return;
					}

					var data = {
						categoryCode : $("#category_code").val(),
						title : $("#title").val(),
						contents : contents,
						urlkey : $("#urlkey").val(),
						changefleg : $("#changefleg").val(),
						priority : $("#priority").val(),
						summary: $(".note-editable")[0].outerText.replace(/\n\n/gi,"\n"),
						image : _.image
					};
					$.ajax({
						url : "insertPost.ajax",
						type : "POST",
						dataType : "json",
						data : JSON.stringify(data),
						complete : function() {
							toastr.info('Data insert OK!');
						},
						error : function(xhr, error, thrown) {
							toastr.error('insert error.');
							console.log(xhr);
							console.log(error);
							console.log(thrown);
						}
					});
				});
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