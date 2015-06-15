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
<script src="<c:url value="/js/jquery-1.11.3.min.js"/>"
	type="text/javascript"></script>


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
				<c:forEach items="${washers}" var="entry" varStatus="status">
					<div class="four columns" <c:if test="${status.index %3 == 0}">style="margin-left:0"</c:if> >
						<a href="<c:url value="/Washer/Detail/${entry.key}"/>"> <img
								class="u-max-full-width"
								src="<c:url value="/images/washer.png"/>"></a>
						<h5>
							<b>${entry.key}</b><br>${entry.value} people waiting
						</h5>
					</div>
				</c:forEach>
			</div>
			
		</div>

	</div>

	<!-- End Document
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>
</html>
