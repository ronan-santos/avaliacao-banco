package br.com.avaliacao.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.util.StringUtils;

import br.com.avaliacao.service.enums.LinhaEnum;

public class VendedorDTO extends LinhaDTO {
	
	
	private String cpf;

	private BigDecimal salario;
	
	private BigDecimal valorTotalVenda;
	
	private List<VendaDTO> vendas;
	
	public VendedorDTO() {}
	
	private VendedorDTO(LinhaEnum identificador, String cpf, String nome, BigDecimal salario  ) {
		
		
		setNome(nome);
		setTipoLinha(identificador);
		setSalario(salario);
		setCpf(cpf);
		
	}

	public static VendedorDTOBuilder builder() {
	
		return new VendedorDTOBuilder();
		
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	
	
	
	
	public BigDecimal getValorTotalVenda() {
		return valorTotalVenda;
	}

	public void setValorTotalVenda(BigDecimal valorTotalVenda) {
		this.valorTotalVenda = valorTotalVenda;
	}

	public List<VendaDTO> getVendas() {
		return vendas;
	}

	public void setVendas(List<VendaDTO> vendas) {
		this.vendas = vendas;
	}




	public static  class VendedorDTOBuilder {
	
		private static final int TAMANHO_CPF = 11;
		private static final String CPF_INVALIDO = "CPF INVALIDO";
		private static final String VENDEDOR_NOME_INVALIDO = "VENDEDOR DESCONHECIDO";
		
		public VendedorDTO build(String...parametros  ) {
			
			return new VendedorDTO(LinhaEnum.VENDEDOR,
						buildCPF(parametros[1]), 
						buildNome(parametros[2]),
						buildSalario(parametros[3]));
		}
		
		private String buildCPF(String cpf) {
			
			if(Objects.nonNull(cpf) && cpf.trim().length() == TAMANHO_CPF) {
				
				return cpf.trim();
			}
			
			return CPF_INVALIDO;
		}
		
		private BigDecimal buildSalario(String salario) {
			
			if(Objects.nonNull(salario)) {
				
				return new BigDecimal(salario);
			}
			
			return null;
		}
		
		private String buildNome(String nome) {
			
			if(StringUtils.hasText(nome) ) {
				
				return nome;
			}
			
			return VENDEDOR_NOME_INVALIDO;
		}
	
	}



	
	

}
