import { lighten, transparentize } from "polished"
import { DefaultTheme } from "styled-components"

import { palette } from "~/shared/styles/palette"
import { typography } from "~/shared/styles/theme/typography"

export const theme: DefaultTheme = {
    app: {
        backgroundColors: [lighten(0.1, palette.jet), lighten(0.1, palette.paynesGray)],
        textColor: typography.colors.primary,
    },
    components: {
        todosList: {
            backgroundColor: transparentize(0.25, palette.antiflashWhite),
            borderColor: palette.antiflashWhite,
            shadowColor: transparentize(0.9, palette.jet),
        },
    },
    spacing: {
        xsmall: "0.25rem",
        small: "0.5rem",
        medium: "1rem",
    },
    typography,
}
