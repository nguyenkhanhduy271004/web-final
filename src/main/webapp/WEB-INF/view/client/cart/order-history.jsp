<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8">
                <title>Lịch sử mua hàng - BeautyShop</title>
                <meta content="width=device-width, initial-scale=1.0" name="viewport">
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link
                    href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&family=Poppins:wght@300;400&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                    rel="stylesheet">
                <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
                <link href="/client/css/bootstrap.min.css" rel="stylesheet">
                <link href="/client/css/style.css" rel="stylesheet">
                <link href="<c:url value='/client/css/carousel-product.css' />" rel="stylesheet">
                <link href="<c:url value='/client/css/header.css' />" rel="stylesheet">
                <link href="<c:url value='/client/css/footer.css' />" rel="stylesheet">
                <link href="<c:url value='/client/css/common.css' />" rel="stylesheet">
                <link href="<c:url value='/client/css/product.css' />" rel="stylesheet">
                <link href="<c:url value='/client/css/hot-product.css' />" rel="stylesheet">
                <link href="<c:url value='/client/css/sale-product.css' />" rel="stylesheet">
                <link href="<c:url value='/client/css/responsive-home.css' />" rel="stylesheet">
                <style>
                    :root {
                        --primary-color: #FFC0CB;
                        --accent-color: #B76E79;
                        --text-color: #333333;
                        --background-color: #F8E1E7;
                        --gold-color: #FFD700;
                    }

                    body {
                        font-family: 'Poppins', sans-serif;
                        color: var(--text-color);
                        background-color: var(--background-color);
                    }

                    h1,
                    h2,
                    h3,
                    .breadcrumb-item,
                    .btn,
                    .table th,
                    .table td {
                        font-family: 'Playfair Display', serif;
                    }

                    .btn-primary {
                        background-color: var(--primary-color);
                        color: white;
                        border-radius: 30px;
                        font-weight: bold;
                        transition: background-color 0.3s;
                    }

                    .btn-primary:hover {
                        background-color: var(--accent-color);
                        color: white;
                    }

                    .header-bar {
                        background-color: white;
                        border-bottom: 1px solid var(--gold-color);
                    }

                    .product-list {
                        display: flex;
                        flex-wrap: wrap;
                        gap: 15px;
                    }

                    .product-card {
                        width: 100%;
                        max-width: 200px;
                        margin: 0 auto;
                        text-align: center;
                        background-color: white;
                        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
                        border-radius: 8px;
                        overflow: hidden;
                    }

                    .table th,
                    .table td {
                        text-align: center;
                        vertical-align: middle;
                    }

                    .breadcrumb {
                        background-color: transparent;
                        padding: 0;
                    }

                    .breadcrumb-item a {
                        color: var(--text-color);
                    }

                    .breadcrumb-item.active {
                        color: var(--accent-color);
                    }
                </style>
            </head>

            <body>
                <div id="spinner"
                    class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
                    <div class="spinner-grow text-primary" role="status"></div>
                </div>

                <jsp:include page="../layout/header.jsp" />

                <div class="container-fluid py-5">
                    <div class="container py-5">
                        <nav aria-label="breadcrumb" class="mb-4">
                            <ol class="breadcrumb">
                                <li class="breadcrumb-item"><a href="/">Home</a></li>
                                <li class="breadcrumb-item active" aria-current="page">Lịch sử mua hàng</li>
                            </ol>
                        </nav>

                        <div class="table-responsive">
                            <table class="table align-middle table-borderless">
                                <thead>
                                    <tr>
                                        <th scope="col">Sản phẩm</th>
                                        <th scope="col">Tên</th>
                                        <th scope="col">Giá cả</th>
                                        <th scope="col">Số lượng</th>
                                        <th scope="col">Thành tiền</th>
                                        <th scope="col">Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${empty orders}">
                                        <tr>
                                            <td colspan="6" class="text-center text-muted">Không có đơn hàng nào được
                                                tạo</td>
                                        </tr>
                                    </c:if>
                                    <c:forEach var="order" items="${orders}">
                                        <tr>
                                            <td colspan="2"><strong>Order ID: ${order.id}</strong></td>
                                            <td>
                                                <fmt:formatNumber type="number" value="${order.totalPrice}" /> đ
                                            </td>
                                            <td></td>
                                            <td>${order.status}</td>
                                        </tr>
                                        <c:forEach var="orderDetail" items="${order.orderDetails}">
                                            <tr>
                                                <td>
                                                    <img src="/images/product/${orderDetail.product.image}"
                                                        class="img-fluid rounded" style="width: 80px; height: 80px;">
                                                </td>
                                                <td><a href="/product/${orderDetail.product.id}"
                                                        class="text-decoration-none">${orderDetail.product.name}</a>
                                                </td>
                                                <td>
                                                    <fmt:formatNumber type="number" value="${orderDetail.price}" /> đ
                                                </td>
                                                <td>${orderDetail.quantity}</td>
                                                <td>
                                                    <fmt:formatNumber type="number"
                                                        value="${orderDetail.price * orderDetail.quantity}" /> đ
                                                </td>
                                                <td>${order.status}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <jsp:include page="../layout/footer.jsp" />

                <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                        class="fa fa-arrow-up"></i></a>

                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/lib/easing/easing.min.js"></script>
                <script src="/client/lib/waypoints/waypoints.min.js"></script>
                <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>
                <script src="/client/js/main.js?version=1"></script>
            </body>

            </html>