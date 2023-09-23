import { parseJSON } from "date-fns"

import { httpClient } from "~/shared/http/http-client"
import { Todo } from "~/todos/api/models/todo"
import { TodoResponse } from "~/todos/api/models/todo-response"

const getTodos = async (): Promise<Todo[]> => {
    const response = await httpClient.get<TodoResponse[]>("/api/todos")

    return response.data.map(mapTodo)
}

const getTodo = async (todoId: string): Promise<Todo> => {
    const response = await httpClient.get<TodoResponse>(`/api/todos/${todoId}`)

    return mapTodo(response.data)
}

const completeTodo = async (todoId: string): Promise<void> => {
    await httpClient.post(`/api/todos/${todoId}/actions/complete`)
}

export const todosClient = {
    getTodos,
    getTodo,
    completeTodo,
}

const mapTodo = ({ addedAt, completedAt, ...rest }: TodoResponse): Todo => ({
    ...rest,
    addedAt: parseJSON(addedAt),
    completedAt: parseJSON(completedAt),
})
