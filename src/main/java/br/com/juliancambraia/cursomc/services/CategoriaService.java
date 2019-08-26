package br.com.juliancambraia.cursomc.services;

import br.com.juliancambraia.cursomc.domain.Categoria;
import br.com.juliancambraia.cursomc.dto.CategoriaDTO;
import br.com.juliancambraia.cursomc.repositories.CategoriaRepository;
import br.com.juliancambraia.cursomc.services.exceptions.DataIntegrityExeption;
import br.com.juliancambraia.cursomc.services.exceptions.ObjectNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria find(Long id) {
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundExeption("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria categoria) {
        return this.categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        Categoria newCategoria = this.find(categoria.getId());
        updateData(newCategoria, categoria);
        return this.categoriaRepository.save(newCategoria);
    }

    private void updateData(Categoria newCategoria, Categoria categoria) {
        newCategoria.setNome(categoria.getNome());
    }

    public void delete(Long id) {
        try {
            this.categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityExeption("Não é possível excluir uma categoria que possui produtos.");
        }
    }

    public List<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO categoriaDTO) {
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }
}
