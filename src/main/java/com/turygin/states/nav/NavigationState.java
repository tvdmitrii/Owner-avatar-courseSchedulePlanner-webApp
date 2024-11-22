package com.turygin.states.nav;

public class NavigationState {

    public final static String HOME = "browser/load";
    public final static NavigationState BROWSER =
            new NavigationState("browser", "browser/load", "/browseCourses.jsp");
    public final static NavigationState CART =
            new NavigationState("cart", "cart/view", "/viewCart.jsp");
    public final static NavigationState CATALOG =
            new NavigationState("catalog", "catalog", "/editCatalog.jsp");

    private String name;
    private String defaultServlet;
    private String jspPage;

    public NavigationState(String name, String defaultServlet, String jspPage) {
        this.name = name;
        this.defaultServlet = defaultServlet;
        this.jspPage = jspPage;
    }

    public String getDefaultServlet() {
        return defaultServlet;
    }

    public void setDefaultServlet(String defaultServlet) {
        this.defaultServlet = defaultServlet;
    }

    public String getJspPage() {
        return jspPage;
    }

    public void setJspPage(String jspPage) {
        this.jspPage = jspPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
