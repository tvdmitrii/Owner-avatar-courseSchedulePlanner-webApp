package com.turygin.states.nav;

/**
 * Class that represents navigation state.
 */
public class NavigationState {

    /**
     * Home page.
     */
    public final static String HOME = "browser/load";
    /**
     * Course browser page.
     */
    public final static NavigationState BROWSER =
            new NavigationState("browser", "browser/load", "/browseCourses.jsp");
    /**
     * Course cart page.
     */
    public final static NavigationState CART =
            new NavigationState("cart", "cart/view", "/viewCart.jsp");
    /**
     * Course catalog page.
     */
    public final static NavigationState CATALOG =
            new NavigationState("catalog", "catalog", "/editCatalog.jsp");
    /**
     * Schedule page.
     */
    public final static NavigationState SCHEDULE =
            new NavigationState("schedule", "schedule", "/schedule.jsp");

    /** Page name. */
    private final String name;

    /** Default page servlet. */
    private final String defaultServlet;

    /** JSP for data display. */
    private final String jspPage;

    /**
     * Instantiates a new Navigation state.
     * @param name           the name
     * @param defaultServlet the default servlet
     * @param jspPage        the jsp page
     */
    private NavigationState(String name, String defaultServlet, String jspPage) {
        this.name = name;
        this.defaultServlet = defaultServlet;
        this.jspPage = jspPage;
    }

    /**
     * Gets default servlet.
     * @return the default servlet
     */
    public String getDefaultServlet() {
        return defaultServlet;
    }

    /**
     * Gets jsp page.
     * @return the jsp page
     */
    public String getJspPage() {
        return jspPage;
    }

    /**
     * Gets name.
     * @return the name
     */
    public String getName() {
        return name;
    }
}
