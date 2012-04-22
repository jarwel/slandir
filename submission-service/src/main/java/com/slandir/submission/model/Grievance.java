package com.slandir.submission.model;

import com.google.common.base.Preconditions;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import java.util.UUID;

public class Grievance {
    
    private final UUID id;
    private final UUID accountId;
    private final UUID personId;
    private final String author;
    private final String description;
    private final DateTime created;

    @JsonCreator
    public Grievance(
        @JsonProperty("id") UUID id,
        @JsonProperty("accountId") UUID accountId,
        @JsonProperty("personId") UUID personId,
        @JsonProperty("author") String author,
        @JsonProperty("description") String description,
        @JsonProperty("created") DateTime created
    ) {
        this.id = Preconditions.checkNotNull(id, "id cannot be null");
        this.accountId = Preconditions.checkNotNull(accountId, "accountId cannot be null");
        this.personId = Preconditions.checkNotNull(personId, "personId cannot be null");
        this.author = Preconditions.checkNotNull(author, "author cannot be null");
        this.description = Preconditions.checkNotNull(description, "description cannot be null");
        this.created = Preconditions.checkNotNull(created, "created cannot be null");
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("accountId")
    public UUID getAccountId() {
        return accountId;
    }

    @JsonProperty("personId")
    public UUID getPersonId() {
        return personId;
    }

    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("created")
    public DateTime getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grievance grievance = (Grievance) o;

        if (!id.equals(grievance.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
