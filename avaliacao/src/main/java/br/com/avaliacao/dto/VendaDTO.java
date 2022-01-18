package br.com.avaliacao.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.util.StringUtils;

import br.com.avaliacao.service.enums.LinhaEnum;

public class VendaDTO extends LinhaDTO {
	
	
	private Long codVenda;
	private List<ItemVendaDTO> produtos;
	private BigDecimal valorVenda;
	
	public VendaDTO() {}
	
	private VendaDTO( LinhaEnum identificador ,Long codVenda, BigDecimal valorTotal, String vendedor, List<ItemVendaDTO> itens ) {
		
		setTipoLinha(identificador);
		setCodVenda(codVenda);
		setValorVenda(valorTotal);
		setNome(vendedor);
		setProdutos(itens);
	}
	
	public Long getCodVenda() {
		return codVenda;
	}
	public void setCodVenda(Long codVenda) {
		this.codVenda = codVenda;
	}
	public List<ItemVendaDTO> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<ItemVendaDTO> produtos) {
		this.produtos = produtos;
	}
	public BigDecimal getValorVenda() {
		return valorVenda;
	}
	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
	
	public static VendaDTOBuilder builder() {
		
		return new VendaDTOBuilder();
	}
	
	public static class VendaDTOBuilder{
		
		private static final String VENDEDOR_INVALIDO = "VENDEDOR DESCONHECIDO";
		
		private Long codigoVenda;
		private String vendedor;
		private List<ItemVendaDTO> itens;
		private BigDecimal valorVenda = BigDecimal.ZERO;
		
		public VendaDTO build(String...parametros) {
			
			buildDadosVenda(parametros);
			
			return new VendaDTO(LinhaEnum.VENDA, 
								this.codigoVenda,
								this.valorVenda,
								this.vendedor,
								this.itens);
		}
		
		private void buildDadosVenda(String...parametros) {
			
			buildCodigoVenda(parametros[1]);
			buildItensvenda(parametros[2]);
			buildNome(parametros[3]);
		}
		
		private void buildValorTotalVenda() {
			
			if(Objects.nonNull(this.itens)) {
				
				this.itens
						.stream()
							.filter(Objects::nonNull)
							.forEach(item ->  this.valorVenda = this.valorVenda.add(calcularVenda(item)));
							
			}
		}
		
		private BigDecimal calcularVenda(ItemVendaDTO venda ) {
			
			return venda.getPreco().multiply(new BigDecimal(venda.getQuantidade()));
		}
	
		private void buildItensvenda(String itemLinha) {
			
			this.itens = ItemVendaDTO
							.builder()
								.build(itemLinha);
			
			buildValorTotalVenda();
		}
		
		private void buildCodigoVenda(String codigo) {
			
			if(StringUtils.hasText(codigo)) {
				
				this.codigoVenda = Long.parseLong(codigo);
			}

		}
		
		private void buildNome(String vendedor) {
			
			
			this.vendedor = StringUtils.hasText(vendedor) 
								? vendedor
								: VENDEDOR_INVALIDO;

		}
		
		
	}
	

}
