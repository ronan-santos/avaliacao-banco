package br.com.avaliacao.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemVendaDTO {
	
	private Long codProduto;
	private Integer quantidade;
	private BigDecimal preco;
	
	public ItemVendaDTO() {}
	
	private ItemVendaDTO(Long codProduto, Integer quantidadem, BigDecimal preco) {
		
		setCodProduto(codProduto);
		setQuantidade(quantidadem);
		setPreco(preco);
		
	}
	
	public static ItemVendaDTOBuilder builder() {
		
		return new ItemVendaDTOBuilder();
	}
	
	public Long getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(Long codProduto) {
		this.codProduto = codProduto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
	public static class ItemVendaDTOBuilder{
		
		private static final String SEPARADOR_VENDA = ",";
		private static final String SEPARADOR_ITEM = "-";
		
		public List<ItemVendaDTO> build(String itens ) {

			return itensToList(tratarItensVenda(itens));
		}
		
		private ItemVendaDTO buildItem(String...parametros) {
			
			return new ItemVendaDTO(buildCodProduto(parametros[0]),
						buildQuantidade(parametros[1]),
					    buildPreco(parametros[2]));
		}
		
		private String tratarItensVenda(String itens) {
			
			itens = itens.replace("[", "")
						.replace("]", "");
			
			return itens;
		}
		
		private List<ItemVendaDTO> itensToList(String itens) {
			
			return Arrays
					.asList(itens.split(SEPARADOR_VENDA))
						.stream()
							.filter(Objects::nonNull )
							.map(item -> buildItem(item.split(SEPARADOR_ITEM)))
							.toList();
		}
		
		
		private Long buildCodProduto(String codigo) {
			
			return Long.parseLong(codigo);
		}
		
		private Integer buildQuantidade(String quantidade) {
			
			return Integer.valueOf(quantidade);
		}
		
		private BigDecimal buildPreco(String preco) {
			
			return new BigDecimal(preco);
		}
		
	}
	
	

}
