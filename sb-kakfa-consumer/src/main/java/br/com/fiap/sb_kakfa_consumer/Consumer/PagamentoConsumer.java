package br.com.fiap.sb_kakfa_consumer.Consumer;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class PagamentoConsumer {
    private final SimpMessagingTemplate messagingTemplate;

    public PagamentoConsumer(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;

    }


    @KafkaListener(topics = "${kafka.topic.pagamento}", groupId = "grupo-pagamentos")
    public void consumirMensagem(String mensagem){
        System.out.println("### Recebido do Kafka: "+ mensagem + " ### ");
        messagingTemplate.convertAndSend("/topic/messages", mensagem);
    }
}
