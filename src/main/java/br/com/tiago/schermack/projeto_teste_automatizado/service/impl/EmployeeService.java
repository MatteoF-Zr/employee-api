package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.entity.Employee;
import br.com.tiago.schermack.projeto_teste_automatizado.repository.EmployeeRepository;
import br.com.tiago.schermack.projeto_teste_automatizado.service.spec.IEmployeeService;
import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeResponseDTO create(EmployeeRequestDTO dto) {

        Employee employee = new Employee(dto.firstName(), dto.email());

        Employee employeeSaved = employeeRepository.save(employee);

        return new EmployeeResponseDTO(employeeSaved.getId(), employeeSaved.getFirstName(), employeeSaved.getEmail());
    }


    @Override
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO dto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        employee.setFirstName(dto.firstName());
        employee.setEmail    (dto.email());

        return new EmployeeResponseDTO(employee.getId(), employee.getFirstName(), employee.getEmail()
        );
    }


    @Override
    public void delete(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        employeeRepository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO findById(Long id) {

        Employee employee = employeeRepository
                .findById   (id)
                .orElseThrow(EntityNotFoundException::new);

        return new EmployeeResponseDTO(employee.getId(), employee.getFirstName(), employee.getEmail()
        );
    }


    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(e -> new EmployeeResponseDTO(
                        e.getId       (),
                        e.getFirstName(),
                        e.getEmail    ()
                ))
                .toList();
    }

}
