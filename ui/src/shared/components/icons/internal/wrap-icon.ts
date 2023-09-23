import styled, { CSSObject } from "styled-components"

import { IconSize, SVGComponent } from "~/shared/components/icons/internal/types"
import { forceSize } from "~/shared/styles/utilities/force-size"

export interface IconWrapperProps {
    $inline: boolean
    $size: IconSize
}

export const wrapIcon = (Component: SVGComponent) =>
    styled(Component)<IconWrapperProps>(({ theme, $inline, $size }) => {
        const sizing: CSSObject =
            $size === "variable" ? { height: "100%", width: "100%" } : forceSize(theme.components.icons.sizes[$size])

        const inlineOrBlock: CSSObject = $inline
            ? { display: "inline-block", verticalAlign: "-0.15em" }
            : { display: "block" }

        return {
            ...sizing,
            ...inlineOrBlock,
            fill: "currentcolor",
            stroke: "currentcolor",
        }
    })
