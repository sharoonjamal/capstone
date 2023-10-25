import { useEffect, useState } from 'react';
import { getProducts } from '../services/productsAPI';
import Product from '../components/Product';


function Products() {
    const [products, setProducts] = useState([])

    useEffect(() => {
        getProducts()
            .then((json) => setProducts(json))
            .catch(err => console.log(err))
    }, [])

    return (
        <div className='products_page'>
            {
                products.map(product => <Product key={product.productId} product={product} />)
            }
        </div>
    )
}

export default Products