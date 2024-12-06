<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Nguyễn Duy - Dự án laptopshop" />
                <meta name="author" content="Nguyễn Duy" />
                <title>Manager Orders - Nguyễn Duy</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <!-- Add Bootstrap Icons for extra visual elements -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet" />
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4 mb-4">Manage Orders</h1>

                                <!-- Filter Form -->
                                <!-- <div class="row justify-content-center mb-4">
                                    <form action="/vendor/search" method="get" class="form-inline"
                                        style="display: flex; align-items: center; gap: 6px;">
                                        <label for="status" class="mr-2" style="width: 100%;">Filter by Status:</label>
                                        <select name="status" id="status" class="form-control mr-3">
                                            <option value="">All</option>
                                            <option value="PENDING" ${param.status eq 'PENDING' ? 'selected' : '' }>
                                                PENDING</option>
                                            <option value="SHIPPING" ${param.status eq 'SHIPPING' ? 'selected' : '' }>
                                                SHIPPING</option>
                                            <option value="COMPLETE" ${param.status eq 'COMPLETE' ? 'selected' : '' }>
                                                COMPLETE</option>
                                            <option value="CANCEL" ${param.status eq 'CANCEL' ? 'selected' : '' }>CANCEL
                                            </option>
                                            <option value="RETURN-REFUND" ${param.status eq 'RETURN-REFUND' ? 'selected'
                                                : '' }>RETURN-REFUND</option>
                                        </select>
                                        <button type="submit" class="btn btn-primary">Filter</button>
                                    </form>
                                </div> -->

                                <!-- Order Table -->
                                <div class="mt-5">
                                    <c:choose>
                                        <c:when test="${not empty orderDetails}">
                                            <table class="table table-striped table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th scope="col">Order ID</th>
                                                        <th scope="col">Tên khách hàng</th>
                                                        <th scope="col">Số lượng</th>
                                                        <th scope="col">Giá</th>
                                                        <!-- <th scope="col">Total</th> -->
                                                        <th scope="col">Hành động</th>

                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="order" items="${orders}">
                                                        <tr>
                                                            <td>${order.id}</td>
                                                            <td>${order.user.fullName}</td>

                                                            <c:set var="totalQuantity" value="0" />

                                                            <c:forEach var="orderDetail" items="${order.orderDetails}">
                                                                <c:if test="${orderDetail.product.shop.id == shopId}">
                                                                    <c:set var="totalQuantity"
                                                                        value="${totalQuantity + orderDetail.quantity}" />
                                                                </c:if>
                                                            </c:forEach>

                                                            <td>${totalQuantity}</td>


                                                            <c:set var="totalOrderPrice" value="0" />

                                                            <c:forEach var="orderDetail" items="${order.orderDetails}">
                                                                <c:if test="${orderDetail.product.shop.id == shopId}">
                                                                    <c:set var="totalOrderPrice"
                                                                        value="${totalOrderPrice + (orderDetail.price * orderDetail.quantity)}" />
                                                                </c:if>
                                                            </c:forEach>

                                                            <td>
                                                                <fmt:formatNumber type="number"
                                                                    value="${totalOrderPrice}" /> đ
                                                            </td>

                                                            <td>
                                                                <a href="/vendor/order/${order.id}"
                                                                    class="btn btn-success">
                                                                    <i class="bi bi-eye"></i>
                                                                </a>
                                                                <a href="/vendor/order/update/${order.id}"
                                                                    class="btn btn-warning mx-2">
                                                                    <i class="bi bi-pencil"></i>
                                                                </a>
                                                                <a href="/vendor/order/delete/${order.id}"
                                                                    class="btn btn-danger">
                                                                    <i class="bi bi-trash"></i>
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>


                                                </tbody>
                                            </table>

                                            <nav aria-label="Page navigation example">
                                                <ul class="pagination justify-content-center">
                                                    <li class="page-item ${currentPage eq 1 ? 'disabled' : ''}">
                                                        <a class="page-link"
                                                            href="/vendor/order?page=${currentPage - 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">&laquo;</span>
                                                            <span class="sr-only">Previous</span>
                                                        </a>
                                                    </li>

                                                    <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                                        <li
                                                            class="page-item ${loop.index eq currentPage ? 'active' : ''}">
                                                            <a class="page-link"
                                                                href="/vendor/order?page=${loop.index}">
                                                                ${loop.index}
                                                            </a>
                                                        </li>
                                                    </c:forEach>

                                                    <li
                                                        class="page-item ${currentPage eq totalPages ? 'disabled' : ''}">
                                                        <a class="page-link"
                                                            href="/vendor/order?page=${currentPage + 1}"
                                                            aria-label="Next">
                                                            <span aria-hidden="true">&raquo;</span>
                                                            <span class="sr-only">Next</span>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </nav>

                                        </c:when>
                                        <c:otherwise>
                                            <div class="alert alert-warning text-center">No orders available</div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>