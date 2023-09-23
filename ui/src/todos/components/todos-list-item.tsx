import { FC, useCallback } from "react"

import { DistanceToNow } from "~/shared/components/dates/distance-to-now"
import { Checkbox } from "~/shared/components/inputs/checkbox"
import { Stack } from "~/shared/components/layout/stack"
import { Paragraph, ParagraphSubtext } from "~/shared/components/typography/typography"
import { Todo } from "~/todos/api/models/todo"
import { useCompleteTodoMutation } from "~/todos/api/use-complete-todo-mutation"

export interface TodosListItemProps {
    todo: Todo
}

export const TodosListItem: FC<TodosListItemProps> = ({ todo }) => {
    const { mutate } = useCompleteTodoMutation()

    const handleChange = useCallback(() => {
        mutate(todo.id)
    }, [mutate, todo])

    return (
        <Stack.Horizontal spacing="medium" align="center">
            <Checkbox size="large" value={todo.isComplete} onChange={handleChange} displayOnly={todo.isComplete} />

            <Stack.Vertical>
                <Paragraph>{todo.title}</Paragraph>

                <ParagraphSubtext color="secondary" italicized>
                    added <DistanceToNow date={todo.addedAt} />
                </ParagraphSubtext>
            </Stack.Vertical>
        </Stack.Horizontal>
    )
}
