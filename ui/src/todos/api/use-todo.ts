import { useQuery } from "@tanstack/react-query"

import { Nillable } from "~/shared/types/nillable"
import { todosClient } from "~/todos/api/todos-client"

export const useTodo = (todoId: Nillable<string>) => useQuery({ enabled: todoId != null, ...useTodo.query(todoId) })

useTodo.queryIdentifier = import.meta.url

useTodo.query = (todoId: Nillable<string>) => ({
    queryKey: [useTodo.queryIdentifier, todoId],
    queryFn: async () => await todosClient.getTodo(todoId!),
})
