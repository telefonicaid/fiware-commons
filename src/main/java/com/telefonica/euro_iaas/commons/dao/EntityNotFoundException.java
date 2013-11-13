package com.telefonica.euro_iaas.commons.dao;

/**
 *
 */
@SuppressWarnings("serial")
public class EntityNotFoundException extends Exception {

    private String errorMsg;

    /**
     * Constructor of the class.
     *
     * @param entity
     *            The requested entity
     * @param primaryKeyFieldName
     *            The name of the primary key attribute
     * @param primaryKeyFieldValue
     *            The value of the primary key attribute
     */
    @SuppressWarnings("unchecked")
    public EntityNotFoundException(Class entity, String primaryKeyFieldName,
            Object primaryKeyFieldValue) {
        this(entity, new String[] { primaryKeyFieldName },
                new Object[] { primaryKeyFieldValue });
    }

    /**
     * Constructor of the class.
     *
     * @param entity
     *            The requested entity
     * @param primaryKeyFieldNames
     *            The names of all the primary key fields
     * @param primaryKeyFieldValues
     *            The values of all the primary key fields
     */
    @SuppressWarnings("unchecked")
    public EntityNotFoundException(Class entity, String[] primaryKeyFieldNames,
            Object[] primaryKeyFieldValues) {

        StringBuffer errorMessage = new StringBuffer("No entity "
                + entity.getName() + " found with the following criteria: [");

        int i;
        for (i = 0; i < primaryKeyFieldNames.length - 1; i++) {
            errorMessage.append(primaryKeyFieldNames[i] + " = ");
            errorMessage.append(primaryKeyFieldValues[i] + ", ");
        }
        errorMessage.append(primaryKeyFieldNames[i] + " = ");
        errorMessage.append(primaryKeyFieldValues[i]).append("].");

        if (getCause() != null) {
            errorMessage.append(" Caused by: " + getCause().getMessage());
        }

        this.errorMsg = errorMessage.toString();
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

}
