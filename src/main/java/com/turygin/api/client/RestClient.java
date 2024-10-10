package com.turygin.api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.turygin.api.model.CourseDTO;
import com.turygin.utility.Config;

import java.util.List;
import java.util.Properties;

public class RestClient {
    private static final Properties API_PROPS = Config.getProperties();
    private final Client client = ClientBuilder.newClient();

    private static String getCourseUrl() {
        return String.format("%s/%s", API_PROPS.getProperty("rest.client.baseUrl"), "course");
    }

    public CourseDTO getCourse(long id) {
        return client.target(getCourseUrl()).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).get(CourseDTO.class);
    }

    public List<CourseDTO> getAllCourse() {
        return client.target(getCourseUrl()).request(MediaType.APPLICATION_JSON).get(new GenericType<List<CourseDTO>>() {});
    }
}
