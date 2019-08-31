package br.com.juliancambraia.cursomc.services;

import br.com.juliancambraia.cursomc.domain.Categoria;
import br.com.juliancambraia.cursomc.domain.Produto;
import br.com.juliancambraia.cursomc.repositories.CategoriaRepository;
import br.com.juliancambraia.cursomc.repositories.ProdutoRepository;
import br.com.juliancambraia.cursomc.services.exceptions.ObjectNotFoundExeption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;


    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto find(Long id) {
        Produto produto = this.produtoRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundExeption("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));

        return produto;
    }

    public Page<Produto> search(String nome, List<Long> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {

        PageRequest request = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = this.categoriaRepository.findAllById(ids);

        return this.produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, request);

    }

}
