import { parseJSON } from "date-fns"

import { httpClient } from "~/shared/http/http-client"
import { Todo } from "~/todos/api/models/todo"
import { TodoResponse } from "~/todos/api/models/todo-response"

const getTodos = async (): Promise<Todo[]> => {
    const response = await httpClient.get<TodoResponse[]>("/api/todos")

    return response.data.map(mapTodo)
}

export const todosClient = {
    getTodos,
}

const mapTodo = ({ addedAt, completedAt, ...rest }: TodoResponse): Todo => ({
    ...rest,
    addedAt: parseJSON(addedAt),
    completedAt: parseJSON(completedAt),
})
