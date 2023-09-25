import { isEmpty } from "rambdax/immutable"
import { FC } from "react"

import { Padding } from "~/shared/components/layout/padding"
import { Stack } from "~/shared/components/layout/stack"
import { Paragraph } from "~/shared/components/typography/typography"
import { Repeat } from "~/shared/components/utilities/repeat"
import { useTodos } from "~/todos/api/use-todos"
import { TodosListItem } from "~/todos/components/todos-list-item"

export const TodosList: FC = () => {
    const { data: todos } = useTodos()

    if (todos == null) {
        return (
            <Repeat times={5}>
                <TodosListItem.Skeleton />
            </Repeat>
        )
    }

    if (isEmpty(todos)) {
        return (
            <Padding spacing="medium">
                <Stack.Vertical spacing="small" justify="center">
                    <Paragraph color="secondary" italicized>
                        You have no todos!
                    </Paragraph>

                    <Paragraph color="secondary" italicized>
                        Add one above to get started.
                    </Paragraph>
                </Stack.Vertical>
            </Padding>
        )
    }

    return (
        <Stack.Vertical>
            {todos.map((todo) => (
                <TodosListItem key={todo.id} todo={todo} />
            ))}
        </Stack.Vertical>
    )
}
