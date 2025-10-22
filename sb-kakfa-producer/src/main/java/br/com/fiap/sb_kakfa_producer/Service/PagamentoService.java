package br.com.fiap.sb_kakfa_producer.Service;

import br.com.fiap.sb_kakfa_producer.DTO.PagamentoDTO;
import br.com.fiap.sb_kakfa_producer.Producer.PagamentoProducer;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final PagamentoProducer producer;

    public PagamentoService(PagamentoProducer producer){
        this.producer = producer;
    }

    public String processarPagamento(PagamentoDTO pagamento){
        try{
           return  producer.enviar(pagamento);

        }
        catch (Exception e){
            return ">>>>>>>> Erro ao enviar pagamento: "+ e.getMessage();

        }
    }
}
