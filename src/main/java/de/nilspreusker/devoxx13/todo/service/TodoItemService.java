package de.nilspreusker.devoxx13.todo.service;

import de.nilspreusker.devoxx13.todo.model.TodoItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Nils Preusker - n.preusker@gmail.com - http://www.nilspreusker.de
 */
public class TodoItemService {

    @PersistenceContext
    private EntityManager em;

    public void saveTodoItem(TodoItem item) {
        em.persist(item);
    }

    public List<TodoItem> findAllTodoItems() {
        TypedQuery<TodoItem> query = em.createQuery("select t from todo_item t order by t.createdDate", TodoItem.class);
        return query.getResultList();
    }

    public TodoItem findTodoItemById(long id) {
        return em.find(TodoItem.class, id);
    }

    public TodoItem updateTodoItem(TodoItem item) {
        TodoItem itemToUpdate = findTodoItemById(item.getId());
        if (itemToUpdate == null) {
            throw new EntityNotFoundException("Couldn't find todo item with id " + item.getId());
        }
        if (item.getText() != null) {
            itemToUpdate.setText(item.getText());
        }
        if (item.isDone()) {
            itemToUpdate.setDone(true);
        } else {
            itemToUpdate.setDone(false);
        }
        return itemToUpdate;
    }

    public void deleteTodoItem(long id) {
        TodoItem todo = findTodoItemById(id);
        em.remove(todo);
    }

}
