package com.software.hms.projeto.dto;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class UsuarioDTO.
 */
public class UsuarioDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String codigo;

	/** The login. */
	private String login;

	/** The nome. */
	private String nome;

	/** The senha. */
	private String senha;

	/** The email. */
	private String email;

	/** The token. */
	private String token;

	/** The permissao. */
	private String permissao;

	/** The cpf. */
	private String cpf;

	/** The date nascimento. */
	private String dateNascimento;

	/** The uf. */
	private String uf;

	/** The cidade. */
	private String cidade;

	/** The complemento. */
	private String complemento;

	/** The sexo. */
	private String sexo;

	/** The foto. */
	private String foto;

	private String validade;


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
	 * Gets the date nascimento.
	 *
	 * @return the dateNascimento
	 */
	public String getDateNascimento() {
		return dateNascimento;
	}

	/**
	 * Sets the date nascimento.
	 *
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

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the foto
	 */
	public String getFoto() {
		return foto;
	}

	/**
	 * @param foto the foto to set
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}


	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}
}