package vstu.isd.notebin.api.auth;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// TODO move url in properties
@FeignClient(name = "auth-service", url = "http://localhost:8081/api/v1/auth")
public interface AuthApi {
    @PostMapping("/verify-access")
    Boolean verifyAccessToken(@Valid @RequestBody VerifyAccessTokenRequest request);
}