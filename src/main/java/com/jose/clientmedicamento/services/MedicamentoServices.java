/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jose.clientmedicamento.services;

import com.jose.clientmedicamento.entity.Medicamentos;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jose Luis
 */
@Stateless
public class MedicamentoServices {
    Medicamentos medicamentos;
    final String URL="http://127.0.0.1:8081/Hospital/resources/medicamentos";
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
            /*if(respuesta.getStatus()!=201)
            {
                return false;
            }*/
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
            /*Client client=ClientBuilder.newClient();
            WebTarget preparo=client.target(URL);
            Response respuesta=preparo.path("/eliminarrmedicamento")
                    .request(MediaType.APPLICATION_JSON)
                    .delete(Response.class);*/
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
        //try
        //{
        /*Client client=ClientBuilder.newClient();
            lista=client.target(URL+"/obtenermedicamentos")
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Medicamentos>>(){});*/
            /*m=preparar()
                .path("/obtenermedicamentos")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Medicamentos>>(){});*/
            /*if(respuesta.getStatus()!=201)
            {
                return false;
            }*/
        /*    }
        }catch(Exception e)
        {
            System.out.println("Error"+e.getLocalizedMessage());
        }*/
        
        Client client=ClientBuilder.newClient();
        List<Medicamentos> m=client.target(URL+"/obtenermedicamentos")
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Medicamentos>>(){});
        return m;
        //return m;
        
    }
    public Medicamentos obtenerporid(String id)
    {
         return preparar()
                .path("/obtenermedicamento/"+id)
                .request(MediaType.APPLICATION_JSON)
                .get(Medicamentos.class);
    }
}
