package br.com.tiago.schermack.projeto_teste_automatizado.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmployeeRequestDTO(
    @NotBlank(message = "O nome é obrigatório") 
    String firstName,

    @NotBlank(message = "O e-mail é obrigatório") 
    @Email(message = "O e-mail é inválido") 
    String email
) {}