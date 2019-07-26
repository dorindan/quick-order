package ro.quickorder.backend.model.enumeration;

public enum RoleName {
    ROLE_USER,      // The default role, a user gets when it signs up
    ROLE_WAITER;    // The role, a page administrator has, aka. Waiter.

    public static RoleName from(String roleName) {
        switch (roleName) {
            case "waiter":
                return ROLE_WAITER;
            case "user":
                return ROLE_USER;
            case "ROLE_WAITER":
                return ROLE_WAITER;
            case "ROLE_USER":
                return ROLE_USER;
            default:
                throw new IllegalStateException("Invalid role name: " + roleName);
        }
    }

    public static String fromEnumToString(RoleName roleName) {
        switch (roleName) {
            case ROLE_WAITER:
                return "waiter";
            case ROLE_USER:
                return "user";
            default:
                return "";
        }
    }
}