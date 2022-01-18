package br.com.avaliacao.service.enums;

public enum LinhaEnum {
	
	
	CLIENTE("002"),
	VENDA("003"),
	VENDEDOR("001");
	
	private LinhaEnum(String codigo) {
		this.codigo = codigo;
	}
	
	
	private String codigo;


	public String getCodigo() {
		return codigo;
	}
	
	

}
