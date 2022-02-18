package model;

/**
 * Model class of contact based on table object contact from database.
 * Contains string fields of contact name and contact email.
 * Contains integer field for contact id.
 * Has getters/setters for all fields.
 *
 */

public class Contact {

    int contactId;
    String contactName;
    String contactEmail;

    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
