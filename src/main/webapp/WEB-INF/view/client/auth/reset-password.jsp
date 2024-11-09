<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Reset Password</title>
            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                    font-family: Arial, sans-serif;
                }

                body {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    min-height: 100vh;
                    background: linear-gradient(135deg, #6a11cb, #2575fc);
                    color: #333;
                }

                .container {
                    background-color: #fff;
                    padding: 2rem;
                    border-radius: 10px;
                    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
                    text-align: center;
                    max-width: 400px;
                    width: 100%;
                }

                h2 {
                    font-size: 2rem;
                    color: #333;
                    margin-bottom: 0.5rem;
                }

                p {
                    color: #666;
                    margin-bottom: 1.5rem;
                    font-size: 1rem;
                }

                .error {
                    color: #d9534f;
                    font-size: 0.9rem;
                    margin-bottom: 1rem;
                }

                .input-group {
                    display: flex;
                    flex-direction: column;
                    margin-bottom: 1.5rem;
                }

                .input-group input {
                    padding: 0.8rem;
                    font-size: 1rem;
                    border: 1px solid #ddd;
                    border-radius: 5px;
                    transition: border-color 0.3s;
                    outline: none;
                }

                .input-group input:focus {
                    border-color: #6a11cb;
                }

                button {
                    background: linear-gradient(135deg, #6a11cb, #2575fc);
                    color: #fff;
                    padding: 0.8rem;
                    border: none;
                    border-radius: 5px;
                    font-size: 1rem;
                    cursor: pointer;
                    transition: transform 0.3s, box-shadow 0.3s;
                }

                button:hover {
                    transform: translateY(-3px);
                    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
                }
            </style>
        </head>

        <body>
            <div class="container">
                <h2>Reset Password</h2>
                <p>Enter the OTP sent to your email and set a new password.</p>

                <!-- Hiển thị lỗi chung nếu có -->
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>

                <form:form method="post" action="/reset-password" modelAttribute="resetPasswordForm">
                    <div class="input-group">
                        <form:input path="otp" placeholder="Enter OTP" required="true" />
                        <form:errors path="otp" cssClass="error" />
                    </div>
                    <div class="input-group">
                        <form:input path="newPassword" type="password" placeholder="Enter new password"
                            required="true" />
                        <form:errors path="newPassword" cssClass="error" />
                    </div>
                    <div class="input-group">
                        <form:input path="confirmPassword" type="password" placeholder="Confirm new password"
                            required="true" />
                        <form:errors path="confirmPassword" cssClass="error" />
                    </div>
                    <button type="submit">Reset Password</button>
                </form:form>
            </div>
        </body>

        </html>