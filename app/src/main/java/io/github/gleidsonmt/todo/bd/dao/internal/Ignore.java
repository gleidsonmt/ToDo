package io.github.gleidsonmt.todo.bd.dao.internal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description: It's used to indicate this field is not required to persist in
 * the database.
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 22, 2026
 * 
 *         Version History: Initial version
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {

}
