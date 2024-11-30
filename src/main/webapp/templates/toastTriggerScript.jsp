<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<script type="text/javascript">
    /**
     * Servlet will set error and success variables if present.
     * The script below will trigger upon page load and display success and/or error
     * messages if they are set.
     */
    window.addEventListener("load", () => {
        const error = "${error}";
        const success = "${success}";
        if (success !== ""){
            const successToastElement = document.getElementById("success-toast");
            const successToast = bootstrap.Toast.getOrCreateInstance(successToastElement);
            successToast.show();
        }
        if (error !== ""){
            console.error(error);
            const errorToastElement = document.getElementById("error-toast");
            const errorToast = bootstrap.Toast.getOrCreateInstance(errorToastElement)
            errorToast.show();
        }
    });
</script>