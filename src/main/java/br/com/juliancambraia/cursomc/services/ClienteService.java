package br.com.juliancambraia.cursomc.services;

import br.com.juliancambraia.cursomc.domain.Cidade;
import br.com.juliancambraia.cursomc.domain.Cliente;
import br.com.juliancambraia.cursomc.domain.Endereco;
import br.com.juliancambraia.cursomc.domain.enums.TipoClienteEnum;
import br.com.juliancambraia.cursomc.dto.ClienteDTO;
import br.com.juliancambraia.cursomc.dto.ClienteNewDTO;
import br.com.juliancambraia.cursomc.repositories.ClienteRepository;
import br.com.juliancambraia.cursomc.repositories.EnderecoRepository;
import br.com.juliancambraia.cursomc.services.exceptions.DataIntegrityExeption;
import br.com.juliancambraia.cursomc.services.exceptions.ObjectNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Long id) {
        Optional<Cliente> cliente = this.clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundExeption("Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        Cliente cliente1 = this.clienteRepository.save(cliente);
        this.enderecoRepository.saveAll(cliente1.getEnderecos());

        return cliente1;
    }

    public Cliente update(Cliente cliente) {
        Cliente newCliente = this.find(cliente.getId());
        updateData(newCliente, cliente);
        return this.clienteRepository.save(newCliente);
    }

    private void updateData(Cliente newCliente, Cliente cliente) {
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }

    public void delete(Long id) {
        try {
            this.clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityExeption("Não é possível excluir porque há entidades relacionadas.");
        }
    }

    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return this.clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
    }

    public Cliente fromDTO(ClienteNewDTO newDTO) {
        Cliente cliente = new Cliente(null, newDTO.getNome(), newDTO.getEmail(), newDTO.getCpfOuCnpj(), TipoClienteEnum.toEnum(newDTO.getTipoClienteEnum()));
        Cidade cidade = new Cidade(newDTO.getCidadeId(), null, null);
        Endereco endereco = new Endereco(null, newDTO.getLogradouro(), newDTO.getNumero(), newDTO.getComplemento(), newDTO.getBairro(), newDTO.getCep(), cliente, cidade);
        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(newDTO.getTelefone1());

        if (newDTO.getTelefone2() != null) {
            cliente.getTelefones().add(newDTO.getTelefone2());
        }

        if (newDTO.getTelefone3() != null) {
            cliente.getTelefones().add(newDTO.getTelefone3());
        }

        return cliente;

    }
}
