package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
import jakarta.persistence.EntityNotFoundException;


@SpringBootTest
@Transactional
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    @DisplayName("Esse teste vai ser responsavel por validar a criacao do funcionario")
    public void DeveCriarEmpregadoERetornarResponseDTO() {
        // arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Matteo", "matteofronza10@gmail.com");

        // act
        EmployeeResponseDTO responseDTO = employeeService.create(requestDTO);

        // assert
        assertEquals(1L, responseDTO.id());
        assertEquals("Matteo", responseDTO.firstName());
        assertEquals("matteofronza10@gmail.com", responseDTO.email());
    }

    @Test
    @DisplayName("Esse teste vai ser responsavel por validar a listagem de funcionarios")
    public void DeveListarERetornarEmpregado() {

        // arrange
        EmployeeRequestDTO e1 = new EmployeeRequestDTO("Matteo", "matteo1@example.com");


        EmployeeRequestDTO e2 = new EmployeeRequestDTO("Luiza", "luiza@example.com");
  
        employeeService.create(e1);
        employeeService.create(e2);
        // act
        List<EmployeeResponseDTO> result = employeeService.findAll();

        // assert
        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).id());
        assertEquals("Matteo", result.get(0).firstName());
        assertEquals("matteo1@example.com", result.get(0).email());

        assertEquals(2L, result.get(1).id());
        assertEquals("Luiza", result.get(1).firstName());
        assertEquals("luiza@example.com", result.get(1).email());


    }
// delete e update
    @Test
    @DisplayName("Esse teste vai ser responsavel por validar a atualizacao de um funcionario")
    public void DeveAtualizarEmpregadoERetornarResponseDTOAtualizado() {
        // arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Matteo", "matteo@example.com");
        EmployeeResponseDTO created = employeeService.create(requestDTO);
        
        EmployeeRequestDTO updateDTO = new EmployeeRequestDTO("Matteo Atualizado", "matteo.novo@example.com");

        // act
        EmployeeResponseDTO updated = employeeService.update(created.id(), updateDTO);

        // assert
        assertEquals(created.id(), updated.id());
        assertEquals("Matteo Atualizado", updated.firstName());
        assertEquals("matteo.novo@example.com", updated.email());
    }

    @Test
    @DisplayName("Esse teste vai ser responsavel por validar a exclusao de um funcionario")
    public void DeveExcluirEmpregado() {
        // arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Matteo", "matteo@example.com");
        EmployeeResponseDTO created = employeeService.create(requestDTO);

        // act
        employeeService.delete(created.id());

        // assert
        assertThrows(EntityNotFoundException.class, () -> employeeService.findById(created.id()));
    }

 }
