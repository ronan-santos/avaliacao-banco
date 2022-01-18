package br.com.avaliacao.service;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.avaliacao.utils.Constantes;

@Component
public class LeitorArquivosService {
	
	@Autowired
	private ProcessadorArquivoService processarArquivoService;
	
	public void importarArquivos() {

		
		processarArquivos(obterPathsArquivos());
				
	}
	
	private void processarArquivos(List<Path> list ) {
		
		if(Objects.nonNull(list)) {
			
			list
				.stream()
					.filter(Objects::nonNull)
					.forEach(this::processarArquivo);
		}
	
	}
		
	private void processarArquivo(Path arquivo) {
		
	
		try(Stream<String> linhasStream = Files.lines(arquivo, StandardCharsets.ISO_8859_1) ){

			processarArquivoService
				.processarArquivo(linhasStream.toList(),arquivo );

			
		} catch (IOException e) {
			//FIXME TRATAR EXEÇÃO
			e.printStackTrace();
		}
		
	}

	
	private List<Path> obterPathsArquivos() {

		try(Stream<Path> arquivos = Files.list(Path.of(Constantes.HOMEPATH_IN))){
			
			return arquivos.filter(file -> !Files.isDirectory(file.getFileName()))
					.filter(file ->  file.getFileName().toString().matches("(\\w)+(.dat)$") )
					.collect(Collectors.toList());
		} catch (IOException e) {
			//FIXME TRATAR EXEÇÃO
			e.printStackTrace();
		}
		return new ArrayList<>();
		
	}

}
