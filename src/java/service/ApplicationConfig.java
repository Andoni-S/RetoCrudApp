/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.AdminFacadeREST.class);
        resources.add(service.EventFacadeREST.class);
        resources.add(service.GameFacadeREST.class);
        resources.add(service.OrganizerFacadeREST.class);
        resources.add(service.PlayerEventFacadeREST.class);
        resources.add(service.PlayerFacadeREST.class);
        resources.add(service.PlayerTeamFacadeREST.class);
        resources.add(service.TeamEventFacadeREST.class);
        resources.add(service.TeamFacadeREST.class);
        resources.add(service.UserFacadeREST.class);
    }
    
}
