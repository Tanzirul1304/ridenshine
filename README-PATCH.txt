RideNShine Product Image Upload Patch

What it adds:
- Product image file picker in Admin > Products
- Automatic browser-side image compression
- Image preview and Remove picture button
- Product thumbnail in the admin product table
- Image stored in PostgreSQL as text, so it survives deployment
- Existing optional image URL remains available

How to apply:
1. Stop the local app: docker compose down
2. Extract this ZIP.
3. Copy the backend and frontend folders into the main RideNShine project folder.
4. When Windows asks, choose Replace the files in the destination.
5. In the RideNShine project PowerShell, run:
   docker compose up --build -d
6. Open http://localhost:3000/admin
7. Go to Products, edit a product, choose a picture, and save.
