package io.prueba.test.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.prueba.test.model.CompraDTO;
import io.prueba.test.service.CompraService;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/compras", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompraResource {

    private final CompraService compraService;

    public CompraResource(final CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public ResponseEntity<List<CompraDTO>> getAllCompras() {
        return ResponseEntity.ok(compraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> getCompra(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(compraService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createCompra(@RequestBody @Valid final CompraDTO compraDTO) {
        final Integer createdId = compraService.create(compraDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateCompra(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final CompraDTO compraDTO) {
        compraService.update(id, compraDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable(name = "id") final Integer id) {
        compraService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/cambiar-estado")
public ResponseEntity<Void> updateEstado(@PathVariable(name = "id") final Integer id,
                                         @RequestBody final String nuevoEstado) {
    compraService.updateEstado(id, nuevoEstado);
    return ResponseEntity.ok().build();
}


}
