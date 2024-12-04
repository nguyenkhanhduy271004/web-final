<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Nguyễn Duy - Dự án laptopshop" />
                <meta name="author" content="Nguyễn Duy" />
                <title>Dashboard - Nguyễn Duy</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manage Products</h1>
                                <!-- <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/vendor">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Product</li>
                                </ol> -->
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between align-items-center">
                                                <h3 style="margin-right: 20px;">Table products</h3>
                                                <a href="/vendor/product/create" class="btn btn-primary">Create a
                                                    product</a>
                                            </div>
                                            <hr />

                                            <!-- Check if products exist -->
                                            <c:choose>
                                                <c:when test="${not empty products}">
                                                    <!-- Table for products -->
                                                    <table class="table table-bordered table-hover">
                                                        <thead>
                                                            <tr>
                                                                <th>ID</th>
                                                                <th>Name</th>
                                                                <th>Price</th>
                                                                <th>Factory</th>
                                                                <th>Action</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach var="product" items="${products}">
                                                                <tr>
                                                                    <th>${product.id}</th>
                                                                    <td>${product.name}</td>
                                                                    <td>
                                                                        <fmt:formatNumber type="number"
                                                                            value="${product.price}" /> đ
                                                                    </td>
                                                                    <td>${product.factory}</td>
                                                                    <td>
                                                                        <a href="/vendor/product/${product.id}"
                                                                            class="btn btn-success">View</a>
                                                                        <a href="/vendor/product/update/${product.id}"
                                                                            class="btn btn-warning mx-2">Update</a>
                                                                        <a href="/vendor/product/delete/${product.id}"
                                                                            class="btn btn-danger">Delete</a>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                    <nav aria-label="Page navigation example">
                                                        <ul class="pagination justify-content-center">
                                                            <li class="page-item ${currentPage eq 1 ? 'disabled' : ''}">
                                                                <a class="page-link"
                                                                    href="/vendor/product?page=${currentPage - 1}"
                                                                    aria-label="Previous">
                                                                    <span aria-hidden="true">&laquo;</span>
                                                                    <span class="sr-only">Previous</span>
                                                                </a>
                                                            </li>
                                                            <c:forEach begin="1" end="${totalPages}" varStatus="loop">
                                                                <li class="page-item">
                                                                    <a class="${loop.index eq currentPage ? 'active page-link' : 'page-link'}"
                                                                        href="/vendor/product?page=${loop.index}">${loop.index}
                                                                    </a>
                                                                </li>
                                                            </c:forEach>
                                                            <li
                                                                class="page-item ${currentPage eq totalPages ? 'disabled' : ''}">
                                                                <a class="page-link"
                                                                    href="/vendor/product?page=${currentPage + 1}"
                                                                    aria-label="Next">
                                                                    <span aria-hidden="true">&raquo;</span>
                                                                    <span class="sr-only">Next</span>
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </nav>
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- Display message when no products are available -->
                                                    <div class="alert alert-warning text-center mt-4">
                                                        Hiện tại không có sản phẩm.
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
                <script src="/client/js/main.js?version=1?></script>

            </body>

            </html>