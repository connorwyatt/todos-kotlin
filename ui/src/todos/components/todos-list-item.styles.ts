import styled, { css } from "styled-components"

export interface ContainerProps {
    $isOptimistic: boolean
}

export const Container = styled.div<ContainerProps>(
    ({ theme, $isOptimistic }) => css`
        opacity: ${$isOptimistic && 0.5};
        padding: ${theme.spacing.small} ${theme.spacing.medium};
    `,
)
