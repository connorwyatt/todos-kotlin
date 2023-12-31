import { useMutation, UseMutationResult } from "@tanstack/react-query"

import { queryClient } from "~/shared/react-query/query-client"
import { pollForResource } from "~/shared/utilities/poll-for-resource"
import { OptimisticTodo } from "~/todos/api/models/optimistic-todo"
import { Todo } from "~/todos/api/models/todo"
import { todosClient } from "~/todos/api/todos-client"
import { useTodo } from "~/todos/api/use-todo"
import { useTodos } from "~/todos/api/use-todos"

export const useCompleteTodoMutation = (): UseMutationResult<void, void, string> => {
    return useMutation({
        mutationFn: async (todoId) => {
            await todosClient.completeTodo(todoId)

            await pollForResource<Todo>(
                () => queryClient.fetchQuery(useTodo.query(todoId)),
                (todo) => todo?.isComplete === true,
            )

            await queryClient.invalidateQueries({ queryKey: [useTodos.queryIdentifier] })
        },
        onMutate: async (todoId) => {
            await Promise.all([
                queryClient.cancelQueries([useTodo.queryIdentifier]),
                queryClient.cancelQueries([useTodos.queryIdentifier]),
            ])

            const useTodosQueryKey = useTodos.query().queryKey

            const previousData = queryClient.getQueryData<(Todo | OptimisticTodo)[]>(useTodosQueryKey)

            queryClient.setQueryData<(Todo | OptimisticTodo)[]>(
                useTodosQueryKey,
                (todos) =>
                    todos?.map((todo) => {
                        if (!("isOptimistic" in todo)) {
                            return todo
                        }

                        if (todo.id !== todoId) {
                            return todo
                        }

                        return { ...todo, isComplete: true, completedAt: new Date(Date.now()) }
                    }),
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
