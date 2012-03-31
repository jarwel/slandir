package com.slandir.submission.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.slandir.submission.model.Grievance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GrievanceDao {

    private final Map<UUID, List<Grievance>> personIndex = Maps.newHashMap();
    private final Map<UUID, List<Grievance>> accountIndex = Maps.newHashMap();

    public List<Grievance> fetchByPerson(UUID personId) {
        if(personIndex.get(personId) == null) {
            return Lists.newArrayList();
        }
        return personIndex.get(personId);
    }

    public List<Grievance> fetchByAccount(UUID accountId) {
        if(accountIndex.get(accountId)  == null) {
            return Lists.newArrayList();
        }
        return accountIndex.get(accountId);
    }

    public void save(Grievance grievance) {
        if(personIndex.get(grievance.getPersonId()) == null) {
            personIndex.put(grievance.getPersonId(), new ArrayList<Grievance>());
        }
        personIndex.get(grievance.getPersonId()).add(grievance);

        if(accountIndex.get(grievance.getAccountId()) == null) {
            accountIndex.put(grievance.getAccountId(), new ArrayList<Grievance>());
        }
        accountIndex.get(grievance.getAccountId()).add(grievance);
    }
    
    public void remove(UUID grievanceId) {
        for(List<Grievance> grievances : personIndex.values()) {
            for(Grievance grievance : grievances) {
                if(grievanceId.equals(grievance.getId())) {
                    grievances.remove(grievance);
                    break;
                }
            }
        }

        for(List<Grievance> grievances : accountIndex.values()) {
            for(Grievance grievance : grievances) {
                if(grievanceId.equals(grievance.getId())) {
                    grievances.remove(grievance);
                    break;
                }
            }
        }
    }
}
