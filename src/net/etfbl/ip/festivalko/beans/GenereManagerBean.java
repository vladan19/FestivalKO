package net.etfbl.ip.festivalko.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import net.etfbl.ip.festivalko.dao.GenereDAO;
import net.etfbl.ip.festivalko.dao.impl.DAOFactory;
import net.etfbl.ip.festivalko.model.Genere;
import net.etfbl.ip.festivalko.utility.Constants;

@ManagedBean(name = "genereManager")
@ViewScoped
public class GenereManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	GenereDAO genereDAO;
	
	private List<Genere> generes;
	
	private Set<Genere> selected;
	
	@PostConstruct
	private void init() {
		genereDAO = DAOFactory.getInstance().getGenereDAO();
		generes = genereDAO.findAll();
		selected = new HashSet<>();
	}
	
	public void select(Genere genere, String type, String indices) {
		if(indices != null) {
			int[] arrayOfIndices = indicesToArray(indices);
			for(int i: arrayOfIndices) {
				selected.add(generes.get(i));
			}
		}
	}
	
	public void deselect(Genere genere, String type, String indices) {
		if(indices != null) {
			int[] arrayOfIndices = indicesToArray(indices);
			for(int i: arrayOfIndices) {
				selected.remove(generes.get(i));
			}
		}
	}
	
	private int[] indicesToArray(String indices) {
		String[] indicesArray = indices.split(",");
		int[] result = new int[indicesArray.length];
		for(int i=0; i<indicesArray.length; ++i) {
			int index = Integer.valueOf(indicesArray[i]);
			result[i] = index;
		}
		return result;
	}
	
	public void delete() {
		if(!selected.isEmpty()) {
			genereDAO.deleteAll(new ArrayList<>(selected));
			generes.removeAll(selected);
			selected.clear();
		}
	}

	public List<Genere> getGeneres() {
		return generes;
	}

	public void setGeneres(List<Genere> generes) {
		this.generes = generes;
	}
	
	public void edit() {
		Iterator<Genere> iterator = selected.iterator();
		if(iterator.hasNext()) {
			Genere genere = iterator.next();
			String url = Constants.GENERE_URL+genere.getName()+"/";
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
