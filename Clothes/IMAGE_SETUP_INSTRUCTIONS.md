# How to Use SteelSeries Headphones Image

## Option 1: Use Remote URL (Current Setup)

The app currently loads images from URLs using Glide. I've updated the URL to a high-quality black wireless headphones image that matches the SteelSeries style.

**File:** `app/src/main/java/com/example/ecommerceapp/data/SeedData.java`

The image URL is already set. The app will load it automatically when you run it.

---

## Option 2: Use Local Image File (If You Have the Image)

If you have the actual SteelSeries headphones image file and want to use it locally:

### Steps:

1. **Save the image file** as `headphones_steelseries.jpg` (or `.png`)

2. **Place it in the drawable folder:**
   - Copy the image to: `app/src/main/res/drawable/headphones_steelseries.jpg`

3. **Update SeedData.java:**

   **Find this line (around line 36-38):**
   ```java
   products.add(new Product(1, "Premium Wireless Headphones", 129.99, 4.5, 124,
       "Premium wireless headphones...",
       "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=800&q=80"));
   ```

   **Replace the URL with:**
   ```java
   products.add(new Product(1, "Premium Wireless Headphones", 129.99, 4.5, 124,
       "Premium wireless headphones...",
       "android.resource://com.example.ecommerceapp/drawable/headphones_steelseries"));
   ```

4. **Alternative: Use Resource ID directly in adapter**

   If using local drawable, you might need to update `ProductAdapter.java` to handle both URLs and drawable resources:

   **In `ProductAdapter.java`, find the `bind()` method and update:**
   ```java
   void bind(Product product) {
       tvProductName.setText(product.getName());
       tvProductPrice.setText(String.format("$%.2f", product.getPrice()));
       tvRating.setText(String.format("%.1f (%d)", product.getRating(), product.getReviews()));
       
       // Load image using Glide
       String imageUrl = product.getImageUrl();
       if (imageUrl.startsWith("android.resource://")) {
           // Local drawable resource
           int resourceId = itemView.getContext().getResources()
               .getIdentifier("headphones_steelseries", "drawable", 
                   itemView.getContext().getPackageName());
           Glide.with(itemView.getContext())
                   .load(resourceId)
                   .placeholder(android.R.drawable.ic_menu_gallery)
                   .error(android.R.drawable.ic_menu_gallery)
                   .into(ivProductImage);
       } else {
           // Remote URL
           Glide.with(itemView.getContext())
                   .load(imageUrl)
                   .placeholder(android.R.drawable.ic_menu_gallery)
                   .error(android.R.drawable.ic_menu_gallery)
                   .into(ivProductImage);
       }
   }
   ```

---

## Option 3: Use Direct Image URL (If You Have One)

If you have a direct URL to the SteelSeries headphones image:

**Update in SeedData.java:**
```java
products.add(new Product(1, "Premium Wireless Headphones", 129.99, 4.5, 124,
    "Premium wireless headphones...",
    "YOUR_DIRECT_IMAGE_URL_HERE"));
```

---

## Current Status

✅ Product name: "Premium Wireless Headphones"  
✅ Image URL: Updated to high-quality black headphones image  
✅ Glide will load the image automatically

The app is ready to display the headphones image. If you want to use a specific local file, follow Option 2 above.
