package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method DELETE()
        headers {
            accept 'application/json'
        }
        url("/api/v1/categories/8f91621b-7661-4cc7-a39a-a3dd7f7e71c4")
    }
    response {
        status 204
    }
}
