# Barber App UI Design Proposal

## 1. Customer-Facing Pages

### a. Home / Dashboard
- Welcome message
- Quick links: Book Order, View Loyalty Points, Redeem Free Haircut (if eligible)
- Display next birthday discount eligibility

### b. Book Order Page
- Customer selects treatment (list of available treatments)
- Date/time picker (if appointments are supported)
- Show price, and if today is birthday, show discounted price
- If customer has 10+ points, show option to redeem for free haircut (disable birthday discount if redeeming)
- Confirm order button

### c. Loyalty Points Page
- Show current points
- List of past orders with points earned
- Progress bar toward next free haircut

### d. Order History Page
- List of all past orders
- Show which orders used birthday discount or redemption

### e. Redeem Free Haircut Page
- If eligible, allow redemption for haircut
- Show confirmation and updated points

---

## 2. Admin/Barber-Facing Pages (for future expansion)
- Manage Treatments (CRUD)
- View Sales Reports
- Customer Management

---

## 3. General UI/UX Considerations
- Responsive design (mobile-friendly)
- Clear feedback on actions (order placed, points updated, etc.)
- Use color coding for discounts, redemptions, and normal orders
- Simple navigation bar: Home, Book Order, Points, History

---

## 4. Technology Stack Recommendation
- Frontend: Thymeleaf (integrated with Spring Boot), Bootstrap for styling
- Forms for order creation, redemption, etc.
- Use Thymeleaf fragments for reusable components (navbar, footer, etc.)

---

*This proposal ensures a user-friendly, maintainable, and scalable UI for the barber app.*

