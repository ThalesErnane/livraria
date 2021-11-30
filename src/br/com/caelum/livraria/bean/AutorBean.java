package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

//@ManagedBean anotação sem ultilizar CDI
@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();

	private Livro livro = new Livro();

	private Integer autorId;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Autor getAutor() {
		return autor;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void carregaPelaId() {
		Integer id = this.autor.getId();
		this.autor = new DAO<Autor>(Autor.class).buscaPorId(id);
		if (this.autor == null) {
			this.autor = new Autor();
		}
	}

	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		} else {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}
		this.autor = new Autor();
		return "livro?faces-redirect=true";
	}

	public void remover(Autor autor) {
		System.out.println(this.livro.getAutores().contains(autor));

		boolean existe = new DAO<Autor>(Autor.class).existe(this.autor);
		//this.livro.getAutores().contains(autor) == true
		if (existe) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Nao pode remover autor que contem um livro!"));
		} else {

			new DAO<Autor>(Autor.class).remove(autor);
		}

	}

	public void carregar(Autor autor) {
		this.autor = autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

}
