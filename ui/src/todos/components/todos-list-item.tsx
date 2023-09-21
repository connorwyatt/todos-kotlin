import { FC } from "react"

import { DistanceToNow } from "~/shared/components/dates/distance-to-now"
import { Stack } from "~/shared/components/layout/stack"
import { Paragraph, ParagraphSubtext } from "~/shared/components/typography/typography"
import { Todo } from "~/todos/api/models/todo"

export interface TodosListItemProps {
    todo: Todo
}

export const TodosListItem: FC<TodosListItemProps> = ({ todo }) => {
    return (
        <Stack.Vertical>
            <Paragraph>{todo.title}</Paragraph>

            <ParagraphSubtext color="secondary" italicized>
                added <DistanceToNow date={todo.addedAt} />
            </ParagraphSubtext>
        </Stack.Vertical>
    )
}
