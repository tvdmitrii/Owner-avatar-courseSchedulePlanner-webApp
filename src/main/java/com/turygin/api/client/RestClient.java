package com.turygin.api.client;

import com.turygin.api.model.CourseWithSectionsDTO;
import com.turygin.api.model.UserDTO;
import com.turygin.api.resource.ICartResourse;
import com.turygin.api.resource.IUserResource;
import com.turygin.utility.Config;
import com.turygin.cognito.TokenResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import com.turygin.api.model.CourseBasicDTO;
import com.turygin.api.model.DepartmentBasicDTO;
import com.turygin.api.resource.ICourseResource;
import com.turygin.api.resource.IDepartmentResource;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;
import java.util.List;
import java.util.Properties;

/**
 * REST API client that implements ICourseRepository interface.
 */
public class RestClient implements ICourseResource, IDepartmentResource, IUserResource, ICartResourse {

    private final String baseUrl;
    private final Client client;

    private static final Logger LOG = LogManager.getLogger(RestClient.class);

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

        Response response = client.target(Config.getProperties().
                getProperty("cognito.oauthURL")).
                request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
                header("Authorization", "Basic " + authHeaderEncoded).
                accept(MediaType.APPLICATION_JSON).
                post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        LOG.debug("Response status {} {}.", response.getStatus(),
                response.getStatusInfo().getReasonPhrase());

        return response.readEntity(TokenResponse.class);
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


    /**
     * Create user if does not exist and get their information.
     * @param uuid UUID from Cognito
     * @return user information object
     */
    public UserDTO createUserIfNotExists(String uuid) {

        Form form = new Form();
        form.param("uuid", uuid);

        Response response =  client.target(getUserUrl()).
                request(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
                accept(MediaType.APPLICATION_JSON_TYPE).
                post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        LOG.debug("Response status {} {}.", response.getStatus(),
                response.getStatusInfo().getReasonPhrase());

        return response.readEntity(UserDTO.class);
    }

    public List<CourseWithSectionsDTO> cartGetCourses(long userId) {
        return client.target(getCartUrl()).path(String.valueOf(userId)).request(MediaType.APPLICATION_JSON).
                get(new GenericType<List<CourseWithSectionsDTO>>() {});
    }

    @Override
    public void cartAddCourseToCart(long l, long l1) {

    }

    @Override
    public void cartRemoveCourse(long l, long l1) {

    }

    @Override
    public void cartUpdateCourse(long l, long l1, List<Long> list) {

    }
}
