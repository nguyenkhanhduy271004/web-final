<%@ page contentType="text/html" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

			<!DOCTYPE html>
			<html lang="en">

			<head>
				<meta charset="UTF-8">
				<meta name="viewport" content="width=device-width, initial-scale=1.0">
				<title>Trang chủ - Cosmetic Shop</title>

				<link rel="icon" href="<c:url value='/client/img/imgThuonghieu/4.png' />" type="image/png">

				<link rel="preconnect" href="https://fonts.googleapis.com">
				<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
				<link
					href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
					rel="stylesheet">

				<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
				<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
					rel="stylesheet">

				<link href="<c:url value='/client/lib/lightbox/css/lightbox.min.css' />" rel="stylesheet">
				<link href="<c:url value='/client/lib/owlcarousel/assets/owl.carousel.min.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/bootstrap.min.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/style.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/carousel-product.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/header.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/footer.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/common.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/product.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/hot-product.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/sale-product.css' />" rel="stylesheet">
				<link href="<c:url value='/client/css/responsive-home.css' />" rel="stylesheet">
				<link rel="stylesheet" href="./css/contact.css" />
				<link rel="stylesheet"
					href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css" />
				<link href="<c:url value='/client/css/contact.css' />" rel="stylesheet">

				<meta name="_csrf" content="${_csrf.token}" />
				<meta name="_csrf_header" content="${_csrf.headerName}" />

				<link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.css"
					rel="stylesheet">
			</head>

			<body>



				<jsp:include page="../layout/header.jsp" />
				<!-- Header -->
				<header>
					<div class="container">
						<h1>Liên hệ với chúng tôi</h1>
						<p>
							Chúng tôi luôn sẵn sàng giúp đỡ bạn. Để lại thông tin và câu hỏi của
							bạn bên dưới.
						</p>
					</div>
				</header>

				<!-- Contact Form Section -->
				<section class="contact-form">
					<div class="container">
						<form action="#" method="POST">
							<div class="form-group">
								<label for="name">Họ và tên:</label>
								<input type="text" id="name" name="name" required />
							</div>
							<div class="form-group">
								<label for="email">Email:</label>
								<input type="email" id="email" name="email" required />
							</div>
							<div class="form-group">
								<label for="phone">Số điện thoại:</label>
								<input type="tel" id="phone" name="phone" />
							</div>
							<div class="form-group">
								<label for="message">Lý do liên hệ:</label>
								<textarea id="message" name="message" rows="5" required></textarea>
							</div>
							<button type="submit">Gửi thông tin</button>
						</form>
					</div>
				</section>

				<!-- Footer -->
				<footer>
					<div class="container">
						<p>&copy; 2024 Mỹ phẩm XYZ. Tất cả quyền lợi được bảo lưu.</p>
					</div>
				</footer>


			</body>

			</html>