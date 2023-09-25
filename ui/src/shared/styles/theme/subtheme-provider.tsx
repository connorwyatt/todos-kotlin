import { FC, PropsWithChildren } from "react"
import { DefaultTheme, ThemeProvider } from "styled-components"

import { Container } from "~/shared/styles/theme/subtheme-provider.styles"

export interface SubthemeProviderProps extends PropsWithChildren {
    theme: DefaultTheme
}

export const SubthemeProvider: FC<SubthemeProviderProps> = ({ children, theme }) => {
    return (
        <ThemeProvider theme={theme}>
            <Container>{children}</Container>
        </ThemeProvider>
    )
}
