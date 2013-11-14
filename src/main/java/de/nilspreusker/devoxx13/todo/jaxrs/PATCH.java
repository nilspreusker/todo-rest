package de.nilspreusker.devoxx13.todo.jaxrs;

/**
 * @author Nils Preusker - n.preusker@gmail.com - http://www.nilspreusker.de
 */

import javax.ws.rs.HttpMethod;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nils Preusker - nils.preusker@movingimage24.de
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("PATCH")
public @interface PATCH {
}
