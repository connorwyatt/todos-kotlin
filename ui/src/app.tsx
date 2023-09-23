import { AddTodoContainer, Container, TodosListContainer } from "~/app.styles"
import { AddTodo } from "~/todos/components/add-todo"
import { TodosList } from "~/todos/components/todos-list"

export const App = () => {
    return (
        <Container>
            <AddTodoContainer>
                <AddTodo />
            </AddTodoContainer>

            <TodosListContainer>
                <TodosList />
            </TodosListContainer>
        </Container>
    )
}
