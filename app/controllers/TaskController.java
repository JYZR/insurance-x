package controllers;

import play.*;
import play.mvc.*;

import play.data.*;
import models.*;

import views.html.*;


public class TaskController extends Controller {
  
  static Form<Task> taskForm = Form.form(Task.class);

  public static Result tasks() {
    return ok(
      views.html.tasks.render(Task.all(), taskForm)
    );
  }
  
  public static Result newTask() {
    Form<Task> filledForm = taskForm.bindFromRequest();
    if(filledForm.hasErrors()) {
      return badRequest(
        views.html.tasks.render(Task.all(), filledForm)
      );
    } else {
      Task.create(filledForm.get());
      return redirect(routes.TaskController.tasks());  
    }
  }
  
  public static Result deleteTask(Long id) {
    Task.delete(id);
    return redirect(routes.TaskController.tasks());
  }
  
}