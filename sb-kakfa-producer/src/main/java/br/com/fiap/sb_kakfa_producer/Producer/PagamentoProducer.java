package br.com.fiap.sb_kakfa_producer.Producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PagamentoProducer {
    @Value("${topicos.pagamento.request.topic}")
    private String topic;

    private final KafkaTemplate<String,String> kakfaTemplate;
    private final ObjectMapper objectMappper;

    public PagamentoProducer(KafkaTemplate<String,String> kakfaTemplate,ObjectMapper objectMappper){
        this.kakfaTemplate = kakfaTemplate;
        this.objectMappper =  objectMappper;
    }
}
