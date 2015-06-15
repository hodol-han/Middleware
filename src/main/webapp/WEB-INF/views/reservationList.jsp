<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<section class="row header">
			<h1>Your Reservations</h1>
			<%@ include file="/WEB-INF/views/header.jsp"%>
		</section>

		<!-- Inflate reservations here -->
		<div class="docs-section">
			<table class="u-full-width">
				<thead>
					<tr>
						<th>Washer Name</th>
						<th>Duration</th>
						<th>Link</th>
					</tr>
				</thead>

				<tbody id="books">

					<c:forEach items="${washers}" var="entry" varStatus="status">
						<c:forEach items="${entry.value}" var="resv">
							<td><c:out value="${entry.key}"></c:out></td>
							<td><c:out value="${resv.washerName}"></c:out></td>
							<td><c:out value="${resv.duration}"></c:out></td>
						</c:forEach>
					
						<div class="four columns" <c:if test="${status.index %3 == 0}">style="margin-left:0"</c:if> >
							<a href="<c:url value="/Washer/Detail/${entry.key}"/>"> <img
									class="u-max-full-width"
									src="<c:url value="/images/washer.png"/>"></a>
							<h5>
								<b>${entry.key}</b><br>${entry.value} people waiting
							</h5>
					</div>
					</c:forEach>



					<c:forEach items="${reservations}" var="reservation">
						<tr>
							<td><c:out value="${reservations.washerName}"></c:out></td>
							<td><c:out value="${reservation.duration}"></c:out></td>
							<td><a href="<c:url value="/Washer/${reservation.washerName}"/>">Detail</a></td>
						</tr>
					</c:forEach>


				</tbody>
			</table>
		</div>




	</div>

	<!-- End Document
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>
</html>
