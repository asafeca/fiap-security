package br.com.fiap.sb_kakfa_producer.Service;

import br.com.fiap.sb_kakfa_producer.Config.PayPalConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Service
public class PayPalService {
    private final PayPalConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    public PayPalService(PayPalConfig config){
        this.config = config;
    }

    public String getAccessToken(){
        String auth = config.getClient() + ":" + config.getClientSecret();
        String encoded = Base64.getEncoder()
                .encodeToString(auth.getBytes());


        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setBasicAuth(encoded);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(config.getApiBaseUrl() + "/v1/oauth2/token",
                request, Map.class);

        return (String)(response.getBody().getOrDefault("access_token",null));

    }

    public Map<?,?> createOrder(Map<String, Object> payload) {
        String token = this.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(
                payload, headers
        );

        ResponseEntity<Map> response = restTemplate.postForEntity(
                config.getApiBaseUrl() + "/v2/checkout/orders", request, Map.class
        );

        return response.getBody();
    }
}
