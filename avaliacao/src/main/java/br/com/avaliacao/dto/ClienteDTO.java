package br.com.avaliacao.dto;

import java.util.Objects;

import br.com.avaliacao.service.enums.LinhaEnum;

public class ClienteDTO extends LinhaDTO {
	
	private String cnpj;
	private String area;
	
	public ClienteDTO() {}
	
	private ClienteDTO(LinhaEnum identificador, String cnpj, String nome, String area ) {
		
		setTipoLinha(identificador);
		setArea(area);
		setCnpj(cnpj);
		setNome(nome);
	}
	
	public static ClienteDTOBuilder builder() {
		
		return new ClienteDTOBuilder();
	}
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public static class ClienteDTOBuilder {
		
		private static final int TAMANHO_CNPJ = 14;
		private static final String CNPJ_INVALIDO = "CNPJ INVALIDO";
		
		
		public ClienteDTO build(String...parametros) {
			
			return new ClienteDTO(LinhaEnum.CLIENTE,
								buildCnpj(parametros[1]),
								parametros[2],
								parametros[3]);
		}
		
		private String buildCnpj(String cnpj) {
			
			if(Objects.nonNull(cnpj) && cnpj.trim().length() == TAMANHO_CNPJ ) {
				
				return cnpj.trim();
			}
			
			return CNPJ_INVALIDO;
		}
	}

}
