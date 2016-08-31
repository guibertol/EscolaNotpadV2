package br.escolanotpad.sc.mb;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.escolanotpad.sc.model.AmbienteRN;
import br.escolanotpad.sc.model.entity.Ambiente;

@ViewScoped
@ManagedBean
public class AmbienteMB {
	private List<Ambiente> listaAmbientes;
	private AmbienteRN ambienteRN;
	private Ambiente ambiente;
	
	@PostConstruct
	public void init(){
		ambienteRN = new AmbienteRN();
		ambiente = new Ambiente();		
	}
	
	public List<Ambiente> getListaAmbientes() {
		if(listaAmbientes == null){
			listaAmbientes = ambienteRN.listar();
		}
		return listaAmbientes;
	}
	
	public void setListaAmbientes(List<Ambiente> listaAmbientes) {
		this.listaAmbientes = listaAmbientes;
	}
	public AmbienteRN getAmbienteRN() {
		return ambienteRN;
	}
	public void setAmbienteRN(AmbienteRN ambienteRN) {
		this.ambienteRN = ambienteRN;
	}
	public Ambiente getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}
	
	public String salvar() throws SQLException{
		ambienteRN.salvar(ambiente);
		listaAmbientes = null;
		return "";
	}
	
	public String excluir(String id){
		Long idExcluir = Long.parseLong(id);
		ambienteRN.excluir(idExcluir);
		listaAmbientes = null;
		return"";
	}
	
	
	

}