package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        headers {
            accept 'application/json'
            contentType 'application/json'
        }
        url("/api/v1/categories/d70864cd-671c-4ec2-a3d2-0cc8f5dc55ba")
        body([
                name   : "Notebook",
                enabled: true
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
