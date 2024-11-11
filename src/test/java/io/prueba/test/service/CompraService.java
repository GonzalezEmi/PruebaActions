package io.prueba.test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import io.prueba.test.domain.Compra;
import io.prueba.test.model.CompraDTO;
import io.prueba.test.repos.CompraRepository;
import io.prueba.test.util.NotFoundException;

class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @InjectMocks
    private CompraService compraService;

    private Compra compra;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear una compra de ejemplo para las pruebas
        compra = new Compra();
        compra.setId(1);
        compra.setEstado("PENDIENTE");
        compra.setFechaCompra(null); // Se puede poner una fecha real si es necesario
        compra.setTotal(100.0);
        compra.setDescripcion("Compra de ejemplo");
    }

    @Test
    void testFindAll() {
        // Mockear el comportamiento del repositorio
        when(compraRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(compra));

        // Llamar al servicio
        List<CompraDTO> result = compraService.findAll();

        // Verificar el resultado
        assertEquals(1, result.size());
        assertEquals("PENDIENTE", result.get(0).getEstado());
        verify(compraRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void testGetCompraById() {
        // Mockear el comportamiento del repositorio
        when(compraRepository.findById(1)).thenReturn(Optional.of(compra));

        // Llamar al servicio
        CompraDTO result = compraService.get(1);

        // Verificar el resultado
        assertEquals("PENDIENTE", result.getEstado());
        verify(compraRepository, times(1)).findById(1);
    }

    @Test
    void testGetCompraById_NotFound() {
        // Simular el caso en que no se encuentra la compra
        when(compraRepository.findById(1)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción NotFoundException
        assertThrows(NotFoundException.class, () -> compraService.get(1));
        verify(compraRepository, times(1)).findById(1);
    }

    @Test
    void testCreateCompra() {
        // Mockear el comportamiento del repositorio
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);

        // Llamar al servicio
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setEstado("PENDIENTE");
        compraDTO.setTotal(100.0);
        compraDTO.setDescripcion("Compra de prueba");

        Integer createdId = compraService.create(compraDTO);

        // Verificar el resultado
        assertEquals(1, createdId);
        verify(compraRepository, times(1)).save(any(Compra.class));
    }

    @Test
    void testUpdateCompra() {
        // Mockear el comportamiento del repositorio
        when(compraRepository.findById(1)).thenReturn(Optional.of(compra));
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);

        // Llamar al servicio
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setEstado("APROBADO");
        compraDTO.setTotal(120.0);
        compraDTO.setDescripcion("Compra actualizada");

        compraService.update(1, compraDTO);

        // Verificar que el repositorio ha sido llamado para guardar la compra actualizada
        verify(compraRepository, times(1)).save(any(Compra.class));
    }

    @Test
    void testUpdateCompra_NotFound() {
        // Simular que la compra no existe
        when(compraRepository.findById(1)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción NotFoundException
        assertThrows(NotFoundException.class, () -> compraService.update(1, new CompraDTO()));
        verify(compraRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteCompra() {
        // Mockear el comportamiento del repositorio
        when(compraRepository.existsById(1)).thenReturn(true);

        // Llamar al servicio
        compraService.delete(1);

        // Verificar que el repositorio ha sido llamado para eliminar la compra
        verify(compraRepository, times(1)).deleteById(1);
    }

    @Test
    void testUpdateEstado() {
        // Mockear el comportamiento del repositorio
        when(compraRepository.findById(1)).thenReturn(Optional.of(compra));
        when(compraRepository.save(any(Compra.class))).thenReturn(compra);

        // Llamar al servicio para actualizar solo el estado
        compraService.updateEstado(1, "APROBADO");

        // Verificar que el estado de la compra fue actualizado
        assertEquals("APROBADO", compra.getEstado());
        verify(compraRepository, times(1)).save(any(Compra.class));
    }

    @Test
    void testUpdateEstado_NotFound() {
        // Simular que la compra no existe
        when(compraRepository.findById(1)).thenReturn(Optional.empty());

        // Verificar que se lanza la excepción NotFoundException
        assertThrows(NotFoundException.class, () -> compraService.updateEstado(1, "APROBADO"));
        verify(compraRepository, times(1)).findById(1);
    }
}
