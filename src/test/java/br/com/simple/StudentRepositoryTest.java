package br.com.simple;

import br.com.simple.model.Student;
import br.com.simple.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData(){
        Student student = new Student("Test", "test@test.com");
        this.studentRepository.save(student);
        Assertions.assertThat(student.getName()).isEqualTo("Test");
        Assertions.assertThat(student.getEmail()).isEqualTo("test@test.com");
        Assertions.assertThat((student.getId())).isNotNull();
    }

    @Test
    public void deleteShouldRemoveData(){
        Student student = new Student("Test", "test@test.com");
        this.studentRepository.save(student);
        this.studentRepository.delete(student);
        Assertions.assertThat(this.studentRepository.findOne(student.getId())).isNull();
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Student student = new Student("Before Update", "beforeupdate@test.com");
        this.studentRepository.save(student);
        student.setName("After Update");
        student.setEmail("afterupdate@test.com");
        this.studentRepository.save(student);
        student = this.studentRepository.findOne(student.getId());
        Assertions.assertThat(student.getName()).isEqualTo("After Update");
        Assertions.assertThat(student.getEmail()).isEqualTo("afterupdate@test.com");
    }
    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase(){
        Student student1 = new Student("TEST", "beforeupdate@test.com");
        Student student2 = new Student("test", "beforeupdate@test.com");
        this.studentRepository.save(student1);
        this.studentRepository.save(student2);
        List<Student> studentList = this.studentRepository.findByNameIgnoreCaseContaining("test");
        Assertions.assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O nome do estudante não pode ser vazio");
        this.studentRepository.save(new Student());
    }

    @Test
    public void createWhenEmailIsNullShouldThrowConstraintViolationException(){
        thrown.expect(ConstraintViolationException.class);
        Student student = new Student();
        student.setName("Test");
        this.studentRepository.save(student);
    }


    @Test
    public void createWhenEmailIsNotValid(){
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Email inválido");
        Student student = new Student();
        student.setName("Test");
        student.setEmail("notValidEmail");
        this.studentRepository.save(student);
    }
}