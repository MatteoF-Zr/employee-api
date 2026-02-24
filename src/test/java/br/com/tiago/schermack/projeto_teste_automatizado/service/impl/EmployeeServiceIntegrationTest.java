package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.entity.Employee;
import br.com.tiago.schermack.projeto_teste_automatizado.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Esse teste vai ser responsavel por validar a criacao do funcionario")
    public void DeveCriarEmpregadoERetornarResponseDTO() {
        // arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Matteo", "matteofronza10@gmail.com");
        Employee employeeSaved = new Employee(requestDTO.firstName(), requestDTO.email());
        employeeSaved.setId(1L);

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employeeSaved);

        // act
        EmployeeResponseDTO responseDTO = employeeService.create(requestDTO);

        // assert
        assertEquals(1L, responseDTO.id());
        assertEquals("Matteo", responseDTO.firstName());
        assertEquals("matteofronza10@gmail.com", responseDTO.email());

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Esse teste vai ser responsavel por validar a listagem de funcionarios")
    public void DeveListarERetornarEmpregado() {

        // arrange
        Employee e1 = new Employee("Matteo", "matteo1@example.com");
        e1.setId(1L);

        Employee e2 = new Employee("Luiza", "luiza@example.com");
        e2.setId(2L);

        when(employeeRepository.findAll())
                .thenReturn(List.of(e1, e2));

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

        verify(employeeRepository, times(1)).findAll();
    }

 }
