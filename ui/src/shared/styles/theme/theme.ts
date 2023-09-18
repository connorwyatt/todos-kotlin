import { lighten } from "polished"
import { DefaultTheme } from "styled-components"

import { palette } from "~/shared/styles/palette"

export const theme: DefaultTheme = {
    app: {
        backgroundColors: [lighten(0.1, palette.argentinianBlue), lighten(0.1, palette.mayaBlue)],
        textColor: palette.jet,
    },
    typography: {
        fontFamilies: {
            body: '"Clear Sans", sans-serif',
        },
    },
}
