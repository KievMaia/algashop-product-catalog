package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method DELETE()
        headers {
            accept 'application/json'
        }
        url("/api/v1/products/019dbc11-088b-7476-8dc0-6cf690b8624b")
    }
    response {
        status 204
    }
}
