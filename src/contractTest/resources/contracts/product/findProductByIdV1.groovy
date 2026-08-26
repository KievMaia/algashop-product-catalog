package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method GET()
        headers {
            accept 'application/json'
        }
        url("/api/v1/products/019dbc11-088b-7476-8dc0-6cf690b8624b")
    }
    response {
        status 200
        headers {
            contentType 'application/json'
        }
        body([
                id: fromRequest().path(3),
                addedAt: anyIso8601WithOffset(),
                name: "Notebook X11",
                brand: "Deep Driver",
                regularPrice: 1500.00,
                salePrice: 1000.0,
                inStock: true,
                enabled: true,
                category: [
                        id:  anyUuid(),
                        name: "Notebook"
                ],
                description: "A Gamer Notebook"
        ])
    }
}