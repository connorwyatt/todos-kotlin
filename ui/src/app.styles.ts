import styled, { css } from "styled-components"

export const Container = styled.div(
    ({ theme }) => css`
        display: grid;
        gap: ${theme.spacing.medium};
        grid-template-areas: "." "addTodo" "todosList" ".";
        grid-template-rows: 1fr auto auto 1fr;
        height: 100vh;
        margin: 0 auto;
        max-width: 48rem;
        overflow: hidden;
        padding: ${theme.spacing.medium};
    `,
)

export const AddTodoContainer = styled.div`
    grid-area: addTodo;
`

export const TodosListContainer = styled.div(
    ({ theme }) => css`
        background-color: ${theme.components.panel.backgroundColor};
        border: 1px solid ${theme.components.panel.borderColor};
        border-radius: 0.5rem;
        box-shadow: 0 0.25rem 0.5rem ${theme.components.panel.shadowColor};
        grid-area: todosList;
        overflow-y: auto;
    `,
)
