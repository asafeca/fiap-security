package br.com.fiap.sb_kakfa_consumer.Consumer;

import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class PagamentoConsumer {

    @KafkaListener(topics = "${kafka.topic.pagamento}", groupId = "grupo-pagamentos")
    public void consumir(String mensagem){

        System.out.println("### Mensagem recebida do Kafka: "+ mensagem);

    }
}
