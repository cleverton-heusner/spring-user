package cleverton.heusner.adapter.input.constant.doc.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserApiDoc {

    public static final String USER_TAG_NAME = "user.tag.name";
    public static final String USER_TAG_DESCRIPTION = "user.tag.description";

    public static final String USER_OPERATION_FIND_BY_ID_SUMMARY = "user.operation.find-by-id.summary";
    public static final String USER_OPERATION_FIND_BY_ID_DESCRIPTION = "user.operation.find-by-id.description";
    public static final String USER_RESPONSE_FIND_BY_ID_OK_DESCRIPTION = "user.response.find-by-id.ok.description";
    public static final String USER_RESPONSE_FIND_BY_ID_NOT_FOUND_DESCRIPTION = "user.response.find-by-id.not-found.description";
    public static final String USER_RESPONSE_FIND_BY_ID_BAD_REQUEST_DESCRIPTION_ID = "user.response.find-by-id.bad-request.description.id";

    public static final String USER_OPERATION_FIND_BY_CPF_SUMMARY = "user.operation.find-by-cpf.summary";
    public static final String USER_OPERATION_FIND_BY_CPF_DESCRIPTION = "user.operation.find-by-cpf.description";
    public static final String USER_RESPONSE_FIND_BY_CPF_OK_DESCRIPTION = "user.response.find-by-cpf.ok.description";
    public static final String USER_RESPONSE_FIND_BY_CPF_NOT_FOUND_DESCRIPTION = "user.response.find-by-cpf.not-found.description";
    public static final String USER_RESPONSE_FIND_BY_CPF_BAD_REQUEST_DESCRIPTION_CPF = "user.response.find-by-cpf.bad-request.description.cpf";

    public static final String USER_OPERATION_CREATE_SUMMARY = "user.operation.create.summary";
    public static final String USER_OPERATION_CREATE_DESCRIPTION = "user.operation.create.description";
    public static final String USER_OPERATION_CREATED_DESCRIPTION = "user.operation.created.description";
    public static final String USER_OPERATION_PARTIAL_CONTENT_DESCRIPTION = "user.operation.partial-content.description";
    public static final String USER_RESPONSE_CREATE_BAD_REQUEST_DESCRIPTION_NAME = "user.response.create.bad-request.description.name";
    public static final String USER_RESPONSE_CREATE_CONFLICT_DESCRIPTION = "user.response.create.conflict.description";

    public static final String USER_OPERATION_UPDATE_BY_ID_SUMMARY = "user.operation.update-by-id.summary";
    public static final String USER_OPERATION_UPDATE_BY_ID_DESCRIPTION = "user.operation.update-by-id.description";
    public static final String USER_RESPONSE_UPDATE_BY_ID_OK_DESCRIPTION = "user.response.update-by-id.ok.description";
    public static final String USER_RESPONSE_UPDATE_BY_ID_NOT_FOUND_DESCRIPTION = "user.response.update-by-id.not-found.description";
    public static final String USER_RESPONSE_UPDATE_BY_ID_BAD_REQUEST_DESCRIPTION_ID = "user.response.update-by-id.bad-request.description.id";

    public static final String USER_OPERATION_ACTIVATE_BY_ID_SUMMARY = "user.operation.activate-by-id.summary";
    public static final String USER_OPERATION_ACTIVATE_BY_ID_DESCRIPTION = "user.operation.activate-by-id.description";
    public static final String USER_RESPONSE_ACTIVATE_BY_ID_OK_DESCRIPTION = "user.response.activate-by-id.ok.description";
    public static final String USER_RESPONSE_ACTIVATE_BY_ID_NOT_FOUND_DESCRIPTION = "user.response.activate-by-id.not-found.description";
    public static final String USER_RESPONSE_ACTIVATE_BY_ID_BAD_REQUEST_DESCRIPTION_ID = "user.response.activate-by-id.bad-request.description.id";

    public static final String USER_OPERATION_FIND_ALL_SUMMARY = "user.operation.find-all.summary";
    public static final String USER_OPERATION_FIND_ALL_DESCRIPTION = "user.operation.find-all.description";
    public static final String USER_RESPONSE_FIND_ALL_OK_DESCRIPTION = "user.response.find-all.ok.description";

    public static final String USER_OPERATION_FILTER_BY_CPF_OR_ZIPCODE_SUMMARY = "user.operation.filter-by-cpf-or-zipcode.summary";
    public static final String USER_OPERATION_FILTER_BY_CPF_OR_ZIPCODE_DESCRIPTION = "user.operation.filter-by-cpf-or-zipcode.description";
    public static final String USER_RESPONSE_FILTER_BY_CPF_OR_ZIPCODE_OK_DESCRIPTION = "user.response.filter-by-cpf-or-zipcode.ok.description";
}