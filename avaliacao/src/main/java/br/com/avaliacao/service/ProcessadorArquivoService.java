package br.com.avaliacao.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.avaliacao.dto.ClienteDTO;
import br.com.avaliacao.dto.VendaDTO;
import br.com.avaliacao.dto.VendedorDTO;
import br.com.avaliacao.service.enums.LinhaEnum;
import br.com.avaliacao.utils.Constantes;

@Component
public class ProcessadorArquivoService {
	
	private static final String SEPARADOR_LINHA = "ç";
	private static final String EXTENSAO_ARQUIVO = ".dat";
	private static final String ARQUIVO_FINALIZADO_END = ".done.dat";
	
	
	public void processarArquivo(List<String> linhas, Path arquivo) {
		
		List<VendaDTO> vendas = obterVendas(linhas);
		List<VendedorDTO> vendedores = relacionarVendasVendedor(obtervendedores(linhas), vendas);

		gerarResultadoProcessamentoArquivo(vendedores, obterClientes(linhas), 
											obterMaiorVenda(vendas), obterVendedorMenorVenda(vendedores),
											arquivo.toFile().getName());
		
	
		setArquivoConcluido(arquivo);
		
	}
	
	private void setArquivoConcluido(Path arquivo ) {
		
		try {
			String nomeArquivo = arquivo.toFile().getName().replace(EXTENSAO_ARQUIVO, ARQUIVO_FINALIZADO_END);
			Files.move(arquivo, arquivo.resolveSibling(nomeArquivo), StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			//FIXME TRATAR EXEÇÃO
			e.printStackTrace();
		}
	}
	
	private void gerarResultadoProcessamentoArquivo(List<VendedorDTO> vendedores, List<ClienteDTO> clientes, 
				Optional<VendaDTO> maiorVenda, Optional<VendedorDTO> piorVendedor, String nomeArquivo  ) {
		
		nomeArquivo = nomeArquivo.replace(EXTENSAO_ARQUIVO, ARQUIVO_FINALIZADO_END);
		
		try(BufferedWriter escritor = new BufferedWriter(new FileWriter(Constantes.HOMEPATH_OUT.concat(nomeArquivo))); ) {
		 
			escritor.write(" QUANTIDADE DE CLIENTES ->>>>>>>>>>>> "+ clientes.size() );
			escritor.newLine();

			escritor.write("QUANTIDADE DE VENDEDORES ->>>>>>>>>>>"+vendedores.size());
			escritor.newLine();
			
			if(maiorVenda.isPresent()) {
				
				escritor.write("ID MAIOR VENDA ->>>>>>>>" + maiorVenda.get().getCodVenda() );
				escritor.newLine();
			}
			
			if(piorVendedor.isPresent()) {
				
				escritor.write("PIOR VENDEDOR ->>>>>>>>>>>>>>>>>>>>" + piorVendedor.get().getNome() );
				escritor.newLine();
			}

		} catch (IOException e) {
			//FIXME TRATAR EXEÇÃO
			e.printStackTrace();
		}
		
	
		
	}
	
	private Optional<VendaDTO> obterMaiorVenda(List<VendaDTO> vendas ){
		
		if(Objects.nonNull(vendas) ) {
			
			return vendas
					.stream()
						.filter(Objects::nonNull)
						.reduce((venda1, venda2) -> venda1.getValorVenda().compareTo(venda2.getValorVenda()) > 0 
														?  venda1 : venda2 );
		}
		
		return Optional.empty();
	}
	
	private Optional<VendedorDTO> obterVendedorMenorVenda(List<VendedorDTO> vendedores ) {
		
		
		if(Objects.nonNull(vendedores)) {
			
			return vendedores
					.stream()
						.filter(Objects::nonNull)
						.reduce((vendedor1,vendedor2)->  vendedor1.getValorTotalVenda().compareTo(vendedor2.getValorTotalVenda()) < 0 
															? vendedor1 : vendedor2);
		
		}
		
		return Optional.empty();
	}
	
	private List<VendedorDTO> relacionarVendasVendedor(List<VendedorDTO> vendedores, List<VendaDTO> vendas ) {
		
		if(verificarVendasEVendedores(vendedores, vendas)) {
			
			return vendedores
					.stream()
						.filter(Objects::nonNull)
						.map(vendedor -> { vendedor.setVendas(definirVendasVendedor(vendas, vendedor));
										   vendedor.setValorTotalVenda(totalizarVendasPor(vendedor));
											return vendedor; } )
						.collect(Collectors.toList());
			
		}
		
		return vendedores;
	}
	
	private BigDecimal totalizarVendasPor(VendedorDTO vendedor ) {

		
		if(Objects.nonNull(vendedor.getVendas())) {

			return 
				vendedor
					.getVendas()
						.stream()
						.filter(Objects::nonNull)
						.map(VendaDTO::getValorVenda )
						.reduce(BigDecimal.ZERO, BigDecimal::add );
		
		}
		
		return BigDecimal.ZERO;
		
	}
	
	private boolean verificarVendasEVendedores(List<VendedorDTO> vendedores, List<VendaDTO> vendas) {
		
		return (Objects.nonNull(vendas) && !vendas.isEmpty())
				&& (Objects.nonNull(vendedores) && !vendedores.isEmpty() );
	}
	
	private List<VendaDTO> definirVendasVendedor( List<VendaDTO> vendas, VendedorDTO vendedor  ){
		
		
		return vendas
				.stream()
					.filter(Objects::nonNull)
					.filter(venda -> venda.getNome().equalsIgnoreCase(vendedor.getNome()))
					.toList();
		
	}
	
	private List<VendedorDTO> obtervendedores(List<String> linhas ){
		
		return linhas
				.stream()
				.filter(Objects::nonNull)
				.filter(linha -> verificarTipoLinha(LinhaEnum.VENDEDOR, linha))
				.map(linha -> VendedorDTO.builder().build(linha.split(SEPARADOR_LINHA)))
				.toList();

	}
	
	private List<ClienteDTO> obterClientes(List<String> linhas ){
		
		return linhas
				.stream()
				.filter(Objects::nonNull)
				.filter(linha -> verificarTipoLinha(LinhaEnum.CLIENTE, linha))
				.map(linha ->  ClienteDTO.builder().build(linha.split(SEPARADOR_LINHA)))
				.toList();

			
	}
	
	private List<VendaDTO> obterVendas(List<String> linhas ){
		
		
		return linhas
				.stream()
					.filter(Objects::nonNull)
					.filter(linha -> verificarTipoLinha(LinhaEnum.VENDA, linha))
					.map(linha -> VendaDTO.builder().build(linha.split(SEPARADOR_LINHA)))
					.toList();
	}
	
	private boolean verificarTipoLinha(LinhaEnum verificador, String linha) {
		
		return linha.startsWith(verificador.getCodigo());
		
	}
	


}
