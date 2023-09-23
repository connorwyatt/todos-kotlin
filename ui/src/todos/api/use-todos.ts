import { useQuery } from "@tanstack/react-query"

import { todosClient } from "~/todos/api/todos-client"

export const useTodos = () => useQuery({ ...useTodos.query() })

useTodos.queryIdentifier = import.meta.url

useTodos.query = () => ({
    queryKey: [useTodos.queryIdentifier],
    queryFn: async () => await todosClient.getTodos(),
})
