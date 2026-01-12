# Step-by-Step Visual Upgrade Guide
## Matching Figma Design to Your eCommerce App

---

## A) QUICK AUDIT - Visual Gaps Found

### Current State vs Figma Design:

**✅ Already Good:**
- Colors match (Primary #6750A4, Secondary #EADDFF, Background #FAFAFA)
- Card corner radius 12dp ✓
- Gradient buttons ✓
- Basic layout structure ✓

**❌ Needs Improvement:**
1. **Login/Register:** Input fields not pill-shaped (currently 16dp radius, need ~28dp for pill)
2. **Login:** Logo needs elevation/shadow (currently flat)
3. **Home:** Missing app icon in toolbar (only text "ShopHub")
4. **Home:** Search bar should be pill-shaped with light grey background (currently white card)
5. **Product Detail:** Product info section needs white card container (currently just text on background)
6. **Product Detail:** Quantity selector buttons need better styling (currently using ChipGrey style)
7. **Checkout:** Input fields should use TextInputLayout with rounded white backgrounds (currently plain EditText)
8. **Purchase History:** Status badges need color variants (Processing = yellow, Shipped = blue, Delivered = green)
9. **Purchase History:** Order card layout needs refinement (spacing, divider placement)
10. **Search:** Dropdown suggestions need better card styling
11. **Cart:** Cart badge needs proper Material BadgeDrawable (currently TextView)
12. **Typography:** Need to ensure Material default text styles are used consistently

---

## B) STEP-BY-STEP INSTRUCTIONS

### STEP 1: Theme + Colors

**File:** `app/src/main/res/values/colors.xml`

**Action:** Verify colors match Figma exactly. Colors are already correct, but add status badge colors:

**Add these lines after line 21:**
```xml
    <!-- Status Badge Colors -->
    <color name="status_processing_bg">#FFF9E6</color>
    <color name="status_processing_text">#D97706</color>
    <color name="status_shipped_bg">#E0F2FE</color>
    <color name="status_shipped_text">#0369A1</color>
```

**File:** `app/src/main/res/values/themes.xml`

**Action:** Ensure theme uses Material Components properly. Already correct ✓

**Verification:** Run app, check that colors match Figma palette.

---

### STEP 2: Typography Defaults (Material)

**File:** `app/src/main/res/values/themes.xml`

**Action:** Add Material typography defaults to theme:

**Add inside `Base.Theme.ShopHub` style (after line 11, before closing tag):**
```xml
        <item name="textAppearanceHeadline1">@style/TextAppearance.MaterialComponents.Headline1</item>
        <item name="textAppearanceHeadline2">@style/TextAppearance.MaterialComponents.Headline2</item>
        <item name="textAppearanceBody1">@style/TextAppearance.MaterialComponents.Body1</item>
        <item name="textAppearanceBody2">@style/TextAppearance.MaterialComponents.Body2</item>
        <item name="textAppearanceButton">@style/TextAppearance.MaterialComponents.Button</item>
```

**Verification:** Text should use Material default sizes/weights automatically.

---

### STEP 3: Spacing System (16dp base) + Common Dimens

**File:** `app/src/main/res/values/dimens.xml` (CREATE NEW FILE)

**Action:** Create dimens.xml with consistent spacing:

**Full file content:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Base Spacing -->
    <dimen name="spacing_base">16dp</dimen>
    <dimen name="spacing_small">8dp</dimen>
    <dimen name="spacing_medium">16dp</dimen>
    <dimen name="spacing_large">24dp</dimen>
    <dimen name="spacing_xlarge">32dp</dimen>
    
    <!-- Input Padding -->
    <dimen name="input_padding_horizontal">14dp</dimen>
    <dimen name="input_padding_vertical">14dp</dimen>
    
    <!-- Button Padding -->
    <dimen name="button_padding_vertical">16dp</dimen>
    
    <!-- Card Radius -->
    <dimen name="card_corner_radius">12dp</dimen>
    
    <!-- Button Radius -->
    <dimen name="button_radius_secondary">12dp</dimen>
    <dimen name="button_radius_pill">999dp</dimen>
    
    <!-- Elevations -->
    <dimen name="elevation_card">4dp</dimen>
    <dimen name="elevation_button">8dp</dimen>
    <dimen name="elevation_toolbar">2dp</dimen>
</resources>
```

**Verification:** File created, ready to use in layouts.

---

### STEP 4: Shapes (12dp cards, pill buttons)

**File:** `app/src/main/res/drawable/bg_input_pill.xml` (CREATE NEW FILE)

**Action:** Create pill-shaped input background for login/register:

**Full file content:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/white" />
    <stroke
        android:width="1dp"
        android:color="@color/divider_border" />
    <corners android:radius="28dp" />
</shape>
```

**File:** `app/src/main/res/drawable/bg_input_pill_filled.xml` (CREATE NEW FILE)

**Action:** Create filled pill-shaped input for email field:

**Full file content:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/light_field_blue_tint" />
    <corners android:radius="28dp" />
</shape>
```

**File:** `app/src/main/res/drawable/bg_search_pill.xml`

**Action:** Update to match Figma - light grey pill shape:

**Replace entire file with:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#EFEFEF" />
    <corners android:radius="28dp" />
</shape>
```

**Verification:** Drawables created, ready to use.

---

### STEP 5: Drawables (gradient, rounded backgrounds, input focus, dividers)

**File:** `app/src/main/res/drawable/bg_logo_gradient.xml`

**Action:** Add elevation shadow effect. Logo gradient is correct, but we need elevation in layout.

**File:** `app/src/main/res/drawable/bg_button_disabled.xml` (CREATE NEW FILE)

**Action:** Create disabled button state for login button:

**Full file content:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#E0E0E0" />
    <corners android:radius="999dp" />
</shape>
```

**File:** `app/src/main/res/drawable/bg_status_processing.xml` (CREATE NEW FILE)

**Action:** Create Processing status badge background:

**Full file content:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/status_processing_bg" />
    <corners android:radius="16dp" />
</shape>
```

**File:** `app/src/main/res/drawable/bg_status_shipped.xml` (CREATE NEW FILE)

**Action:** Create Shipped status badge background:

**Full file content:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/status_shipped_bg" />
    <corners android:radius="16dp" />
</shape>
```

**File:** `app/src/main/res/drawable/bg_quantity_button.xml` (CREATE NEW FILE)

**Action:** Create quantity selector button background (light grey, rounded):

**Full file content:**
```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="#F5F5F5" />
    <corners android:radius="8dp" />
</shape>
```

**Verification:** All drawables created.

---

### STEP 6: Toolbar/Header + Icon Buttons + Cart Badge

**File:** `app/src/main/res/layout/activity_home.xml`

**Action:** Add app icon to toolbar (left side, before "ShopHub" text):

**Find this section (around line 35-43):**
```xml
                <!-- App Name -->
                <TextView
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:text="ShopHub"
                    android:textSize="20sp"
                    android:textStyle="bold"
                    android:textColor="@color/text_primary" />
```

**Replace with:**
```xml
                <!-- App Icon -->
                <ImageView
                    android:layout_width="32dp"
                    android:layout_height="32dp"
                    android:src="@drawable/ic_app_logo"
                    android:background="@drawable/bg_logo_gradient"
                    android:padding="6dp"
                    android:layout_marginEnd="8dp"
                    android:contentDescription="ShopHub Logo" />

                <!-- App Name -->
                <TextView
                    android:layout_width="0dp"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    android:text="ShopHub"
                    android:textSize="20sp"
                    android:textStyle="bold"
                    android:textColor="@color/text_primary" />
```

**Action:** Update search bar to use pill background:

**Find this section (around line 108-153):**
```xml
    <!-- Search Bar Card -->
    <com.google.android.material.card.MaterialCardView
        android:id="@+id/cardSearch"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginEnd="16dp"
        android:layout_marginTop="16dp"
        app:cardCornerRadius="12dp"
        app:cardElevation="2dp"
        app:cardBackgroundColor="@color/white"
```

**Replace with:**
```xml
    <!-- Search Bar Card -->
    <com.google.android.material.card.MaterialCardView
        android:id="@+id/cardSearch"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginEnd="16dp"
        android:layout_marginTop="16dp"
        app:cardCornerRadius="28dp"
        app:cardElevation="0dp"
        app:cardBackgroundColor="#EFEFEF"
        app:strokeWidth="0dp"
```

**Action:** Update cart badge to use proper Material BadgeDrawable:

**File:** `app/src/main/java/com/example/ecommerceapp/activities/HomeActivity.java`

**Add import at top:**
```java
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
```

**In `updateCartBadge()` method, replace the TextView badge logic with:**
```java
private void updateCartBadge() {
    int cartCount = cartManager.getTotalQuantity();
    if (cartCount > 0) {
        // Use Material BadgeDrawable
        BadgeDrawable badge = BadgeDrawable.create(this);
        badge.setNumber(cartCount);
        badge.setBackgroundColor(getResources().getColor(R.color.primary, null));
        BadgeUtils.attachBadgeDrawable(badge, findViewById(R.id.btnCart), null);
        tvCartBadge.setVisibility(View.GONE); // Hide TextView badge
    } else {
        tvCartBadge.setVisibility(View.GONE);
    }
}
```

**Note:** If BadgeUtils is not available, keep TextView approach but style it better.

**Verification:** 
- App icon appears in toolbar
- Search bar is pill-shaped with light grey background
- Cart badge shows count properly

---

### STEP 7: Product Cards (Grid) + Images + Ripple

**File:** `app/src/main/res/layout/item_product_card.xml`

**Action:** Ensure card styling matches Figma exactly:

**Current card is good, but verify these attributes:**
- `app:cardCornerRadius="12dp"` ✓
- `app:cardElevation="4dp"` ✓
- `android:foreground="?android:attr/selectableItemBackground"` ✓ (ripple effect)

**Action:** Update product price color to match Figma (should be text_secondary, not primary):

**Find this (around line 51-59):**
```xml
            <!-- Price -->
            <TextView
                android:id="@+id/tvProductPrice"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="4dp"
                android:textSize="18sp"
                android:textStyle="bold"
                android:textColor="@color/primary"
                tools:text="$129.99" />
```

**Replace with:**
```xml
            <!-- Price -->
            <TextView
                android:id="@+id/tvProductPrice"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="4dp"
                android:textSize="16sp"
                android:textColor="@color/text_secondary"
                tools:text="$129.99" />
```

**Verification:** Product cards show price in grey, not purple.

---

### STEP 8: Detail Page Quantity Selector + Sticky Bottom CTA

**File:** `app/src/main/res/layout/activity_product_detail.xml`

**Action:** Add white card container for product info section:

**Find this section (around line 60-101):**
```xml
            <!-- Product Name -->
            <TextView
                android:id="@+id/tvProductName"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="24dp"
                android:textSize="24sp"
                android:textStyle="bold"
                android:textColor="@color/text_primary"
                tools:text="Wireless Headphones" />

            <!-- Product Price -->
            <TextView
                android:id="@+id/tvProductPrice"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:textSize="28sp"
                android:textStyle="bold"
                android:textColor="@color/primary_purple"
                tools:text="$79.99" />

            <!-- Description Label -->
            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="24dp"
                android:text="Description"
                android:textSize="18sp"
                android:textStyle="bold"
                android:textColor="@color/text_primary" />

            <!-- Description Text -->
            <TextView
                android:id="@+id/tvProductDescription"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:textSize="16sp"
                android:textColor="@color/text_secondary"
                android:lineSpacingExtra="4dp"
                tools:text="Premium wireless headphones with noise cancellation and 30-hour battery life" />
```

**Replace with (wrap in MaterialCardView):**
```xml
            <!-- Product Info Card -->
            <com.google.android.material.card.MaterialCardView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="24dp"
                app:cardCornerRadius="12dp"
                app:cardElevation="2dp"
                app:cardBackgroundColor="@color/white"
                app:strokeWidth="0dp">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="vertical"
                    android:padding="16dp">

                    <!-- Product Name -->
                    <TextView
                        android:id="@+id/tvProductName"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:textSize="24sp"
                        android:textStyle="bold"
                        android:textColor="@color/text_primary"
                        tools:text="Wireless Headphones" />

                    <!-- Product Price -->
                    <TextView
                        android:id="@+id/tvProductPrice"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="8dp"
                        android:textSize="28sp"
                        android:textStyle="bold"
                        android:textColor="@color/primary"
                        tools:text="$129.99" />

                    <!-- Description Label -->
                    <TextView
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="24dp"
                        android:text="Description"
                        android:textSize="18sp"
                        android:textStyle="bold"
                        android:textColor="@color/text_primary" />

                    <!-- Description Text -->
                    <TextView
                        android:id="@+id/tvProductDescription"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="8dp"
                        android:textSize="16sp"
                        android:textColor="@color/text_secondary"
                        android:lineSpacingExtra="4dp"
                        tools:text="Premium wireless headphones with noise cancellation and 30-hour battery life" />

                </LinearLayout>

            </com.google.android.material.card.MaterialCardView>
```

**Action:** Update quantity selector buttons to match Figma (light grey background):

**Find this section (around line 121-147):**
```xml
                <Button
                    android:id="@+id/btnDecrease"
                    android:layout_width="48dp"
                    android:layout_height="48dp"
                    android:text="-"
                    android:textSize="20sp"
                    style="@style/ChipGrey" />
```

**Replace both buttons with:**
```xml
                <Button
                    android:id="@+id/btnDecrease"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:text="-"
                    android:textSize="18sp"
                    android:background="@drawable/bg_quantity_button"
                    android:textColor="@color/text_primary"
                    android:padding="0dp"
                    android:minWidth="0dp"
                    android:minHeight="0dp" />
```

**Do the same for btnIncrease.**

**Action:** Update toolbar background to white (currently background color):

**Find this (around line 14):**
```xml
        android:background="@color/background"
```

**Replace with:**
```xml
        android:background="@color/white"
```

**Verification:**
- Product info is in white card
- Quantity buttons are light grey, rounded
- Toolbar is white

---

### STEP 9: Cart List Item Design + Totals Bottom Sheet Style

**File:** `app/src/main/res/layout/item_cart_row.xml`

**Action:** Verify cart item styling matches Figma. Current design is good ✓

**File:** `app/src/main/res/layout/activity_cart.xml`

**Action:** Ensure bottom summary card styling is correct. Already good ✓

**Verification:** Cart items display correctly with quantity controls.

---

### STEP 10: Checkout Form Styling (TextInputLayout)

**File:** `app/src/main/res/layout/activity_checkout.xml`

**Action:** Replace plain EditText with TextInputLayout for better Material styling:

**Find the Full Name input section (around line 50-71):**
```xml
            <!-- Full Name Label -->
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Full Name"
                android:textSize="16sp"
                android:textStyle="bold"
                android:textColor="@color/text_primary" />

            <!-- Full Name Input -->
            <EditText
                android:id="@+id/etFullName"
                android:layout_width="match_parent"
                android:layout_height="56dp"
                android:layout_marginTop="8dp"
                android:hint="Enter your full name"
                android:inputType="textPersonName"
                android:background="@drawable/bg_input_rounded_white"
                android:textSize="16sp"
                android:textColor="@color/text_primary"
                android:paddingStart="16dp"
                android:paddingEnd="16dp" />
```

**Replace with:**
```xml
            <!-- Full Name Input -->
            <com.google.android.material.textfield.TextInputLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="20dp"
                android:hint="Full Name"
                style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
                app:boxCornerRadiusTopStart="12dp"
                app:boxCornerRadiusTopEnd="12dp"
                app:boxCornerRadiusBottomStart="12dp"
                app:boxCornerRadiusBottomEnd="12dp"
                app:boxStrokeColor="@color/divider_border"
                app:hintTextColor="@color/text_primary">

                <com.google.android.material.textfield.TextInputEditText
                    android:id="@+id/etFullName"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:inputType="textPersonName"
                    android:textSize="16sp"
                    android:textColor="@color/text_primary"
                    android:paddingStart="14dp"
                    android:paddingEnd="14dp"
                    android:paddingTop="14dp"
                    android:paddingBottom="14dp" />

            </com.google.android.material.textfield.TextInputLayout>
```

**Repeat for Address, Phone, and Email fields (use same pattern).**

**For Address field, add:**
```xml
                    android:minLines="3"
                    android:maxLines="5"
                    android:gravity="top|start"
```

**Action:** Update toolbar background to white:

**Find (around line 14):**
```xml
        android:background="@color/background"
```

**Replace with:**
```xml
        android:background="@color/white"
```

**File:** `app/src/main/java/com/example/ecommerceapp/activities/CheckoutActivity.java`

**Action:** Update findViewById calls to get EditText from TextInputLayout:

**Find:**
```java
        etFullName = findViewById(R.id.etFullName);
```

**Replace with:**
```java
        TextInputLayout fullNameLayout = findViewById(R.id.etFullName);
        etFullName = fullNameLayout.getEditText();
```

**Do the same for etAddress, etPhone, etEmail.**

**Add import:**
```java
import com.google.android.material.textfield.TextInputLayout;
```

**Verification:**
- Inputs use TextInputLayout with rounded corners
- Form looks more Material Design compliant

---

### STEP 11: Search Type-Ahead Dropdown Styling

**File:** `app/src/main/res/layout/activity_search.xml`

**Action:** Update search input to match Figma (pill-shaped, light grey):

**Find the search card section (around line 62-88):**
```xml
        <!-- Search Input Card -->
        <com.google.android.material.card.MaterialCardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:cardCornerRadius="12dp"
            app:cardElevation="2dp"
            app:cardBackgroundColor="@color/white">
```

**Replace with:**
```xml
        <!-- Search Input Card -->
        <com.google.android.material.card.MaterialCardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:cardCornerRadius="28dp"
            app:cardElevation="0dp"
            app:cardBackgroundColor="#EFEFEF"
            app:strokeWidth="0dp">
```

**File:** `app/src/main/res/layout/item_suggestion_row.xml`

**Action:** Ensure suggestion cards match Figma styling. Current is good ✓

**Verification:** Search bar is pill-shaped with light grey background.

---

### STEP 12: Purchase History Card Styling + Order Detail Dialog

**File:** `app/src/main/res/layout/item_order_row.xml`

**Action:** Update status badge to support different colors based on status:

**Find the status badge (around line 44-57):**
```xml
            <!-- Status Badge -->
            <TextView
                android:id="@+id/tvStatus"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Delivered"
                android:textSize="12sp"
                android:textStyle="bold"
                android:background="@drawable/bg_status_delivered"
                android:textColor="@color/success_text"
                android:paddingStart="12dp"
                android:paddingEnd="12dp"
                android:paddingTop="6dp"
                android:paddingBottom="6dp"
                tools:text="Delivered" />
```

**Keep as is - we'll handle colors in Java.**

**File:** `app/src/main/java/com/example/ecommerceapp/adapters/OrderAdapter.java`

**Action:** Update bind method to set status badge colors dynamically:

**Find the bind method (around line 77-82):**
```java
        void bind(Order order) {
            tvOrderId.setText(order.getFormattedOrderId());
            tvOrderDate.setText(dateFormat.format(order.getTimestamp()));
            tvOrderTotal.setText(String.format("$%.2f", order.getTotal()));
            tvStatus.setText(order.getStatus() != null ? order.getStatus() : "Delivered");
        }
```

**Replace with:**
```java
        void bind(Order order) {
            tvOrderId.setText(order.getFormattedOrderId());
            tvOrderDate.setText(dateFormat.format(order.getTimestamp()));
            tvOrderTotal.setText(String.format("$%.2f", order.getTotal()));
            
            String status = order.getStatus() != null ? order.getStatus() : "Delivered";
            tvStatus.setText(status);
            
            // Set status badge colors based on status
            if ("Processing".equalsIgnoreCase(status)) {
                tvStatus.setBackgroundResource(R.drawable.bg_status_processing);
                tvStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.status_processing_text, null));
            } else if ("Shipped".equalsIgnoreCase(status)) {
                tvStatus.setBackgroundResource(R.drawable.bg_status_shipped);
                tvStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.status_shipped_text, null));
            } else { // Delivered
                tvStatus.setBackgroundResource(R.drawable.bg_status_delivered);
                tvStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.success_text, null));
            }
        }
```

**Action:** Update order date format to match Figma ("Jan 10, 2026"):

**Find the dateFormat initialization (around line 34):**
```java
        this.dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
```

**This is already correct ✓**

**File:** `app/src/main/res/layout/dialog_order_detail.xml`

**Action:** Verify dialog layout matches Figma. Already created ✓

**Verification:**
- Status badges show correct colors (Processing = yellow, Shipped = blue, Delivered = green)
- Order date format matches Figma
- Dialog shows order details when card is tapped

---

## C) FILE CHANGES SUMMARY

### Files to CREATE:
1. `app/src/main/res/values/dimens.xml`
2. `app/src/main/res/drawable/bg_input_pill.xml`
3. `app/src/main/res/drawable/bg_input_pill_filled.xml`
4. `app/src/main/res/drawable/bg_button_disabled.xml`
5. `app/src/main/res/drawable/bg_status_processing.xml`
6. `app/src/main/res/drawable/bg_status_shipped.xml`
7. `app/src/main/res/drawable/bg_quantity_button.xml`

### Files to MODIFY:
1. `app/src/main/res/values/colors.xml` - Add status colors
2. `app/src/main/res/values/themes.xml` - Add typography
3. `app/src/main/res/drawable/bg_search_pill.xml` - Update to light grey
4. `app/src/main/res/layout/activity_home.xml` - Add app icon, update search bar
5. `app/src/main/res/layout/item_product_card.xml` - Update price color
6. `app/src/main/res/layout/activity_product_detail.xml` - Add white card, update quantity buttons, toolbar
7. `app/src/main/res/layout/activity_checkout.xml` - Use TextInputLayout, white toolbar
8. `app/src/main/res/layout/activity_search.xml` - Update search bar styling
9. `app/src/main/res/layout/item_order_row.xml` - Already good, colors handled in Java
10. `app/src/main/java/com/example/ecommerceapp/activities/HomeActivity.java` - Update cart badge (optional)
11. `app/src/main/java/com/example/ecommerceapp/activities/CheckoutActivity.java` - Update to use TextInputLayout
12. `app/src/main/java/com/example/ecommerceapp/adapters/OrderAdapter.java` - Add status badge color logic

---

## D) FINAL VISUAL CHECKLIST

### ✅ Colors
- [ ] Primary #6750A4 used for buttons, links, accents
- [ ] Secondary #EADDFF used for quantity buttons, icon containers
- [ ] Background #FAFAFA on all screens
- [ ] Text Primary #1C1B1F for headings, labels
- [ ] Text Secondary #6F6F6F for descriptions, hints
- [ ] Gradient #6750A4 → #3F51B5 on buttons and logo

### ✅ Shapes & Elevations
- [ ] Cards: 12dp corner radius, 4dp elevation
- [ ] Primary buttons: Pill shape (999dp radius), 8dp elevation
- [ ] Input fields: 12dp or 28dp (pill) radius
- [ ] Toolbar: 2dp elevation, white background

### ✅ Spacing
- [ ] Base spacing: 16dp between major sections
- [ ] Input padding: 14dp horizontal, 14dp vertical
- [ ] Card internal padding: 16dp
- [ ] Consistent margins throughout

### ✅ Components
- [ ] Login: Pill-shaped inputs, logo with elevation
- [ ] Home: App icon in toolbar, pill search bar
- [ ] Product Detail: White card for info, light grey quantity buttons
- [ ] Checkout: TextInputLayout with rounded inputs
- [ ] Purchase History: Colored status badges
- [ ] Cart: Proper badge display
- [ ] Search: Pill-shaped search bar

### ✅ Typography
- [ ] Material default text styles used
- [ ] Bold for headings, regular for body
- [ ] Consistent text sizes

### ✅ Interactions
- [ ] Ripple effects on clickable cards
- [ ] Button disabled states (login button)
- [ ] Privacy checkbox enables/disables login

---

**END OF GUIDE**

Apply these changes step-by-step, testing after each major change. The app should now match your Figma design while maintaining all functionality.
