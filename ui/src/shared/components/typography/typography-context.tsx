import { createContext } from "react"
import { DefaultTheme } from "styled-components"

export interface TypographyContextValue {
    color: keyof DefaultTheme["typography"]["colors"]
    emphasized: boolean
    italicized: boolean
    textStyle: keyof DefaultTheme["typography"]["styles"]
}

const defaultValue: TypographyContextValue = {
    color: "primary",
    emphasized: false,
    italicized: false,
    textStyle: "paragraph",
}

export const TypographyContext = createContext<TypographyContextValue>(defaultValue)
