package com.turygin.api.client;

import com.turygin.api.model.ErrorDTO;
import com.turygin.api.model.SectionDTO;
import com.turygin.api.resource.*;
import com.turygin.utility.Config;
import com.turygin.cognito.TokenResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;

import com.turygin.api.model.CourseDTO;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;
import java.util.List;
import java.util.Properties;

/**
 * REST API client implementation.
 */
public class RestClient implements
        ICourseResource, IDepartmentResource, IUserResource, ICartResource, ISectionResource, IInstructorResource {

    /** Base API url. */
    private final String baseUrl;

    /** Jersey REST API client. */
    private final Client client;

    private static final Logger LOG = LogManager.getLogger(RestClient.class);

    /**
     * Instantiates REST API client.
     * @param baseUrl base API url
     */
    public RestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        client = ClientBuilder.newClient();
    }

    /** Checks whether response has a successful status code.
     *
     * @param response REST API response
     * @return true if status is between 200 and 300, false otherwise
     */
    public static boolean isStatusSuccess(Response response) {
        return response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL);
    }

    /**
     * Creates an error message from error DTO in response and closes the response.
     * @param response REST API response
     * @return error message containing status code and description
     */
    public static String getErrorMessage(Response response) {
        ErrorDTO error = response.readEntity(ErrorDTO.class);
        response.close();
        return String.format("Status: %d. Error: %s", error.getStatus(), error.getMessage());
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
     * Helper method that constructs URL for cart endpoints.
     * @return URL for cart endpoints
     */
    private String getCartUrl() {
        return String.format("%s/%s", baseUrl, "cart");
    }

    /**
     * Helper method that constructs URL for user endpoints.
     * @return URL for user endpoints
     */
    private String getUserUrl() {
        return String.format("%s/%s", baseUrl, "user");
    }

    private String getSectionUrl() {
        return String.format("%s/%s", baseUrl, "section");
    }

    private String getInstructorUrl() {
        return String.format("%s/%s", baseUrl, "instructor");
    }

    /**
     * Fetches AWS Cognito JWT token based on authorization code.
     * @param authorizationCode authorization code
     * @return cognito JWT token response
     */
    public TokenResponse getCognitoToken(String authorizationCode)
    {
        Properties webAppProps = Config.getProperties();

        Form form = new Form();
        form.param("grant_type", "authorization_code");
        form.param("client-secret", webAppProps.getProperty("cognito.client.secret"));
        form.param("client_id", webAppProps.getProperty("cognito.client.id"));
        form.param("code", authorizationCode);
        form.param("redirect_uri", webAppProps.getProperty("cognito.redirectURL"));

        String authHeader = webAppProps.getProperty("cognito.client.id") +
                ":" + webAppProps.getProperty("cognito.client.secret");
        String authHeaderEncoded = Base64.getEncoder().encodeToString(authHeader.getBytes());

        try (Response response = client.target(Config.getProperties().
                        getProperty("cognito.oauthURL")).
                request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
                header("Authorization", "Basic " + authHeaderEncoded).
                accept(MediaType.APPLICATION_JSON).
                post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE))) {

            LOG.debug("Cognito token response status {} {}.", response.getStatus(),
                    response.getStatusInfo().getReasonPhrase());

            return response.readEntity(TokenResponse.class);
        }
    }

    /**
     * Fetches course information using unique course ID.
     * @param id unique course ID
     * @return course DTO
     */
    public Response getCourse(long id) {
        return client.target(getCourseUrl()).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).get();
    }

    /**
     * Fetches information about all courses as a list.
     * @return a list of course DTOs
     */
    public Response getAllCourses() {
        return client.target(getCourseUrl()).request(MediaType.APPLICATION_JSON).get();
    }

    /**
     * Fetches information about all courses that match search criteria.
     * @param title partial course title
     * @param departmentId departmentId
     * @return a list of course DTOs
     */
    public Response findCourses(String title, long departmentId) {
        return client.target(String.format("%s/%s", getCourseUrl(), "find")).
                queryParam("title", title).
                queryParam("departmentId", departmentId).
                request(MediaType.APPLICATION_JSON).get();
    }

    /**
     * Removes a course.
     * @param courseId course ID
     * @return 204 status if successful
     */
    public Response deleteCourse(long courseId) {
        return client.target(getCourseUrl()).path(String.valueOf(courseId)).request().delete();
    }

    /**
     * Adds a new course
     * @param courseDTO new course DTO
     * @return 201 status with up-to-date new course DTO
     */
    public Response addCourse(CourseDTO courseDTO) {
        return client.target(getCourseUrl()).
                request(MediaType.APPLICATION_JSON).post(Entity.entity(courseDTO, MediaType.APPLICATION_JSON));
    }

    /**
     * Updates an existing course.
     * @param courseDTO course DTO with the new information
     * @return updated course DTO
     */
    public Response updateCourse(CourseDTO courseDTO) {
        return client.target(getCourseUrl()).
                request(MediaType.APPLICATION_JSON).put(Entity.entity(courseDTO, MediaType.APPLICATION_JSON));
    }

    /**
     * Fetches department information using unique department ID.
     * @param id unique department ID
     * @return department DTO
     */
    public Response getDepartment(long id) {
        return client.target(getDepartmentUrl()).path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).get();
    }

    /**
     * Fetches information about all departments as a list.
     * @return a list of department DTOs
     */
    public Response getAllDepartments() {
        return client.target(getDepartmentUrl()).request(MediaType.APPLICATION_JSON).get();
    }


    /**
     * Creates a user if the user does not exist and loads their information.
     * @param uuid UUID from Cognito
     * @return user DTO
     */
    public Response createUserIfNotExists(String uuid) {

        Form form = new Form();
        form.param("uuid", uuid);

        return client.target(getUserUrl()).
                request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
                accept(MediaType.APPLICATION_JSON_TYPE).
                post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
    }

    /**
     * Get all courses in user's cart.
     * @param userId user ID
     * @return a list of course with sections DTOs
     */
    public Response cartGetCourses(long userId) {
        return client.target(getCartUrl()).path(String.valueOf(userId)).request(MediaType.APPLICATION_JSON).get();
    }

    /**
     * Adds a new course to user's cart.
     * @param userId user ID
     * @param courseId course ID
     * @return course DTO containing information about the course
     */
    public Response cartAddCourse(long userId, long courseId) {
        return client.target(getCartUrl() + "/{userId}/course/{courseId}").
                resolveTemplate("userId", userId).resolveTemplate("courseId", courseId).request().
                post(Entity.json(""));
    }

    /**
     * Removes a course from user's cart.
     * @param userId user ID
     * @param courseId course ID
     * @return 204 status if the course was removed
     */
    public Response cartRemoveCourse(long userId, long courseId) {
        return client.target(getCartUrl() + "/{userId}/course/{courseId}").
                resolveTemplate("userId", userId).resolveTemplate("courseId", courseId).request().
                delete();
    }

    /**
     * Updates selected course sections in user's cart.
     * @param userId user ID
     * @param courseId course ID
     * @param sectionIds a list of selected section IDs
     * @return updated course with sections DTO
     */
    public Response cartUpdateSections(long userId, long courseId, List<Long> sectionIds) {
        return client.target(getCartUrl() + "/{userId}/course/{courseId}").
                resolveTemplate("userId", userId).resolveTemplate("courseId", courseId).
                request(MediaType.APPLICATION_JSON).put(Entity.entity(sectionIds, MediaType.APPLICATION_JSON));
    }

    public Response getAllCourseSections(long courseId) {
        return client.target(getSectionUrl()).path(String.valueOf(courseId)).request(MediaType.APPLICATION_JSON).get();
    }

    public Response deleteSection(long sectionId) {
        return client.target(getSectionUrl()).path(String.valueOf(sectionId)).
                request(MediaType.APPLICATION_JSON).delete();
    }

    public Response addSection(long courseId, SectionDTO sectionDTO) {
        return client.target(getSectionUrl()).path(String.valueOf(courseId)).
                request(MediaType.APPLICATION_JSON).post(Entity.entity(sectionDTO, MediaType.APPLICATION_JSON));
    }

    public Response updateSection(SectionDTO sectionDTO) {
        return client.target(getSectionUrl()).
                request(MediaType.APPLICATION_JSON).put(Entity.entity(sectionDTO, MediaType.APPLICATION_JSON));
    }

    public Response getAllInstructors() {
        return client.target(getInstructorUrl()).request(MediaType.APPLICATION_JSON).get();
    }
}
