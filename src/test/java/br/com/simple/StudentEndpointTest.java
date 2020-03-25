package br.com.simple;

import br.com.simple.model.Student;
import br.com.simple.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentEndpointTest {
    @Autowired
    private TestRestTemplate restTemplate;
    //    DUAS FORMAS DE FAZER VV ^^
    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int port;
    @MockBean
    private StudentRepository studentRepository;

    @TestConfiguration
    static class Config {
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthorization("daneluzzitiago", "teague");
        }
    }

    @Before
    public void setup(){
        Student student = new Student(1L, "Test", "test@test.com");
        BDDMockito.when(studentRepository.findOne(student.getId())).thenReturn(student);
    }

    @Test
    public void listStudentWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        this.restTemplate = this.restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = this.restTemplate.getForEntity("/v1/protected/students/",
                String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void getStudentByIdWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        this.restTemplate = this.restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = this.restTemplate.getForEntity("/v1/protected/students/1",
                String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void listStudentWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
        List<Student> students = asList(
                new Student(1L, "test1", "test1@test.com"),
                new Student(2L, "test2", "test2@test.com"));
        BDDMockito.when(studentRepository.findAll()).thenReturn(students);
        ResponseEntity<String> response = this.restTemplate.getForEntity("/v1/protected/students/",
                String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getStudentByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/v1/protected/students/{id}",
                String.class, 1L);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    public void getStudentByIdWhenUsernameAndPasswordAreCorrectAndStudentDoesNotExistShouldReturnStatusCode200() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/v1/protected/students/{id}",
                String.class, -1);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

//    @Test             DEPRECATED
//    public void deleteStudentWhenUserHasRoleAdminAndStudentExistsShouldReturnStatusCode200() throws Exception {
//        BDDMockito.doNothing().when(studentRepository).delete(1L);
//        ResponseEntity<String> exchange = restTemplate.exchange("/v1/admin/students/{id}", HttpMethod.DELETE,
//                null, String.class, 1L);
//        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
//        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/admin/students/{id}", 1L))
//                .andExpect(MockMvcResultMatchers.status().isFound());
//    }
}
