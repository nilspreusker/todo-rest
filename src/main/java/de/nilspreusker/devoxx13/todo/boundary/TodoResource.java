package de.nilspreusker.devoxx13.todo.boundary;

import de.nilspreusker.devoxx13.todo.jaxrs.PATCH;
import de.nilspreusker.devoxx13.todo.model.TodoItem;
import de.nilspreusker.devoxx13.todo.service.TodoItemService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.keycloak.KeycloakSecurityContext;


/**
 * @author Nils Preusker - n.preusker@gmail.com - http://www.nilspreusker.de
 */

@Path("todo")
@Stateless
@SecurityDomain("keycloak")
public class TodoResource {

    @Inject
    private TodoItemService service;

    @Context private HttpServletRequest servletRequest;

    // create
    @POST
    @RolesAllowed("user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTodo(@Context UriInfo uriInfo, TodoItem todo) throws URISyntaxException {
        service.saveTodoItem(todo);
        return Response.created(new URI(uriInfo.getRequestUri() + "/" + todo.getId())).build();
    }

    // get all
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Response getAll() {

        // Showcase how to access user info provided by keycloak
        KeycloakSecurityContext ctx = (KeycloakSecurityContext) servletRequest.getAttribute(KeycloakSecurityContext.class.getName());
        System.out.println("----");
        System.out.println("User name: " + ctx.getToken().getPreferredUsername());
        System.out.println("----");
        System.out.println("User is user: " + servletRequest.isUserInRole("user"));
        System.out.println("User is admin: " + servletRequest.isUserInRole("admin"));
        System.out.println("----");
        System.out.println("User name: " + servletRequest.getRemoteUser());
        System.out.println("----");

        List<TodoItem> items = service.findAllTodoItems();
        return Response.ok(items).build();
    }

    // get by id
    @GET
    @Path("/{id}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) {
        try {
            TodoItem item = service.findTodoItemById(id);
            return Response.ok(item).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // update
    @PATCH
    @Path("/{id}")
    @RolesAllowed("user")
    public Response update(@PathParam("id") long id, TodoItem item) {
        try {
            service.updateTodoItem(item);
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // delete
    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response delete(@PathParam("id") long id) {
        try {
            service.deleteTodoItem(id);
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
