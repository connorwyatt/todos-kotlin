import styled, { css, DefaultTheme } from "styled-components"

export interface ContainerProps {
    $direction: "horizontal" | "vertical"
    $spacing: keyof DefaultTheme["spacing"] | null
}

export const Container = styled.div<ContainerProps>(
    ({ theme, $direction, $spacing }) => css`
        display: flex;
        flex-direction: ${$direction === "horizontal" ? "row" : $direction === "vertical" ? "column" : null};
        gap: ${$spacing != null ? theme.spacing[$spacing] : null};
    `,
)
