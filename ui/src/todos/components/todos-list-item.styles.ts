import styled, { css } from "styled-components"

export interface ContainerProps {
    $isOptimistic: boolean
}

export const Container = styled.div<ContainerProps>(
    ({ $isOptimistic }) => css`
        opacity: ${$isOptimistic && 0.5};
    `,
)
