package br.com.juliancambraia.cursomc.dto;

import br.com.juliancambraia.cursomc.domain.Cliente;
import br.com.juliancambraia.cursomc.services.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
public class ClienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Length(min = 5, max = 120, message = "O Preenchimento deve ser entre 5 a 120 caracteres.")
    @NotEmpty(message = "Preenchimento obrigatório.")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório.")
    @Email(message = "Email inválido.")
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
