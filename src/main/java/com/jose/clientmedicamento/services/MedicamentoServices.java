/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jose.clientmedicamento.services;

import com.jose.clientmedicamento.entity.Medicamentos;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Feature;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD;
import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME;

/**
 *
 * @author Jose Luis
 */
@Stateless
public class MedicamentoServices{
    Medicamentos medicamentos;
    final String URL="http://127.0.0.1:8081/Hospital/resources/medicamentos";
    
    
    //HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("jose", "jose");
    //HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials("jose", "jose").build();

    protected WebTarget preparar()
    {
        
        return ClientBuilder.newClient().target(URL);
    }
    public Boolean crear(Medicamentos medicamentos)
    {
        try
        {Client client=ClientBuilder.newClient();
            WebTarget preparo=client.target(URL+"/crearmedicamento");
            Invocation.Builder invocationBuiler=preparo
                    .request(MediaType.APPLICATION_JSON);
            Response respuesta=invocationBuiler
                    .post(Entity.entity(medicamentos,MediaType.APPLICATION_JSON));
            return true;
        }catch(Exception e)
        {
            System.out.println("Error"+e.getLocalizedMessage());
            return false;
        }    
    }
    public Boolean actualiza(Medicamentos medicamentos)
    {
        System.out.println("entreeee");
        try
            
        {
            Client client=ClientBuilder.newClient();
            WebTarget preparo=client.target(URL);
            Response respuesta=preparo.path("/actualizarmedicamento")
                    .request()
                    .put((Entity.entity(medicamentos,MediaType.APPLICATION_JSON)));
            System.out.println(respuesta.getStatus());
            return true;
        }catch(Exception e)
        {
            System.out.println("Error"+e.getLocalizedMessage());
            return false;
        }    
    }
    public Boolean eliminar(String id)
    {
        try
        {
            Client client=ClientBuilder.newClient();
            WebTarget preparo=client.target(URL).path("/eliminarrmedicamento").queryParam("id",id);
            Invocation.Builder invocationBuiler=preparo
                    .request(MediaType.APPLICATION_JSON);
            Response respuesta=invocationBuiler
                    .delete();
            System.out.println(respuesta.getStatus());
            return true;
        }catch(Exception e)
        {
            System.out.println("Error"+e.getLocalizedMessage());
            return false;
        }    
    }
    public List<Medicamentos> listar(Medicamentos medicamentos)
    {
        //ClientConfig clientconfig= new ClientConfig();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("jose", "jose");
        
        //clientconfig.register(feature);
        //clientconfig.register(HttpAuthenticationFeature.basicBuilder().credentials("jose", "jose").build());
        //Client client=ClientBuilder.newClient(clientconfig);
       Client client=ClientBuilder.newBuilder().register(feature).newClient();
         //Client client=ClientBuilder.newClient();
        List<Medicamentos> m=client.target(URL+"/obtenermedicamentos")
                    .request(MediaType.APPLICATION_JSON)
                    //.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, "jose")
                    //.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, "jose")
                    .get(new GenericType<List<Medicamentos>>(){});
;
        return m;
    }
    public Medicamentos obtenerporid(String id)
    {
         return preparar()
                .path("/obtenermedicamento/"+id)
                .request(MediaType.APPLICATION_JSON)
                .get(Medicamentos.class);
    }
}
