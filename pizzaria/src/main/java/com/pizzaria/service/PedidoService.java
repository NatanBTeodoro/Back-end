package com.pizzaria.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzaria.dto.DashboardDTO;
import com.pizzaria.dto.PedidoConfirmacaoDTO;
import com.pizzaria.dto.PedidoCriarDTO;
import com.pizzaria.dto.PedidoCriarDTO.ItemCriarDTO;
import com.pizzaria.dto.PedidoListagemDTO;
import com.pizzaria.dto.PedidoListagemDTO.ItemListagemDTO;
import com.pizzaria.entity.Bebida;
import com.pizzaria.entity.Cliente;
import com.pizzaria.entity.Endereco;
import com.pizzaria.entity.Pedido;
import com.pizzaria.entity.Pedido.ConfirmacaoRecebimento;
import com.pizzaria.entity.Pedido.Status;
import com.pizzaria.entity.PedidoItem;
import com.pizzaria.entity.PedidoItem.Tipo;
import com.pizzaria.entity.Pizza;
import com.pizzaria.repository.BebidaRepository;
import com.pizzaria.repository.ClienteRepository;
import com.pizzaria.repository.PedidoItemRepository;
import com.pizzaria.repository.PedidoRepository;
import com.pizzaria.repository.PizzaRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Transactional
    public PedidoListagemDTO criar(PedidoCriarDTO dto) {
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido não pode ser vazio.");
        }

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com id: " + dto.getClienteId()));

        if (!Boolean.TRUE.equals(cliente.getAtivo())) {
            throw new IllegalArgumentException("Conta excluída.");
        }

        if (enderecoService.buscarEnderecoEntrega(cliente.getId()) == null) {
            throw new IllegalArgumentException("Cadastre um endereço de entrega no perfil antes de fazer o pedido.");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setValorTotal(BigDecimal.ZERO);
        pedido.setStatus(Status.PENDENTE);
        pedido = pedidoRepository.save(pedido);

        BigDecimal total = BigDecimal.ZERO;
        List<PedidoItem> itensSalvos = new ArrayList<>();

        for (ItemCriarDTO itemDto : dto.getItens()) {
            BigDecimal valorUnitario = buscarValor(itemDto.getTipo(), itemDto.getItemId());
            PedidoItem item = new PedidoItem();
            item.setPedido(pedido);
            item.setTipo(itemDto.getTipo());
            item.setItemId(itemDto.getItemId());
            item.setQuantidade(itemDto.getQuantidade());
            item.setValorUnitario(valorUnitario);
            itensSalvos.add(pedidoItemRepository.save(item));
            total = total.add(valorUnitario.multiply(BigDecimal.valueOf(itemDto.getQuantidade())));
        }

        pedido.setValorTotal(total);
        pedidoRepository.save(pedido);

        return montarListagem(pedido, itensSalvos);
    }

    @Transactional
    public PedidoListagemDTO confirmarRecebimento(Long pedidoId, PedidoConfirmacaoDTO dto) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado com id: " + pedidoId));

        if (dto.getClienteId() == null || !pedido.getCliente().getId().equals(dto.getClienteId())) {
            throw new IllegalArgumentException("Pedido não pertence ao cliente informado.");
        }

        if (pedido.getConfirmacaoRecebimento() != ConfirmacaoRecebimento.AGUARDANDO) {
            throw new IllegalArgumentException("Este pedido já foi confirmado.");
        }

        if (dto.isRecebido()) {
            pedido.setConfirmacaoRecebimento(ConfirmacaoRecebimento.RECEBIDO);
            pedido.setStatus(Status.ENTREGUE);
            pedido.setReclamacao(null);
        } else {
            if (dto.getReclamacao() == null || dto.getReclamacao().isBlank()) {
                throw new IllegalArgumentException("Descreva o problema ao marcar como não recebido.");
            }
            pedido.setConfirmacaoRecebimento(ConfirmacaoRecebimento.NAO_RECEBIDO);
            pedido.setStatus(Status.NAO_RECEBIDO);
            pedido.setReclamacao(dto.getReclamacao().trim());
        }

        pedidoRepository.save(pedido);
        return montarListagem(pedido, pedidoItemRepository.findByPedidoId(pedido.getId()));
    }

    @Transactional
    public void removerPedidosDoCliente(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteIdOrderByDataHoraDesc(clienteId);
        for (Pedido pedido : pedidos) {
            pedidoItemRepository.deleteAll(pedidoItemRepository.findByPedidoId(pedido.getId()));
            pedidoRepository.delete(pedido);
        }
    }

    @Transactional(readOnly = true)
    public List<PedidoListagemDTO> listar() {
        return pedidoRepository.findAllByOrderByDataHoraDesc().stream()
                .map(p -> montarListagem(p, pedidoItemRepository.findByPedidoId(p.getId())))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoListagemDTO> listarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new NoSuchElementException("Cliente não encontrado com id: " + clienteId);
        }
        return pedidoRepository.findByClienteIdOrderByDataHoraDesc(clienteId).stream()
                .map(p -> montarListagem(p, pedidoItemRepository.findByPedidoId(p.getId())))
                .toList();
    }

    private BigDecimal buscarValor(Tipo tipo, Long itemId) {
        if (tipo == Tipo.PIZZA) {
            Pizza pizza = pizzaRepository.findById(itemId)
                    .orElseThrow(() -> new NoSuchElementException("Pizza não encontrada com id: " + itemId));
            return pizza.getValor();
        }
        Bebida bebida = bebidaRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Bebida não encontrada com id: " + itemId));
        return bebida.getValor();
    }

    private String buscarNome(Tipo tipo, Long itemId) {
        if (tipo == Tipo.PIZZA) {
            return pizzaRepository.findById(itemId).map(Pizza::getNome).orElse("Pizza");
        }
        return bebidaRepository.findById(itemId).map(Bebida::getNome).orElse("Bebida");
    }

    private String formatarEndereco(Cliente cliente) {
        Endereco endereco = enderecoService.buscarEnderecoEntrega(cliente.getId());
        if (endereco == null) {
            return "Endereço não cadastrado";
        }
        return formatarEndereco(endereco);
    }

    private String formatarEndereco(Endereco endereco) {
        StringBuilder sb = new StringBuilder();
        if (endereco.getRotulo() == Endereco.Rotulo.OUTRO && endereco.getNomePersonalizado() != null
                && !endereco.getNomePersonalizado().isBlank()) {
            sb.append(endereco.getNomePersonalizado()).append(" — ");
        } else if (endereco.getRotulo() != null) {
            sb.append(rotuloLegivel(endereco.getRotulo())).append(" — ");
        }
        sb.append(endereco.getRua()).append(", ").append(endereco.getNumero());
        sb.append(" — ").append(endereco.getBairro());
        sb.append(", ").append(endereco.getCidade());
        if (endereco.getCep() != null && !endereco.getCep().isBlank()) {
            sb.append(" — CEP: ").append(endereco.getCep());
        }
        return sb.toString();
    }

    private String rotuloLegivel(Endereco.Rotulo rotulo) {
        return switch (rotulo) {
            case CASA -> "Casa";
            case TRABALHO -> "Trabalho";
            case OUTRO -> "Outro";
        };
    }

    private PedidoListagemDTO montarListagem(Pedido pedido, List<PedidoItem> itens) {
        PedidoListagemDTO dto = new PedidoListagemDTO();
        dto.setId(pedido.getId());
        dto.setClienteNome(pedido.getCliente().getNomeCompleto());
        dto.setClienteAtivo(Boolean.TRUE.equals(pedido.getCliente().getAtivo()));
        dto.setDataHora(pedido.getDataHora());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setStatus(pedido.getStatus().name());
        dto.setEnderecoEntrega(formatarEndereco(pedido.getCliente()));
        dto.setConfirmacaoRecebimento(pedido.getConfirmacaoRecebimento().name());
        dto.setReclamacao(pedido.getReclamacao());
        dto.setItens(itens.stream()
                .map(i -> new ItemListagemDTO(
                        i.getId(),
                        buscarNome(i.getTipo(), i.getItemId()),
                        i.getQuantidade(),
                        i.getValorUnitario()))
                .toList());
        return dto;
    }

    @Transactional(readOnly = true)
    public long contarTotal() {
        return pedidoRepository.count();
    }

    @Transactional(readOnly = true)
    public long contarHoje() {
        LocalDate hoje = LocalDate.now();
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getDataHora() != null && p.getDataHora().toLocalDate().equals(hoje))
                .count();
    }
}
