package io.github.gleidsonmt.todo.view_model;

import io.github.gleidsonmt.todo.model.List;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 20, 2026
 * 
 *         Version History: Initial version
 */
public class ListViewModelConverter {

    public List convert(ListViewModel model) {
        List temp = create(model.getId(), model.getName());

        return temp;
    }

    public List create(long id, String name) {
        var temp = new List(name);
        temp.setId(id);
        return temp;
    }

}
