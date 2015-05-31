<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
<!-- embed jQuery javascript file here -->


<!-- Favicon
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link rel="icon" type="image/png" href="images/favicon.png">

</head>
<body>

	<!-- Primary Page Layout
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
	<div class="container">
		<section class="header">
			<h1>Current Runing Washers</h1>
			<%@ include file="/WEB-INF/views/header.jsp"%>
		</section>
		<div class="docs-section">
			<!-- Inflate search results here -->
			<div class="row" style="text-align: center;">
				<div class="four columns">
					<a href="<c:url value="Washer/Detail/Power"/>"> <image
							class="u-max-full-width" src="<c:url value="/images/washer.png"/>"></a>
					<h5>Name, 0 people waiting</h5>

				</div>
				<div class="four columns">
					<image class="u-max-full-width" src="<c:url value="/images/washer_busy.png"/>">
					<h5>Name, 2 people waiting</h5>
				</div>
				<div class="four columns">
					<image class="u-max-full-width" src="<c:url value="/images/washer.png"/>">
					<h5>Name, 0 people waiting</h5>
				</div>
			</div>

			<div class="row" style="text-align: center;">
				<div class="four columns">
					<image class="u-max-full-width" src="<c:url value="/images/washer_reallybusy.png"/>">
					<h5>Name, 6 people waiting</h5>
				</div>
				<div class="four columns">
					<image class="u-max-full-width" src="<c:url value="images/washer_reallybusy.png"/>">
					<h5>Name, 10 people waiting</h5>
				</div>
				<div class="four columns">
					<image class="u-max-full-width" src="<c:url value="images/washer_busy.png"/>">
					<h5>Name, 2 people waiting</h5>
				</div>
			</div>


		</div>

	</div>

	<!-- End Document
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>
</html>
