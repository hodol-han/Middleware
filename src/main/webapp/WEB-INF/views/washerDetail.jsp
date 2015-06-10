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
<link rel="icon" type="image/png"
	href="<c:url value="/images/favicon.png"/>">

</head>
<body>

	<!-- Primary Page Layout
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
	<div class="container">
		<section class="header">
			<h1>Washer Detail</h1>
			<%@ include file="/WEB-INF/views/header.jsp"%>
		</section>
		<div class="docs-section">
			<!-- Inflate search results here -->
			<div class="row" style="text-align: center;">
				<div class="six columns">
					<image class="u-max-full-width"
						src="<c:url value="/images/washer.png"/>">
					<h5><b>${name}</b><br>${size} people waiting</h5>
				</div>
				<div class="six columns">
					<h5>Current Queue of ${name}</h5>
					<ol>
						<%
							int i = 0;
						%>
						<c:forEach items="${reservations}" var="reservation">
							<li>${reservation.who},${reservation.duration/60000}
								minute(s) <c:if
									test="${reservation.who eq '${sessionScope.userid}'}">" "
						<a href="<c:url value="/Washer/Cancel/${name}/${i}"/>">X</a>
								</c:if>
							</li>
							<%
								i++;
							%>
						</c:forEach>
						<!-- 
						<li>User 1, 20 minute(s)</li>
						<li>User 5, 30 minute(s)</li>
						<li>User 3, 10 minute(s)</li>
						<li>User 4, 50 minute(s)</li>
					-->
					</ol>
					<button class="button-primary" id="doyouwannaenqueue">Do
						you want to enqueue?</button>
					<a class="button" href="<c:url value="/Washer"/>">Back to List</a>
						
					<script>
						$("#doyouwannaenqueue").click(function() {
							if ($("#enqueue-section").is(":visible"))
								$("#enqueue-section").hide();
							else
								$("#enqueue-section").show();
						});
					</script>
				</div>
			</div>



		</div>

		<div class="docs-section" id="enqueue-section" style="display: none">

			<div class="row" style="text-align: center;">
				<h3>Do you want to enqueue?</h3>
				<form action="<c:url value="/Washer/Enqueue/${name}"/>"
					method="post" style="">
					<input id="duration" name="duration" type="number"
						placeholder="Duration here"> minute(s). <input
						class="button button-primary" type="submit" value="Enqueue!">
				</form>
				<button id="idunnohowlongittakes">Don't you know how long
					does it take?</button>
				<script>
					$("#idunnohowlongittakes").click(function() {
						if ($("#calculateplz").is(":visible"))
							$("#calculateplz").hide();
						else
							$("#calculateplz").show();

					});
				</script>
				<div id="calculateplz" style="display: none">
					<h5>Check them to calculate roughly!</h5>
					<div class="row">
						<div class="six columns" style="text-align: right">
							<p>Other options</p>
							<label> <input name="option" type="checkbox"
								id="dehydrate" value="1.2"><span class="label-body">Dehydrate</span>
							</label> <label> <input name="option" type="checkbox" id="dry"
								value="1.5"><span class="label-body">Dry</span>
							</label>
						</div>

						<div class="six columns" style="text-align: left">
							<p>Amount of your laundry</p>
							<label> <input type="radio" name="amount" value="60"><span
								class="label-body">Many</span>
							</label> <label> <input type="radio" name="amount" value="30"><span
								class="label-body">Moderate</span>
							</label> <label> <input type="radio" name="amount" value="15"><span
								class="label-body">A few</span>
							</label>
						</div>
						<button class="button-primary" id="calcbutton">Calculate
							please!</button>
						<script>
							$("#calcbutton")
									.click(
											function() {
												var amount = $(
														':radio[name="amount"]:checked')
														.val();
												var multiplier = 1;

												var checkedboxes = $(':checkbox[name="option"]:checked');
												for (var i = 0; i < checkedboxes.length; i++) {
													multiplier *= $(
															checkedboxes[i])
															.val();
												}

												var duration = amount
														* multiplier;

												$("#duration").val(
														duration.toFixed(2));
											});
						</script>

					</div>
				</div>


			</div>

		</div>

	</div>

	<!-- End Document
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>
</html>
