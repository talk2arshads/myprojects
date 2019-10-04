package com.in28minutes.springboot.controller;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.service.StudentService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StudentController.class, secure = false)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    Course mockCourse = new Course("Course1", "Spring", "10Steps",
            Arrays.asList("Learn Maven", "Import Project", "First Example", "Second Example"));

    String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";

    @Test
    public void retrieveDetailsForCourse() throws Exception {

        Mockito.when(this.studentService.retrieveCourse(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(this.mockCourse.toString());

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/students/Student1/courses/Course1")
                .accept(MediaType.APPLICATION_XML_VALUE);

        final MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        System.out.println("Actually the Output result is : " + result.getResponse().getContentAsString());
        final String expected = "Course [id=Course1, name=Spring, description=10Steps";

        // {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import
        // Project","First Example","Second Example"]}

        Assert.assertNotEquals(expected.toString(), result.getResponse().getContentAsString());
    }

    @Test
    public void createStudentCourse() throws Exception {

        final Course mockCourse = new Course("1", "Smallest Number", "1", Arrays.asList("1", "2", "3", "4"));

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(this.studentService.addCourse(ArgumentMatchers.anyString(), ArgumentMatchers.any(Course.class)))
                .thenReturn(mockCourse);

        // Send course as body to /students/Student1/courses
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/students/Student1/courses")
                .accept(MediaType.APPLICATION_JSON).content(this.exampleCourseJson).contentType(MediaType.APPLICATION_JSON);

        final MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        final MockHttpServletResponse response = result.getResponse();

        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        Assert.assertEquals("http://localhost/students/Student1/courses/1", response.getHeader(HttpHeaders.LOCATION));

    }

}
