import { FunctionComponent, SVGProps } from "react"
import { DefaultTheme } from "styled-components"

export type SVGComponent = FunctionComponent<SVGProps<SVGSVGElement> & { title?: string | undefined }>

export type IconSize = keyof DefaultTheme["components"]["icons"]["sizes"] | "variable"
