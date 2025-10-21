package br.com.fiap.sb_kakfa_producer.Controller;

import br.com.fiap.sb_kakfa_producer.DTO.PagamentoDTO;
import br.com.fiap.sb_kakfa_producer.Service.PagamentoService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {
    private final PagamentoService service;

    public PagamentoController(PagamentoService service){
        this.service = service;
    }

    @PostMapping
    public String enviar(@RequestBody PagamentoDTO dto){
        return  service.processarPagamento(dto);
    }


}
