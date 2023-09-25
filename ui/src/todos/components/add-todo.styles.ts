import { size } from "polished"
import styled, { css } from "styled-components"

import { buttonReset } from "~/shared/styles/utilities/button-reset"

export const Form = styled.form`
    display: flex;
`

export const Input = styled.input(
    ({ theme }) => css`
        appearance: none;
        background: none;
        border: none;
        flex: 1 1 0;
        font-size: 1.25rem;
        grid-area: input;
        outline: none;
        padding: ${theme.spacing.medium};

        &::placeholder {
            color: ${theme.typography.colors.secondary};
            font-style: italic;
        }
    `,
)

export interface ButtonProps {
    $size: number
}

export const Button = styled.button<ButtonProps>(
    ({ $size }) => css`
        ${buttonReset};
        ${size($size)};

        align-items: center;
        display: flex;
        flex: 0 0 auto;
        justify-content: center;
    `,
)
