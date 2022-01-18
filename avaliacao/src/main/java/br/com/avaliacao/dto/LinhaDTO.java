package br.com.avaliacao.dto;

import br.com.avaliacao.service.enums.LinhaEnum;

public abstract class LinhaDTO {

	
	private LinhaEnum tipoLinha;
	
	private String nome;

	public LinhaEnum getTipoLinha() {
		return tipoLinha;
	}

	public void setTipoLinha(LinhaEnum tipoLinha) {
		this.tipoLinha = tipoLinha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
}
