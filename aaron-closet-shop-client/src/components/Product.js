import React from 'react'

function Product({ product }) {
    return (
        <div className='product_card'>
            <img src={product.imageUrl} alt={product.name + " image"} />
            <h3>{product.nam}</h3>
            <p>{product.price}</p>
            <p>{product.description}</p>
        </div>
    )
}

export default Product

// {
//     "productId": 3,
//     "name": "Sample Product",
//     "description": "This is a sample product description.",
//     "price": 19.99,
//     "stockQuantity": 100,
//     "imageUrl": "https://example.com/sample-product-image.jpg",
//     "manufacturer": "Sample Manufacturer Inc."
//   }