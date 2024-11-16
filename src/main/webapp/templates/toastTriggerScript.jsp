<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<script type="text/javascript">
    window.addEventListener("load", () => {
        const error = "${error}";
        const success = "${success}";
        if (success !== ""){
            console.log(success);
            const successToastElement = document.getElementById("success-toast");
            const successToast = bootstrap.Toast.getOrCreateInstance(successToastElement);
            successToast.show();
        }
        if (error !== ""){
            console.log(error);
            const errorToastElement = document.getElementById("error-toast");
            const errorToast = bootstrap.Toast.getOrCreateInstance(errorToastElement)
            errorToast.show();
        }
    });
</script>