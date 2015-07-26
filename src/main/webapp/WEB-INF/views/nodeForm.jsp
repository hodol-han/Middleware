<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Basic Page Needs
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<meta charset="utf-8">
<title>Your page title here :)</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Mobile Specific Metas
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- FONT
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link href="http://fonts.googleapis.com/css?family=Raleway:400,300,600"
	rel="stylesheet" type="text/css">

<!-- CSS
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link rel="stylesheet" href="<c:url value="/css/normalize.css"/>">
<link rel="stylesheet" href="<c:url value="/css/skeleton.css"/>">
<link rel="stylesheet" href="<c:url value="/css/custom.css"/>">

<!-- JavaScript
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<!-- jQuery -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>

<!-- Favicon
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link rel="icon" type="image/png" href="/images/favicon.png">

</head>
<body>

	<!-- Primary Page Layout
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
	<div class="container">
		<section class="header">
			<!-- Set action url to modification if Book attribute exists in the 'model'. -->
			<h1>Node Registration/Modification</h1>
			<%@ include file="/WEB-INF/views/header.jsp"%>
		</section>

		<div class="docs-section u-full-width" style="text-align: center;">
			<form method="post" action="<c:url value="/Node/Register"/>">
				<div>
					<label class="field-label">Serial Number</label>
					<input id="serial" class="u-full-width" name="serial" type="text"
						required="required" placeholder="Serial number here">
				</div>
				<div>
					<label class="field-label">Product Name</label>
					<input id="product-name" class="u-full-width" name="product-name" type="text"
						required="required" placeholder="Product name here">
				</div>
				<div>
					<label class="field-label">Node Name</label>
					<input id="node-name" class="u-full-width" name="node-name" type="text"
						placeholder="Node name here">
				</div>
				
				<input class="button" type="submit" value="Register">
				<a class="button" href="<c:url value="/Node/List"/>">Back to List</a>
				
			</form>
			
		</div>

	</div>

	<!-- End Document
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>
</html>
