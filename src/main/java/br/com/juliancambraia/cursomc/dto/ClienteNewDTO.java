package br.com.juliancambraia.cursomc.dto;

import br.com.juliancambraia.cursomc.services.validation.ClienteInsert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteInsert
public class ClienteNewDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Length(min = 5, max = 120, message = "O Preenchimento deve ser entre 5 a 120 caracteres.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    private String nome;

    @Email(message = "Email inválido.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório.")
    private String cpfOuCnpj;

    private Long tipoClienteEnum;

    @NotEmpty(message = "Preenchimento obrigatório.")
    private String logradouro;

    @NotEmpty(message = "Preenchimento obrigatório.")
    private String numero;

    private String complemento;

    private String bairro;

    @NotEmpty(message = "Preenchimento obrigatório.")
    private String cep;

    private String telefone1;

    private String telefone2;

    private String telefone3;

    private Long cidadeId;

    public ClienteNewDTO() {
    }

    public ClienteNewDTO(String nome, String email, String cpfOuCnpj, Long tipoClienteEnum, String logradouro, String numero, String complemento, String bairro, String cep, Long cidadeId) {
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipoClienteEnum = tipoClienteEnum;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.cidadeId = cidadeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public Long getTipoClienteEnum() {
        return tipoClienteEnum;
    }

    public void setTipoClienteEnum(Long tipoClienteEnum) {
        this.tipoClienteEnum = tipoClienteEnum;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Long cidadeId) {
        this.cidadeId = cidadeId;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }
}
