package com.slandir.submission;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.proofpoint.discovery.client.DiscoveryBinder;
import com.slandir.submission.dao.GrievanceDao;
import com.slandir.submission.resource.GrievanceResource;

public class MainModule implements Module {
    
    private static final String SERVICE_NAME = "submission";
    
    @Override
    public void configure(Binder binder) {
        binder.requireExplicitBindings();
        binder.disableCircularProxies();
        
        binder.bind(GrievanceDao.class).in(Scopes.SINGLETON);
        
        binder.bind(GrievanceResource.class).in(Scopes.SINGLETON);
        
        //eventBinder(binder).bindEventClient(DescriptorEvent.class);

        DiscoveryBinder.discoveryBinder(binder).bindHttpAnnouncement(SERVICE_NAME);
    }
}
