package br.com.simple.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

@Entity
public class Student extends AbstractEntity{
//    Combar com @Valid no momento de salvar
    @NotEmpty(message = "O nome do estudante não pode ser vazio")
    private String name;
    @Email
    @NotEmpty(message = "O email não pode ser vazio")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
