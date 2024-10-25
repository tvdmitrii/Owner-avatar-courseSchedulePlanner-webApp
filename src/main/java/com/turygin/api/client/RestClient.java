package com.turygin.api.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import com.turygin.api.model.CourseBasicDTO;
import com.turygin.api.model.DepartmentBasicDTO;
import com.turygin.api.resource.ICourseResource;
import com.turygin.api.resource.IDepartmentResource;

import java.util.List;

/**
 * REST API client that implements ICourseRepository interface.
 */
public class RestClient implements ICourseResource, IDepartmentResource {

    private final String baseUrl;
    private final Client client;

    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        client = ClientBuilder.newClient();
    }

    /**
     * Helper method that constructs URL for course endpoints.
     * @return URL for course endpoints
     */
    private String getCourseUrl() {
        return String.format("%s/%s", baseUrl, "course");
    }

    /**
     * Helper method that constructs URL for department endpoints.
     * @return URL for department endpoints
     */
    private String getDepartmentUrl() {
        return String.format("%s/%s", baseUrl, "department");
    }

    /**
     * Fetches course information using unique course ID.
     * @param id unique course ID
     * @return course information
     */
    public CourseBasicDTO getCourse(long id) {
        return client.target(getCourseUrl()).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).
                get(CourseBasicDTO.class);
    }

    /**
     * Fetches information about all courses as a list.
     * @return a list of course information objects
     */
    public List<CourseBasicDTO> getAllCourses() {
        return client.target(getCourseUrl()).request(MediaType.APPLICATION_JSON).
                get(new GenericType<List<CourseBasicDTO>>() {});
    }

    /**
     * Fetches information about all courses that match search criteria.
     * @param title partial course title
     * @param departmentId departmentId
     * @return a list of course information objects
     */
    public List<CourseBasicDTO> findCourses(String title, long departmentId) {
        return client.target(String.format("%s/%s", getCourseUrl(), "find")).
                queryParam("title", title).
                queryParam("departmentId", departmentId).
                request(MediaType.APPLICATION_JSON).
                get(new GenericType<List<CourseBasicDTO>>() {});
    }

    /**
     * Fetches department information using unique department ID.
     * @param id unique department ID
     * @return department information
     */
    public DepartmentBasicDTO getDepartment(long id) {
        return client.target(getDepartmentUrl()).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).
                get(DepartmentBasicDTO.class);
    }

    /**
     * Fetches information about all departments as a list.
     * @return a list of department information objects
     */
    public List<DepartmentBasicDTO> getAllDepartments() {
        return client.target(getDepartmentUrl()).request(MediaType.APPLICATION_JSON).
                get(new GenericType<List<DepartmentBasicDTO>>() {});
    }
}
