import { CSSObject } from "styled-components"

export const forceSize = (height: string, width: string = height): CSSObject => ({
    height: height,
    maxHeight: height,
    maxWidth: width,
    minHeight: height,
    minWidth: width,
    width: width,
})
