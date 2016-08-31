package com.software.hms.projeto.dto;

// TODO: Auto-generated Javadoc

import java.io.Serializable;

/**
 * The Class UsuarioDTO.
 */
public class UsuarioDTO implements Serializable {
	
	 /** The Constant serialVersionUID. */
	 private static final long serialVersionUID = 1L;
	
	 /** The login. */
 	private String login;
	 
 	/** The nome. */
 	private String nome;

	private String email;
	 
 	/** The senha. */
 	private String senha;
	 
 	/** The token. */
 	private String token;
	 
 	/** The permissao. */
 	private String permissao;
	 
 	/** The cpf. */
 	private String cpf;
	 
 	private String dateNascimento;
	 
 	/** The uf. */
 	private String uf;
	 
 	/** The cidade. */
 	private String cidade;
	 
 	/** The complemento. */
 	private String complemento;

	private String sexo;
	 
	 /**
 	 * Instantiates a new usuario DTO.
 	 */
 	public UsuarioDTO(){}

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the nome.
	 *
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Sets the nome.
	 *
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Gets the senha.
	 *
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Sets the senha.
	 *
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the permissao.
	 *
	 * @return the permissao
	 */
	public String getPermissao() {
		return permissao;
	}

	/**
	 * Sets the permissao.
	 *
	 * @param permissao the permissao to set
	 */
	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	/**
	 * Gets the cpf.
	 *
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * Sets the cpf.
	 *
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the dateNascimento
	 */
	public String getDateNascimento() {
		return dateNascimento;
	}

	/**
	 * @param dateNascimento the dateNascimento to set
	 */
	public void setDateNascimento(String dateNascimento) {
		this.dateNascimento = dateNascimento;
	}

	/**
	 * Gets the uf.
	 *
	 * @return the uf
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * Sets the uf.
	 *
	 * @param uf the uf to set
	 */
	public void setUf(String uf) {
		this.uf = uf;
	}

	/**
	 * Gets the cidade.
	 *
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * Sets the cidade.
	 *
	 * @param cidade the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * Gets the complemento.
	 *
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * Sets the complemento.
	 *
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}