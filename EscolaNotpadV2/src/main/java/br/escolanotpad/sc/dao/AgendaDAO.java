package br.escolanotpad.sc.dao;


import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.escolanotpad.sc.model.entity.Agenda;
import br.escolanotpad.sc.model.entity.Turma;
import br.escolanotpad.sc.model.entity.Usuario;

public class AgendaDAO extends DAO{
	
	public AgendaDAO(){
		
	}
	
	public AgendaDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	public void salvar(Agenda agenda) throws SQLException{
		if(agenda.getId() == null){
			getEM().persist(agenda);
		}else{
			getEM().merge(agenda);
		}
	}
	
	public Agenda buscarPorId(Long id){
		return getEM().find(Agenda.class, id);
	}
	
	public List<Agenda> listar(){
		Query query = getEM().createQuery("From Agenda order by id desc", Agenda.class);
		return query.getResultList();
	}
	
	public void excluir(Long id){
		Agenda agenda = getEM().getReference(Agenda.class, id);
		getEM().remove(agenda);
	}
	
	public List<Usuario> listarParaAutoComplete(String busca) {
		Query query = getEM().createQuery("From Usuario where perfil = :perfil order by perfil", Usuario.class);
		query.setParameter("perfil", "ROLE_PROFESSOR");
		return query.getResultList();
	}

	public List<Agenda> listarAgendasPorTurma(String busca) {
		
		try {
			Query especial = getEM().createNativeQuery("select turma_id from turma_usuario where alunosTurma_id = :id_usuario");
			especial.setParameter("id_usuario", busca);
			//int resultado = especial.getFirstResult();
			BigInteger resultQuery = (BigInteger) especial.getSingleResult();
			
			
			
			Query query = getEM().createQuery("From Agenda where turma_id = :turmaid", Agenda.class);
			query.setParameter("turmaid", resultQuery.longValue());
			
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public List<Agenda> listaAgendaPorProfessor(String professorId) {
		Query query = getEM().createQuery("From Agenda where professorResponsavel_id = :professorid", Agenda.class);
		query.setParameter("professorid", professorId);
		return query.getResultList();
	}
	
	public List<Agenda> buscarPorData(Date busca){
		Query query = getEM().createQuery("From Agenda where data = :data", Agenda.class);
		query.setParameter("data", busca);
		
		return query.getResultList();
	}

	public List<Agenda> buscarPorAmbiente(Long buscaAmbiente, Date buscaData) {
		Query query = getEM().createQuery("From Agenda where ambiente_id = :ambiente_id and data = :data", Agenda.class);
		query.setParameter("ambiente_id", buscaAmbiente);
		query.setParameter("data", buscaData);
		
		return query.getResultList();
	}

	public List<Agenda> buscarPorHorario(Long buscaAmbiente, Date buscaData, Date buscaInicioDaAula,Date buscaFimDaAula) {
		Query query = getEM().createQuery("From Agenda where ambiente_id = :ambiente_id and data = :data and (inicioDaAula > :inicio and inicioDaAula < :termino) AND (fimDaAula > :inicio and fimDaAula < :termino) ", Agenda.class);
		query.setParameter("ambiente_id", buscaAmbiente);
		query.setParameter("data", buscaData);
		query.setParameter("inicio", buscaInicioDaAula);
		query.setParameter("termino", buscaFimDaAula);
		
		return query.getResultList();
	}

	public List<Agenda> listaAgendaPorAluno(String usuarioId) {
		
		//Query para buscar um ID DE TURMA com base no id do usuario
		Query queryBuscaIdTurma = getEM().createNativeQuery("select turma_id from turma_usuario where alunosTurma_id = :usuarioIdBusca");
		queryBuscaIdTurma.setParameter("usuarioIdBusca", usuarioId);
		List resultadoBuscaIdTurma = queryBuscaIdTurma.getResultList();
		
		//Query para buscar a agenda, com base no id da turma obtido na qiuery de cima
		Query queryBuscaAgenda = getEM().createQuery("From Agenda where turma_id = :turmaId ", Agenda.class);
		queryBuscaAgenda.setParameter("turmaId", resultadoBuscaIdTurma.get(0));
		
		return queryBuscaAgenda.getResultList();
	}

	public List<Turma> listaUsuarioParaEmail(Long id) {
		
		//Query para buscar um ID DE TURMA com base no id do usuario
		Query queryBuscaIdTurma = getEM().createQuery("From Turma where id = :turmaid", Turma.class);
		queryBuscaIdTurma.setParameter("turmaid", id);
		
		return queryBuscaIdTurma.getResultList();
		
	}

	

	

}
