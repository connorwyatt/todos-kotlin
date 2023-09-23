import styled, { css } from "styled-components"

export const Input = styled.input(
    ({ theme }) => css`
        appearance: none;
        background: none;
        border: none;
        font-size: 1.25rem;
        outline: none;
        padding: ${theme.spacing.medium};
        width: 100%;

        &::placeholder {
            color: ${theme.typography.colors.secondary};
            font-style: italic;
        }
    `,
)
