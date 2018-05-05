package br.com.caelum.ingresso.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

		private List<Sessao> sessoesDaSala;
		
		public GerenciadorDeSessao(List<Sessao> sessoesDaSala){
			this.sessoesDaSala = sessoesDaSala;
		}
		
		private boolean horarioIsConflitante(Sessao sessaoExistente, Sessao sessaoNova){
			LocalDate hoje = LocalDate.now();
			
			LocalDateTime horarioSessaoExistente = sessaoExistente.getHorario().atDate(hoje);
			LocalDateTime horarioSessaoNova = sessaoNova.getHorario().atDate(hoje);
			
			boolean terminaAntes = horarioSessaoNova.plus(sessaoNova.getFilme().getDuracao()).isBefore(horarioSessaoExistente); 
			boolean terminaDepois = horarioSessaoExistente.plus(sessaoExistente.getFilme().getDuracao()).isBefore(horarioSessaoNova); 
			
			if(terminaAntes || terminaDepois) {
				return false;
			}
			return true;
		}
		
		public boolean cabe(Sessao sessaoNova){
			
			return sessoesDaSala.stream().noneMatch(sessaoExistente -> horarioIsConflitante(sessaoExistente, sessaoNova));
		}
}
