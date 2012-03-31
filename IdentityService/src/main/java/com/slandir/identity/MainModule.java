package com.slandir.identity;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

import com.proofpoint.discovery.client.DiscoveryBinder;
import com.slandir.identity.dao.PersonDao;
import com.slandir.identity.resource.PersonResource;

public class MainModule implements Module {
    
    private static final String SERVICE_NAME = "identity";
    
    @Override
    public void configure(Binder binder) {
        binder.requireExplicitBindings();
        binder.disableCircularProxies();
        
        binder.bind(PersonDao.class).in(Scopes.SINGLETON);
        
        binder.bind(PersonResource.class).in(Scopes.SINGLETON);
        
        //eventBinder(binder).bindEventClient(DescriptorEvent.class);

        DiscoveryBinder.discoveryBinder(binder).bindHttpAnnouncement(SERVICE_NAME);
    }
}
