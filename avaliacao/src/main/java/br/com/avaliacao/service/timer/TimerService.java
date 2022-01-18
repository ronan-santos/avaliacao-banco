package br.com.avaliacao.service.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.avaliacao.service.LeitorArquivosService;

@Component
public class TimerService {
	
	
	@Autowired
	private LeitorArquivosService leitorService;
	
	@Scheduled(fixedRate = 4000 )
	public void iniciarSchedule() {
		
		leitorService.importarArquivos();
	}

}
