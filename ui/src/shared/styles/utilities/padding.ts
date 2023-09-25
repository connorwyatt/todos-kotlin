import { css, DefaultTheme } from "styled-components"

export const padding = (spacing: keyof DefaultTheme["spacing"]) =>
    css(
        ({ theme }) => css`
            padding: ${theme.spacing[spacing]};
        `,
    )
