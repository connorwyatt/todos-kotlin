import { Container, TodosListContainer } from "~/app.styles"
import { TodosList } from "~/todos/components/todos-list"

export const App = () => {
    return (
        <Container>
            <TodosListContainer>
                <TodosList />
            </TodosListContainer>
        </Container>
    )
}
