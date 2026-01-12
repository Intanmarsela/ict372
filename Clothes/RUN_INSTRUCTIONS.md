# E-Commerce Android App - Run Instructions

## Project Overview
This is a complete E-Commerce Android app built with Java and XML, featuring:
- User Registration & Login
- Product Browsing & Search
- Shopping Cart
- Checkout & Order Management
- Purchase History (last 6 months)

## Package Structure
All code is in: `com.example.ecommerceapp`

## How to Run

### 1. Open Project
- Open the project in Android Studio
- Wait for Gradle sync to complete
- Ensure Gson dependency is included (already added in build.gradle.kts)

### 2. Build & Run
- Connect an Android device or start an emulator (API 35+)
- Click Run or press Shift+F10
- The app will install and launch

### 3. Test Flow

#### Initial Setup
1. **LoginActivity** (Launcher) appears first
2. Click "Don't have an account? Register" to go to RegisterActivity
3. Register a new user:
   - Full Name: e.g., "John Doe"
   - Email: e.g., "john@example.com"
   - Password: e.g., "password123"
   - Confirm Password: same as password
4. After registration, you'll be redirected to LoginActivity
5. Login with your credentials

#### Shopping Flow
1. **HomeActivity** shows 14 seeded products in a scrollable list
2. **Search**: Click the search bar to open SearchActivity
   - Type product name to see type-ahead suggestions
   - Tap a suggestion to view product details
3. **Browse Products**: Tap any product card to open ProductDetailActivity
4. **Product Detail**: 
   - View product info
   - Adjust quantity with +/- buttons
   - Click "Add to Cart"
5. **Cart**: Click cart icon (top right) to view CartActivity
   - Update quantities or remove items
   - View total price
   - Click "Place Order" to checkout
6. **Checkout**: Fill in shipping details and place order
7. **Purchase History**: Access via menu (3 dots) -> Purchase History
   - Shows only orders from last 6 months

## Navigation Map
- RegisterActivity → LoginActivity (after registration)
- LoginActivity → HomeActivity (on login success)
- LoginActivity → RegisterActivity (create account link)
- HomeActivity → ProductDetailActivity (tap product, EXTRA_PRODUCT_ID)
- HomeActivity → SearchActivity (tap search bar)
- HomeActivity → CartActivity (tap cart icon)
- HomeActivity → PurchaseHistoryActivity (menu)
- SearchActivity → ProductDetailActivity (tap suggestion, EXTRA_PRODUCT_ID)
- CartActivity → CheckoutActivity
- CheckoutActivity → PurchaseHistoryActivity (after order)

## Data Persistence
- All data persists using SharedPreferences with JSON (Gson)
- Data survives app restart:
  - Registered users
  - Current logged-in user
  - Shopping cart
  - Orders
- Products are seeded on first launch (14 products)

## Key Features
- ✅ Type-ahead search with real-time filtering
- ✅ Shopping cart with quantity management
- ✅ Order creation with timestamp
- ✅ Purchase history filtered to last 6 months
- ✅ Clean, consistent UI with Material Design
- ✅ One Activity per screen
- ✅ Intent extras for navigation (EXTRA_PRODUCT_ID, EXTRA_ORDER_ID)

## Notes
- Images use placeholder drawables (android.R.drawable.ic_menu_gallery)
- In production, replace with actual image loading library (Glide/Picasso)
- All prices are in USD format
- Cart count badge appears when cart has items
