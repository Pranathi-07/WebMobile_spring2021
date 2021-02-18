import { Component, OnInit } from '@angular/core';
import {Todo} from './todo';

@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.css']
})
export class TodosComponent implements OnInit {
  public todos: Todo[] = [];
  public Name: String;
  public Edit: Todo = null;
  public error: Boolean = false;
  constructor() { }

  ngOnInit(): void {
    
  }

  addingTodo() {
    if(this.Name) {
    if (this.Edit && this.Edit['id']) {
      const todo = {
        ...this.Edit,
        name: this.Name
      }
      const index = this.todos.findIndex(t => t.id === todo['id'])
      this.todos[index] = todo
    } else {
      this.todos.push({
        id: Math.random() * 10000,
        name: this.Name,
        completed: false
      })
    }
    this.Name = ''
    this.Edit = null;
    this.error = false;
  } else {
    this.error = true;
    this.Edit = null;
  }

  }

  createTodo(index) {
    console.log(index)
    const todo = this.todos[index];
    todo['completed'] = !todo['completed'];
    this.todos[index]= todo;
  }

  editingTodo(index) {
    const todo = this.todos[index];
    this.Name = todo['name'];
    this.Edit = todo;
  }

  deletingTodo(index) {
    console.log(index)
    this.todos.splice(index,1)
  }
}
