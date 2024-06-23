package fyodor.dev.apigateway.filter;

import fyodor.dev.apigateway.exception.InvalidAccessTokenException;
import fyodor.dev.apigateway.exception.MissingAccessTokenException;
import fyodor.dev.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;
    public static class Config {}


    @Autowired
    public AuthenticationFilter(JwtUtil jwtUtil, RouteValidator validator) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.validator = validator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = null;
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new MissingAccessTokenException("Missing authorization token");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);

                    request = exchange.getRequest()
                            .mutate()
                            .header("X-User-login", jwtUtil.extractEmail(authHeader))
                            .build();
                } catch (Exception e) {
                    throw new InvalidAccessTokenException("Invalid Access Token");
                }
            }
            return chain.filter(exchange.mutate()
                    .request(request)
                    .build());
        });
    }
}
