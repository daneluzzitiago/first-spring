package br.com.simple.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

@Entity
public class Student extends AbstractEntity{
//    Combar com @Valid no momento de salvar
    @NotEmpty(message = "O nome do estudante não pode ser vazio")
    private String name;
    @Email(message = "Email inválido")
    @NotEmpty(message = "O email não pode ser vazio")
    private String email;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Student(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
