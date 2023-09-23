import { useMutation, UseMutationResult } from "@tanstack/react-query"

import { queryClient } from "~/shared/react-query/query-client"
import { launch } from "~/shared/utilities/launch"
import { pollForResource } from "~/shared/utilities/poll-for-resource"
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

            launch(() => queryClient.invalidateQueries({ queryKey: [useTodos.queryIdentifier] }))
        },
        onMutate: async (todoId) => {
            await Promise.all([
                queryClient.cancelQueries([useTodo.queryIdentifier]),
                queryClient.cancelQueries([useTodos.queryIdentifier]),
            ])

            const useTodosQueryKey = useTodos.query().queryKey

            const previousData = queryClient.getQueryData<Todo[]>(useTodosQueryKey)

            queryClient.setQueryData<Todo[]>(
                useTodosQueryKey,
                (todos) =>
                    todos?.map((todo) => {
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
