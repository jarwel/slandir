package com.slandir.submission.dao;

import com.slandir.submission.model.Grievance;

import java.util.List;
import java.util.UUID;

public interface GrievanceDao {
    List<Grievance> fetchByPerson(UUID personId);
    List<Grievance> fetchByAccount(UUID accountId);
    void save(Grievance grievance);
    void remove(UUID grievanceId);
}
