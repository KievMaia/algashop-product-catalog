package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        headers {
            accept 'application/json'
            contentType 'application/json'
        }
        url("/api/v1/products/d70864cd-671c-4ec2-a3d2-0cc8f5dc55ba")
        body([
                name        : "Notebook X11",
                brand       : "Deep Driver",
                regularPrice: 1500.00,
                salePrice   : 1000.00,
                enabled     : true,
                categoryId  : "f5ab7a1e-37da-41e1-892b-a1d38275c2f2",
                description : "A Gamer Notebook"
        ])
    }
    response {
        status 404
        headers {
            contentType 'application/problem+json'
        }
        body([
                instance: fromRequest().path(),
                type    : "/errors/not-found",
                title   : "Not Found"
        ])
    }
}
