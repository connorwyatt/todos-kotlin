import { transparentize } from "polished"
import { mergeDeepRight } from "rambdax/immutable"
import { DefaultTheme } from "styled-components"

import { palette } from "~/shared/styles/palette"
import { invertedTypographyColors } from "~/shared/styles/theme/inverted-typography-colors"
import { theme } from "~/shared/styles/theme/theme"
import { Subset } from "~/shared/types/subset"

const newProps: Subset<DefaultTheme> = {
    app: { textColor: invertedTypographyColors.primary },
    components: {
        panel: { backgroundColor: transparentize(0.25, palette.argentinianBlue), borderColor: palette.argentinianBlue },
    },
    typography: {
        colors: invertedTypographyColors,
    },
}

export const bluePanelTheme: DefaultTheme = mergeDeepRight(theme, newProps)
