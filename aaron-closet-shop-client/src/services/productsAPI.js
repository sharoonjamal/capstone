const url = process.env.REACT_APP_API_URL;

export async function getProducts() {

    const init = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
    };

    const response = await fetch(url + '/products', init);
    if (response.status === 200) {
        const json = await response.json();
        return json
    } else {
        return Promise.reject(response);
    }
}