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
    <style>
        /* Reset margin, padding và box-sizing */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Body */
        body {
            font-family: "Arial", sans-serif;
            background-color: #f3f3f3;
            color: #333;
            line-height: 1.6;
            padding: 0;
            margin: 0;
        }

        /* Header */
        header {
            background-color: #ff7b7b; /* Màu hồng nhẹ, phù hợp với mỹ phẩm */
            color: #fff;
            text-align: center;
            padding: 60px 0;
            border-bottom: 5px solid #f24c4c; /* Đường viền dưới tạo điểm nhấn */
        }

        header h1 {
            font-size: 36px;
            margin-bottom: 10px;
        }

        header p {
            font-size: 18px;
            font-weight: 300;
            margin-top: 10px;
        }

        /* Container */
        .container {
            width: 90%;
            max-width: 1200px;
            margin: 0 auto;
        }

        /* Contact Form Section */
        .contact-form {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            margin-top: 40px;
        }

        .contact-form h2 {
            font-size: 28px;
            margin-bottom: 20px;
            color: #ff7b7b;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            font-weight: bold;
            display: block;
            margin-bottom: 8px;
            color: #666;
        }

        /* Input fields and textarea */
        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 12px;
            border-radius: 8px;
            border: 1px solid #ddd;
            font-size: 16px;
            transition: border-color 0.3s;
        }

        .form-group input:focus,
        .form-group textarea:focus {
            border-color: #ff7b7b; /* Đổi màu viền khi focus vào */
            outline: none;
        }

        textarea {
            resize: vertical;
        }

        /* Button */
        button {
            background-color: #ff7b7b; /* Màu hồng */
            color: #fff;
            padding: 15px 30px;
            font-size: 18px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #f24c4c; /* Đổi màu khi hover */
        }

        button:active {
            background-color: #d74f4f;
        }

        /* Contact Information Section */
        .contact-info {
            margin-top: 50px;
            text-align: center;
        }

        .contact-info h3 {
            font-size: 24px;
            color: #ff7b7b;
            margin-bottom: 20px;
        }

        .contact-info p {
            font-size: 16px;
            color: #666;
            margin-bottom: 10px;
        }

        .contact-info a {
            color: #ff7b7b;
            text-decoration: none;
        }

        .contact-info a:hover {
            text-decoration: underline;
        }

        /* Footer */
        footer {
            background-color: #333;
            color: #fff;
            padding: 20px 0;
            text-align: center;
            margin-top: 40px;
        }

        footer p {
            font-size: 16px;
        }
    </style>
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