<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Liên hệ - Mỹ phẩm XYZ</title>
    <link rel="stylesheet" href="./css/contact.css" />
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css"
    />
    <link href="<c:url value='/client/css/contact.css' />" rel="stylesheet">
</head>
<body>
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

<script src="js/main.js"></script>
</body>
</html>