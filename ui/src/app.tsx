import { AddTodoContainer, Container, TodosListContainer } from "~/app.styles"
import { bluePanelTheme } from "~/shared/styles/theme/blue-panel-theme"
import { SubthemeProvider } from "~/shared/styles/theme/subtheme-provider"
import { AddTodo } from "~/todos/components/add-todo"
import { TodosList } from "~/todos/components/todos-list"

export const App = () => {
    return (
        <Container>
            <AddTodoContainer>
                <SubthemeProvider theme={bluePanelTheme}>
                    <AddTodo />
                </SubthemeProvider>
            </AddTodoContainer>

            <TodosListContainer>
                <TodosList />
            </TodosListContainer>
        </Container>
    )
}
