<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>OTP Verification</title>
        <style>
            /* Reset CSS */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
                font-family: Arial, sans-serif;
            }

            /* Background styling */
            body {
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
                background: linear-gradient(135deg, #6a11cb, #2575fc);
                color: #333;
            }

            /* Card styling */
            .verification-container {
                background-color: #fff;
                padding: 2rem;
                border-radius: 8px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
                text-align: center;
                max-width: 400px;
                width: 100%;
            }

            /* Title styling */
            h2 {
                font-size: 1.8rem;
                color: #333;
                margin-bottom: 1rem;
            }

            /* Input field styling */
            .input-group {
                display: flex;
                flex-direction: column;
                margin-bottom: 1.5rem;
            }

            .input-group input {
                padding: 0.8rem;
                font-size: 1rem;
                border: 1px solid #ddd;
                border-radius: 4px;
                transition: border-color 0.3s;
                outline: none;
            }

            .input-group input:focus {
                border-color: #6a11cb;
            }

            /* Button styling */
            button {
                background: linear-gradient(135deg, #6a11cb, #2575fc);
                color: #fff;
                padding: 0.8rem;
                border: none;
                border-radius: 4px;
                font-size: 1rem;
                cursor: pointer;
                transition: transform 0.3s, box-shadow 0.3s;
            }

            button:hover {
                transform: translateY(-3px);
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            }

            /* Error message styling */
            .error {
                color: #d9534f;
                font-size: 0.9rem;
                margin-top: 0.5rem;
            }
        </style>
    </head>

    <body>
        <div class="verification-container">
            <h2>OTP Verification</h2>
            <p>Please enter the OTP sent to your email.</p>

            <form:form method="post" action="/verify-otp" modelAttribute="otpForm">
                <div class="input-group">
                    <form:input path="otp" placeholder="Enter OTP" required="true" />
                    <form:errors path="otp" cssClass="error" />
                </div>
                <button type="submit">Verify OTP</button>
            </form:form>
        </div>
    </body>

    </html>