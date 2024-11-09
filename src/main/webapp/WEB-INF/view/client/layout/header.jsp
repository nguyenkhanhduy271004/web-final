<%@ page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />

        <div class="fixed-top">
            <div class="bg-gradient">
                <div class="menu-content d-flex justify-content-center">
                    <div class="menu-item">Freeship 15K mọi đơn hàng</div>
                    <div class="separator mx-2">|</div>
                    <div class="menu-item">Mua trước trả sau 0%</div>
                    <div class="separator mx-2">|</div>
                    <div class="menu-item">Mua online nhận tại cửa hàng gần nhất</div>
                </div>
            </div>
            <div class="container px-0">

                <nav class="navbar navbar-light bg-white navbar-expand-xl">
                    <a href="/" class="navbar-brand">
                        <img src="<c:url value='/client/img/imgThuonghieu/4.png' />" alt="Shop logo"
                            style="width: 70px;" />
                        <!-- <h1 class="text-primary display-6">Cosmetic Shop</h1> -->
                    </a>
                    <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarCollapse">
                        <span class="fa fa-bars text-primary"></span>
                    </button>
                    <div class="collapse navbar-collapse bg-white justify-content-between mx-5" id="navbarCollapse">

                        <!-- Navigation Links -->
                        <div class="navbar-nav">
                            <a href="/" class="nav-item nav-link active" style="color: #000;">Home</a>
                            <a href="/about" class="nav-item nav-link" style="color: #000;">About</a>
                            <a href="/specials" class="nav-item nav-link" style="color: #000;">Specials</a>
                            <a href="/new" class="nav-item nav-link" style="color: #000;">New</a>
                            <a href="/contact" class="nav-item nav-link" style="color: #000;">Contact</a>
                        </div>

                        <!-- Search Box -->
                        <form class="search-form d-flex mx-3" action="/search" method="get">
                            <input type="text" name="query" class="form-control search-test"
                                placeholder="Top 3 kem chống nắng HOT nhất" />
                            <button type="submit" class="btn search-button">
                                <i class="fas fa-search"></i>
                            </button>
                        </form>

                        <!-- Login and Cart Section -->
                        <div class="d-flex m-3 me-0">
                            <c:if test="${not empty pageContext.request.userPrincipal}">
                                <a href="/cart" class="position-relative my-auto me-4">
                                    <i class="fa fa-shopping-bag fa-2x" style="color: #e7456f;"></i>
                                    <span
                                        class="position-absolute bg-secondary rounded-circle d-flex align-items-center justify-content-center text-dark px-1"
                                        style="top: -5px; left: 15px; height: 20px; min-width: 20px;" id="sumCart">
                                        ${sessionScope.sum}
                                    </span>
                                </a>
                                <div class="dropdown my-auto">
                                    <a href="#" class="dropdown" role="button" id="dropdownMenuLink"
                                        data-bs-toggle="dropdown" aria-expanded="false" data-bs-toggle="dropdown"
                                        aria-expanded="false">
                                        <i class="fas fa-user fa-2x" style="color: #e7456f;"></i>
                                    </a>

                                    <ul class="dropdown-menu dropdown-menu-end p-4" aria-labelledby="dropdownMenuLink">
                                        <li class="d-flex align-items-center flex-column" style="min-width: 300px;">
                                            <img style="width: 150px; height: 150px; border-radius: 50%; overflow: hidden;"
                                                src="<c:url value='/client/img/avatar/5.png' />" alt="Avatar logo" />
                                            <div class="text-center my-3">
                                                <c:out value="${sessionScope.fullName}" />
                                            </div>
                                        </li>

                                        <li><a class="dropdown-item" href="#">Quản lý tài khoản</a></li>

                                        <li><a class="dropdown-item" href="/order-history">Lịch sử mua hàng</a></li>
                                        <li><a class="dropdown-item" href="/user/wishlist">Sản phẩm yêu thích</a></li>
                                        <li>
                                            <hr class="dropdown-divider">
                                        </li>
                                        <li>
                                            <form method="post" action="/logout">
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                    value="${_csrf.token}" />
                                                <button class="dropdown-item">Đăng xuất</button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </c:if>
                            <c:if test="${empty pageContext.request.userPrincipal}">
                                <a href="/login" class="a-login position-relative my-auto">
                                    Đăng nhập
                                </a>
                            </c:if>
                        </div>
                    </div>
                </nav>
            </div>
        </div>