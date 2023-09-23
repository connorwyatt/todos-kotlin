import { useMutation, UseMutationResult } from "@tanstack/react-query"

import { queryClient } from "~/shared/react-query/query-client"
import { pollForResource } from "~/shared/utilities/poll-for-resource"
import { OptimisticTodo } from "~/todos/api/models/optimistic-todo"
import { Todo } from "~/todos/api/models/todo"
import { TodoDefinition } from "~/todos/api/models/todo-definition"
import { todosClient } from "~/todos/api/todos-client"
import { useTodo } from "~/todos/api/use-todo"
import { useTodos } from "~/todos/api/use-todos"

export const useAddTodoMutation = (): UseMutationResult<void, void, TodoDefinition> => {
    return useMutation({
        mutationFn: async (todoDefinition) => {
            const { id: todoId } = await todosClient.addTodo(todoDefinition)

            await pollForResource<Todo>(() => queryClient.fetchQuery(useTodo.query(todoId)))

            await queryClient.invalidateQueries({ queryKey: [useTodos.queryIdentifier] })
        },
        onMutate: async (todoDefinition) => {
            await Promise.all([
                queryClient.cancelQueries([useTodo.queryIdentifier]),
                queryClient.cancelQueries([useTodos.queryIdentifier]),
            ])

            const useTodosQueryKey = useTodos.query().queryKey

            const previousData = queryClient.getQueryData<(Todo | OptimisticTodo)[]>(useTodosQueryKey)

            queryClient.setQueryData<(Todo | OptimisticTodo)[]>(useTodosQueryKey, (todos) =>
                (todos ?? []).concat({ ...todoDefinition, id: window.crypto.randomUUID(), isOptimistic: true }),
            )

            return { previousData }
        },
        onError: (_error, _todoId, context) => {
            queryClient.setQueryData(useTodos.query().queryKey, context?.previousData)
        },
        onSettled: async () => {
            await queryClient.invalidateQueries({ queryKey: [useTodos.queryIdentifier] })
        },
    })
}
