<%@ page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="Nguyễn Duy - Dự án cosmetic shop" />
            <meta name="author" content="Nguyễn Duy" />
            <title>Dashboard - Quản lý doanh thu</title>
            <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
            <link href="css/styles.css" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f6f9;
                    color: #333;
                }

                .card {
                    border-radius: 10px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    transition: transform 0.2s ease-in-out;
                }

                .card:hover {
                    transform: translateY(-5px);
                }

                .card-body {
                    font-size: 1.2em;
                    font-weight: 600;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    height: 100px;
                }

                .card-footer {
                    background-color: rgba(255, 255, 255, 0.2);
                    border-top: 1px solid rgba(255, 255, 255, 0.1);
                }

                .stretched-link {
                    font-weight: bold;
                }

                .container-fluid {
                    padding: 2em;
                }

                .breadcrumb {
                    background: none;
                    font-size: 1.1em;
                    font-weight: 500;
                }

                .breadcrumb-item a {
                    color: #007bff;
                }

                .breadcrumb-item.active {
                    color: #6c757d;
                }
            </style>
        </head>

        <body class="sb-nav-fixed">
            <jsp:include page="../layout/header.jsp" />
            <div id="layoutSidenav">
                <jsp:include page="../layout/sidebar.jsp" />
                <div id="layoutSidenav_content">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 class="mt-4">Dashboard - Quản lý doanh thu</h1>
                            <ol class="breadcrumb mb-4">
                                <li class="breadcrumb-item active">Thống kê doanh thu</li>
                            </ol>
                            <div class="row">
                                <div class="col-xl-3 col-md-6">
                                    <div class="card bg-info text-white mb-4">
                                        <div class="card-body">Tổng Doanh Thu: ${totalRevenue} VND</div>
                                        <div class="card-footer d-flex align-items-center justify-content-between">
                                            <a class="small text-white stretched-link" href="#">Xem chi tiết</a>
                                            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-md-6">
                                    <div class="card bg-primary text-white mb-4">
                                        <div class="card-body">Số lượng khách hàng: ${countUsers}</div>
                                        <div class="card-footer d-flex align-items-center justify-content-between">
                                            <a class="small text-white stretched-link" href="/vendor/user">Xem chi
                                                tiết</a>
                                            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-md-6">
                                    <div class="card bg-danger text-white mb-4">
                                        <div class="card-body">Số lượng sản phẩm: ${countProducts}</div>
                                        <div class="card-footer d-flex align-items-center justify-content-between">
                                            <a class="small text-white stretched-link" href="/vendor/product">Xem chi
                                                tiết</a>
                                            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xl-3 col-md-6">
                                    <div class="card bg-success text-white mb-4">
                                        <div class="card-body">Số lượng đơn hàng: ${countOrders}</div>
                                        <div class="card-footer d-flex align-items-center justify-content-between">
                                            <a class="small text-white stretched-link" href="/vendor/order">Xem chi
                                                tiết</a>
                                            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                        </div>
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
            <script src="js/scripts.js"></script>
        </body>

        </html>