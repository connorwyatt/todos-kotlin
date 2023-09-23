import { FC, useCallback } from "react"

import { DistanceToNow } from "~/shared/components/dates/distance-to-now"
import { Checkbox } from "~/shared/components/inputs/checkbox"
import { Stack } from "~/shared/components/layout/stack"
import { Paragraph, ParagraphSubtext } from "~/shared/components/typography/typography"
import { OptimisticTodo } from "~/todos/api/models/optimistic-todo"
import { Todo } from "~/todos/api/models/todo"
import { useCompleteTodoMutation } from "~/todos/api/use-complete-todo-mutation"
import { Container } from "~/todos/components/todos-list-item.styles"

export interface TodosListItemProps {
    todo: Todo | OptimisticTodo
}

export const TodosListItem: FC<TodosListItemProps> = ({ todo }) => {
    const { mutate } = useCompleteTodoMutation()

    const isOptimistic = "isOptimistic" in todo

    const handleChange = useCallback(() => {
        mutate(todo.id)
    }, [mutate, todo])

    return (
        <Container $isOptimistic={isOptimistic}>
            <Stack.Horizontal spacing="medium" align="center">
                <Checkbox
                    size="large"
                    value={!isOptimistic ? todo.isComplete : false}
                    onChange={handleChange}
                    displayOnly={!isOptimistic ? todo.isComplete : false}
                />

                <Stack.Vertical>
                    <Paragraph>{todo.title}</Paragraph>

                    <ParagraphSubtext color="secondary" italicized>
                        {!isOptimistic ? (
                            <>
                                added <DistanceToNow date={todo.addedAt} />
                            </>
                        ) : (
                            <>adding</>
                        )}
                    </ParagraphSubtext>
                </Stack.Vertical>
            </Stack.Horizontal>
        </Container>
    )
}
