import { useQuery } from "@tanstack/react-query"

import { todosClient } from "~/todos/api/todos-client"

export const useTodos = () =>
    useQuery({ queryKey: [import.meta.url], queryFn: async () => await todosClient.getTodos() })
