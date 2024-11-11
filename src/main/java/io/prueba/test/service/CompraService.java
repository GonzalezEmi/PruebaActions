package io.prueba.test.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.prueba.test.domain.Compra;
import io.prueba.test.model.CompraDTO;
import io.prueba.test.repos.CompraRepository;
import io.prueba.test.util.NotFoundException;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(final CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<CompraDTO> findAll() {
        final List<Compra> compras = compraRepository.findAll(Sort.by("id"));
        return compras.stream()
                .map(compra -> mapToDTO(compra, new CompraDTO()))
                .toList();
    }

    public CompraDTO get(final Integer id) {
        return compraRepository.findById(id)
                .map(compra -> mapToDTO(compra, new CompraDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CompraDTO compraDTO) {
        final Compra compra = new Compra();
        mapToEntity(compraDTO, compra);
        return compraRepository.save(compra).getId();
    }

    public void update(final Integer id, final CompraDTO compraDTO) {
        final Compra compra = compraRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(compraDTO, compra);
        compraRepository.save(compra);
    }

    public void delete(final Integer id) {
        compraRepository.deleteById(id);
    }

    // Método específico para actualizar solo el estado
    @Transactional
    public void updateEstado(final Integer id, final String nuevoEstado) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        compra.setEstado(nuevoEstado); // Actualiza solo el estado
        compraRepository.save(compra);
    }

    private CompraDTO mapToDTO(final Compra compra, final CompraDTO compraDTO) {
        compraDTO.setId(compra.getId());
        compraDTO.setFechaCompra(compra.getFechaCompra());
        compraDTO.setEstado(compra.getEstado());
        compraDTO.setTotal(compra.getTotal());
        compraDTO.setDescripcion(compra.getDescripcion());
        return compraDTO;
    }

    private Compra mapToEntity(final CompraDTO compraDTO, final Compra compra) {
        compra.setFechaCompra(compraDTO.getFechaCompra());
        compra.setEstado(compraDTO.getEstado());
        compra.setTotal(compraDTO.getTotal());
        compra.setDescripcion(compraDTO.getDescripcion());
        return compra;
    }

}
