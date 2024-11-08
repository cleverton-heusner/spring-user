package cleverton.heusner.adapter.input.constant.doc.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginApiDoc {

    public static final String LOGIN_TAG_NAME = "login.tag.name";
    public static final String LOGIN_TAG_DESCRIPTION = "login.tag.description";

    public static final String LOGIN_OPERATION_AUTHENTICATION_SUMMARY = "login.operation.authentication.summary";
    public static final String LOGIN_OPERATION_AUTHENTICATION_DESCRIPTION = "login.operation.authentication.description";
    public static final String LOGIN_OPERATION_AUTHENTICATED_DESCRIPTION = "login.operation.authenticated.description";

    public static final String LOGIN_OPERATION_REGISTER_SUMMARY = "login.operation.register.summary";
    public static final String LOGIN_OPERATION_REGISTER_DESCRIPTION = "login.operation.register.description";
    public static final String LOGIN_OPERATION_REGISTERED_DESCRIPTION = "login.operation.registered.description";

    public static final String LOGIN_OPERATION_FIND_ALL_SUMMARY = "login.operation.find-all.summary";
    public static final String LOGIN_OPERATION_FIND_ALL_DESCRIPTION = "login.operation.find-all.description";
    public static final String LOGIN_RESPONSE_FIND_ALL_OK_DESCRIPTION = "login.response.find-all.ok.description";
}