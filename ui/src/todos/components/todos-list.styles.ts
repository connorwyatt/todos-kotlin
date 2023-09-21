import styled, { css } from "styled-components"

export const Container = styled.div(
    ({ theme }) => css`
        background-color: ${theme.components.todosList.backgroundColor};
        border: 1px solid ${theme.components.todosList.borderColor};
        border-radius: 0.5rem;
        box-shadow: 0 0.25rem 0.5rem ${theme.components.todosList.shadowColor};
        padding: 1rem;
    `,
)
