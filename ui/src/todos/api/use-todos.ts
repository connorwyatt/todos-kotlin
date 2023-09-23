import { useQuery } from "@tanstack/react-query"

import { OptimisticTodo } from "~/todos/api/models/optimistic-todo"
import { Todo } from "~/todos/api/models/todo"
import { todosClient } from "~/todos/api/todos-client"

export const useTodos = () => useQuery<(Todo | OptimisticTodo)[]>({ ...useTodos.query() })

useTodos.queryIdentifier = import.meta.url

useTodos.query = () => ({
    queryKey: [useTodos.queryIdentifier],
    queryFn: async () => await todosClient.getTodos(),
})
