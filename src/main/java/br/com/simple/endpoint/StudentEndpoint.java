package br.com.simple.endpoint;

import br.com.simple.error.ResourceNotFoundException;
import br.com.simple.model.Student;
import br.com.simple.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("students")
public class StudentEndpoint {
    private final StudentRepository studentDAO;
    @Autowired

    public StudentEndpoint(StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findStudentByName(@PathVariable String name){
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable){
        return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @GetMapping (path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable ("id") Long id){
        verifyIfStudentExists(id);
        Student student = studentDAO.findOne(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping

//    Evita a inserção na base quando uma exception é lançada. Não funciona com todos os tipos
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody Student student){ //@Valid comba com @NotEmpty no model
        // Após fazer a requisição desejo continuar visualizando as informações do objeto criado
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
    }

    //@RequestMapping(method = RequestMethod.DELETE)
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id){
        verifyIfStudentExists(id);
        studentDAO.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.PUT)
    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student){
        //Verifica pelo ID, se já existe é update, se não é save normal
        verifyIfStudentExists(student.getId()); //Garante que só pode editar, e não criar um novo
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id){
        if (studentDAO.findOne(id) == null){
//            Esse tratamento abaixo não é bom, ideal é usar exception
//            return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
            throw new ResourceNotFoundException("Student not found for ID "+id);
        }
    }
}
