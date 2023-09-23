import { FC } from "react"

import { Panel } from "~/shared/components/layout/panel"
import { Stack } from "~/shared/components/layout/stack"
import { useTodos } from "~/todos/api/use-todos"
import { TodosListItem } from "~/todos/components/todos-list-item"
import { Container } from "~/todos/components/todos-list.styles"

export const TodosList: FC = () => {
    const { data: todos } = useTodos()

    return (
        <Panel>
            <Container>
                <Stack.Vertical spacing="medium">
                    {todos != null && todos.map((todo) => <TodosListItem key={todo.id} todo={todo} />)}
                </Stack.Vertical>
            </Container>
        </Panel>
    )
}
