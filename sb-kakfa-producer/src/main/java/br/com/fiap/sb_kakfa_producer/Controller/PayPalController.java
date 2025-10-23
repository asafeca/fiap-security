package br.com.fiap.sb_kakfa_producer.Controller;


import br.com.fiap.sb_kakfa_producer.Service.PayPalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {
    private final PayPalService service;


    public PayPalController(PayPalService service){
        this.service = service;
    }

    @PostMapping("/orders")
    public Map<String,Object> createOrder(@RequestBody Map<String,Object> payload){
        return (Map) service.createOrder(payload);
    }
}
