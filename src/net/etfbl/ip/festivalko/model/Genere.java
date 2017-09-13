package net.etfbl.ip.festivalko.model;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import net.etfbl.ip.festivalko.dao.GenereDAO;
import net.etfbl.ip.festivalko.dao.impl.DAOFactory;

@ManagedBean(name = "genereBean", eager = false)
@RequestScoped
public class Genere implements Model<Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GenereDAO genereDAO;
	
	private int id;
	private String name;
	
	@PostConstruct
	private void init() {
		genereDAO = DAOFactory.getInstance().getGenereDAO();
	}
	
	public Genere() {
		super();
	}

	public Genere(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Integer getPrimaryKey() {
		return id;
	}

	public void save() {
		if(!genereDAO.exists(id)) {
			genereDAO.save(this);
		}
		else {
			genereDAO.update(this);
		}
	}
	
	public String edit() {
		Genere g = genereDAO.findByName(name);
		if (g==null) {
			//TODO: Staviti da vraca 404
			return null;
		}
		id = g.id;
		return null;
	}
}
