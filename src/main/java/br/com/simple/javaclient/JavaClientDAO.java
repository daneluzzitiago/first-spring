package br.com.simple.javaclient;

import br.com.simple.handler.RestResponseExceptionHandler;
import br.com.simple.model.PageableResponse;
import br.com.simple.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JavaClientDAO {

    private RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/protected/students")
            .basicAuthorization("daneluzzitiago", "teague")
            .errorHandler(new RestResponseExceptionHandler())
            .build();

    private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/admin/students")
            .basicAuthorization("daneluzzitiago", "teague")
            .errorHandler(new RestResponseExceptionHandler())
            .build();

    public Student findById(long id){
        return restTemplate.getForObject("/{id}", Student.class, id);
//        ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, id);
    }

    public List<Student> listAll(){
        ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null,
            new ParameterizedTypeReference<PageableResponse<Student>>() {
            });
        return exchange.getBody().getContent();
    }

    public void update(Student student){
        System.out.println("Student como argumento: "+student);
        System.out.println("Student atual na base com esse id: "+this.findById(student.getId()));
        restTemplateAdmin.put("/", student);
    }

    public void delete(long id){
        restTemplateAdmin.delete("/{id}", id);
    }

    public Student save(Student student){
        ResponseEntity<Student> exchangePost = restTemplateAdmin.exchange("/",
            HttpMethod.POST, new HttpEntity<>(student, createJSONHeader()), Student.class);
        return exchangePost.getBody();
    }

    private static HttpHeaders createJSONHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}

