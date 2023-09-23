import styled, { css } from "styled-components"

export interface ContainerProps {
    $removePadding: boolean
}

export const Container = styled.div<ContainerProps>(
    ({ theme, $removePadding }) => css`
        background-color: ${theme.components.panel.backgroundColor};
        border: 1px solid ${theme.components.panel.borderColor};
        border-radius: 0.5rem;
        box-shadow: 0 0.25rem 0.5rem ${theme.components.panel.shadowColor};
        padding: ${!$removePadding && theme.spacing.medium};
    `,
)
