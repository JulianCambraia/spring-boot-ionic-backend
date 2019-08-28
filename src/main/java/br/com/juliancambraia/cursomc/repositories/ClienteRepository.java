package br.com.juliancambraia.cursomc.repositories;

import br.com.juliancambraia.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Adicionando esta anotação está busca não será envolvida em transações no banco de dados, isto torna mais rápido a busca e
     * não permite o locking de banco de dados.
     *
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    Cliente findByEmail(String email);
}
