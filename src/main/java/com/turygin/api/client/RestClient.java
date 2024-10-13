package com.turygin.api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.turygin.api.model.CourseDTO;
import com.turygin.api.repository.ICourseRepository;
import com.turygin.utility.Config;

import java.util.List;
import java.util.Properties;

/**
 * REST API client that implements ICourseRepository interface.
 */
public class RestClient implements ICourseRepository {
    private static final Properties API_PROPS = Config.getProperties();
    private final Client client = ClientBuilder.newClient();

    /**
     * Helper method that constructs URL for course endpoints.
     * @return URL for course endpoints
     */
    private static String getCourseUrl() {
        return String.format("%s/%s", API_PROPS.getProperty("rest.client.baseUrl"), "course");
    }

    /**
     * Fetches course information using unique course ID.
     * @param id unique course ID
     * @return course information
     */
    public CourseDTO getCourse(long id) {
        return client.target(getCourseUrl()).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).get(CourseDTO.class);
    }

    /**
     * Fetches information about all courses as a list.
     * @return a list of course information objects
     */
    public List<CourseDTO> getAllCourses() {
        return client.target(getCourseUrl()).request(MediaType.APPLICATION_JSON).get(new GenericType<List<CourseDTO>>() {});
    }
}
