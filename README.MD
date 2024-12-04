Web Commerce - Cosmetics
A Spring MVC-based e-commerce web application for a cosmetics store.

Features
Customer
Product Catalog: Browse products by category, filter by brand, price, rating.
Shopping Cart & Checkout: Thêm vào giỏ, adjust items, and place orders.
User Management: Register, log in, view order history.
Admin
Product Management: Add, edit, and delete products.
Order Management: View and update orders.
Tech Stack
Backend: Spring MVC, Spring Boot, Spring Data JPA, Spring Security
Frontend: JSP, Bootstrap, AJAX
Database: MySQL
Installation
Clone the Repository:

bash
Copy code
git clone https://github.com/yourusername/web-commerce-cosmetics.git
cd web-commerce-cosmetics
Configure Database: Update application.properties with your MySQL details.

Run the Project:

bash
Copy code
mvn spring-boot:run
Project Structure
Controllers: Handle requests
Models: Database entities
Repositories: Data access
Services: Business logic
Key Endpoints
Home: /
Product Details: /product/{id}
Cart & Checkout: /cart, /checkout
Admin: /admin
