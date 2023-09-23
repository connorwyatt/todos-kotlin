import styled, { css } from "styled-components"

export const Container = styled.div(
    ({ theme }) => css`
        display: flex;
        flex-direction: column;
        gap: 1rem;
        height: 100vh;
        justify-content: center;
        margin: 0 auto;
        max-width: 48rem;
        overflow: hidden;
        padding: ${theme.spacing.medium};
    `,
)

export const AddTodoContainer = styled.div`
    flex: 0 0 auto;
`

export const TodosListContainer = styled.div`
    flex: 0 1 0;
`
