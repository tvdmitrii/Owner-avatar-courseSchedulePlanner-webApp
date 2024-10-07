package com.turygin.api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.turygin.api.model.CourseDTO;

import java.util.List;

public class RestClient {
    private static final String REST_URI = "http://localhost:8080/course_schedule_planner_api_war/api/course";
    private final Client client = ClientBuilder.newClient();

    public CourseDTO getCourse(long id) {
        return client.target(REST_URI).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).get(CourseDTO.class);
    }

    public List<CourseDTO> getAllCourse() {
        return client.target(REST_URI).request(MediaType.APPLICATION_JSON).get(new GenericType<List<CourseDTO>>() {});
    }
}
