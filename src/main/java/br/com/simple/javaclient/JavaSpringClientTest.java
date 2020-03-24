package br.com.simple.javaclient;

import br.com.simple.model.PageableResponse;
import br.com.simple.model.Student;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.web.client.RestTemplate;

public class JavaSpringClientTest {
    public static void main(String[] args) {

        Student studentPost = new Student();
        studentPost.setName("Test");
        studentPost.setEmail("test@test.com");
        studentPost.setId(23L);

        JavaClientDAO dao = new JavaClientDAO();
        dao.update(studentPost);
    }
}
