import { css } from "styled-components"

export const themeDefaults = css(
    ({ theme }) => css`
        color: ${theme.app.textColor};
    `,
)
