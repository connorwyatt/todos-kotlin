/* eslint-disable sonarjs/no-nested-switch */
import styled, { css, DefaultTheme } from "styled-components"

export interface ContainerProps {
    $align: "top" | "center" | "bottom" | "stretch"
    $direction: "horizontal" | "vertical"
    $justify: "left" | "center" | "right" | "stretch"
    $spacing: keyof DefaultTheme["spacing"] | null
}

export const Container = styled.div<ContainerProps>(
    ({ theme, $align, $direction, $justify, $spacing }) => css`
        align-items: ${alignItemsValue($direction, $align, $justify)};
        display: flex;
        flex-direction: ${$direction === "horizontal" ? "row" : $direction === "vertical" ? "column" : null};
        gap: ${$spacing != null ? theme.spacing[$spacing] : null};
        justify-content: ${justifyContentValue($direction, $align, $justify)};
        width: 100%;
    `,
)

const alignItemsValue = (
    direction: ContainerProps["$direction"],
    align: ContainerProps["$align"],
    justify: ContainerProps["$justify"],
): string => {
    switch (direction) {
        case "vertical":
            switch (justify) {
                case "left":
                    return "start"
                case "center":
                    return "center"
                case "right":
                    return "end"
                case "stretch":
                    return "stretch"
            }
            break
        case "horizontal":
            switch (align) {
                case "top":
                    return "start"
                case "center":
                    return "center"
                case "bottom":
                    return "end"
                case "stretch":
                    return "stretch"
            }
            break
    }
}

const justifyContentValue = (
    direction: ContainerProps["$direction"],
    align: ContainerProps["$align"],
    justify: ContainerProps["$justify"],
): string => {
    switch (direction) {
        case "horizontal":
            switch (justify) {
                case "left":
                    return "start"
                case "center":
                    return "center"
                case "right":
                    return "end"
                case "stretch":
                    return "stretch"
            }
            break
        case "vertical":
            switch (align) {
                case "top":
                    return "start"
                case "center":
                    return "center"
                case "bottom":
                    return "end"
                case "stretch":
                    return "stretch"
            }
            break
    }
}
