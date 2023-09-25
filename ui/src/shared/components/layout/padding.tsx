import { FC, PropsWithChildren } from "react"
import { DefaultTheme } from "styled-components"

import { Container } from "~/shared/components/layout/padding.styles"

export interface PaddingProps extends PropsWithChildren {
    spacing: keyof DefaultTheme["spacing"]
}

export const Padding: FC<PaddingProps> = ({ children, spacing }) => {
    return <Container $spacing={spacing}>{children}</Container>
}
