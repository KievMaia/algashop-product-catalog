package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        headers {
            accept 'application/json'
            contentType 'application/json'
        }
        url("/api/v1/categories/8f91621b-7661-4cc7-a39a-a3dd7f7e71c4")
        body([
                name   : value(
                        test("Notebook"),
                        stub(nonBlank())
                ),
                enabled: value(
                        test(true),
                        stub(anyBoolean())
                )
        ])
    }
    response {
        status 200
        headers {
            contentType 'application/json'
        }
        body([
                id     : fromRequest().path(3),
                name   : fromRequest().body('$.name'),
                enabled: fromRequest().body('$.enabled')
        ])
    }
}
